 /*! \file exception.cc 
 //  \brief Entry point into the Nachos kernel .
 //
 //    There are two kinds of things that can cause control to
 //    transfer back to here:
 //
 //    syscall -- The user code explicitly requests to call a Nachos
 //    system call
 //
 //    exceptions -- The user code does something that the CPU can't handle.
 //    For instance, accessing memory that doesn't exist, arithmetic errors,
 //    etc.  
 //
 //    Interrupts (which can also cause control to transfer from user
 //    code into the Nachos kernel) are handled elsewhere.
 */
 // Copyright (c) 1992-1993 The Regents of the University of California.
 // All rights reserved.  See copyright.h for copyright notice and limitation
 
 // of liability and disclaimer of warranty provisions.
 
#include "machine/machine.h"
#include "kernel/msgerror.h"
#include "kernel/system.h"
#include "userlib/syscall.h"
#include "kernel/synch.h"
#include "drivers/drvACIA.h"
#include "drivers/drvConsole.h"
#include "filesys/oftable.h"
#include "vm/pagefaultmanager.h"
#include "utility/objid.h"

//----------------------------------------------------------------------
// GetLengthParam
/*! Returns the length of a string stored in the machine memory,
//    including the '\0' terminal
//
// \param addr is the memory address of the string */
//----------------------------------------------------------------------
static int GetLengthParam(int addr) {
   int i=0;
   int c=-1;
   
   // Scan the string until the null character is found
   while (c != 0) {
     g_machine->mmu->ReadMem(addr++,1,&c,false);
     i++;
   }
   return i+1;
 }

//----------------------------------------------------------------------
// GetStringParam
/*!	Copies a string from the machine memory
//
//	\param addr is the memory address of the string
//	\param dest is where the string is going to be copied
//      \param maxlen maximum length of the string to copy in dest,
//        including the trailing '\0' 
*/
//----------------------------------------------------------------------
static void GetStringParam(int addr,char *dest,int maxlen) {
   int i=0;
   int c=-1;
   
   while ((c != 0) && (i < maxlen)) {
     // Read a character from the machine memory
     g_machine->mmu->ReadMem(addr++,1,&c,false);
     // Put it in the kernel memory
     dest[i++] = (char)c;
   }
   // Force a \0 at the end
   dest[maxlen-1]='\0';
 }

 //----------------------------------------------------------------------
 // ExceptionHandler
 /*!   Entry point into the Nachos kernel.  Called when a user program
 //    is executing, and either does a syscall, or generates an addressing
 //    or arithmetic exception.
 //
 //    For system calls, the calling convention is the following:
 //
 //    - system call identifier -- r2
 //    - arg1 -- r4
 //    - arg2 -- r5
 //    - arg3 -- r6
 //    - arg4 -- r7
 //
 //    The result of the system call, if any, must be put back into r2. 
 //
 //    \param exceptiontype is the kind of exception.
 //           The list of possible exception are defined in machine.h.
 //    \param vaddr is the address that causes the exception to occur
 //           (when used)  
 */
 //----------------------------------------------------------------------
void ExceptionHandler(ExceptionType exceptiontype, int vaddr)
{

  // Get the content of the r2 register (system call number in case
  // of a system call
  int type = g_machine->ReadIntRegister(2);

  switch (exceptiontype) {

  case NO_EXCEPTION:
    printf("Nachos internal error, a NoException exception is raised ...\n");
    g_machine->interrupt->Halt(0);
    break;

  case SYSCALL_EXCEPTION: {
    // System calls
    // -------------
    switch(type) {

	char msg[MAXSTRLEN]; // Argument for the PError system call

	// You will find below all Nachos system calls ...

	case SC_HALT: 
	  // The halt system call. Stops Nachos.
	  DEBUG('e', (char*)"Shutdown, initiated by user program.\n");
	  g_machine->interrupt->Halt(0);
	  g_syscall_error->SetMsg((char*)"",NoError);
	  return;
	  
	case SC_SYS_TIME: {
	  // The systime system call. Gets the system time
	  DEBUG('e', (char*)"Systime call, initiated by user program.\n");
	  int addr=g_machine->ReadIntRegister(4);
	  uint64_t tick = g_stats->getTotalTicks();
	  uint32_t seconds = (uint32_t)
	    cycle_to_sec(tick,g_cfg->ProcessorFrequency);
	  uint32_t nanos =  (uint32_t)
	    cycle_to_nano(tick,g_cfg->ProcessorFrequency);
	  g_machine->mmu->WriteMem(addr,sizeof(uint32_t),seconds);
	  g_machine->mmu->WriteMem(addr+4,sizeof(uint32_t),nanos);
	  g_syscall_error->SetMsg((char*)"",NoError);
	  break;
	}

	case SC_EXIT:{
          // The exit system call
	  // Ends the calling thread
	  DEBUG('e', (char*)"Thread 0x%x %s exit call.\n", g_current_thread,g_current_thread->GetName());
	  ASSERT(g_current_thread->typeId == THREAD_TYPE_ID);
	  g_current_thread->Finish();
	  break;
        }

        case SC_EXEC: {
          // The exec system call
          // Creates a new process (thread+address space)
          DEBUG('e', (char*)"Process: Exec call.\n");
	  int addr;
          int size;
	  char name[MAXSTRLEN];
	  int error;
	  
	  // Get the process name
          addr = g_machine->ReadIntRegister(4);
          size = GetLengthParam(addr);
          char ch[size];
	  GetStringParam(addr,ch,size);	
	  sprintf(name,"master thread of process %s",ch);
	  Process * p = new Process(ch, & error);
	  if (error != NoError) {
	    g_machine->WriteIntRegister(2,-1);
	    if (error == OutOfMemory)
	      g_syscall_error->SetMsg((char*)"",error);
	    else
	      g_syscall_error->SetMsg(ch,error);
	    break;
	  }
	  Thread *ptThread = new Thread(name);
	  int32_t tid = g_object_ids->AddObject(ptThread);
	  error = ptThread->Start(p,
				  p->addrspace->getCodeStartAddress(),
				  -1);
	  if (error != NoError) {
	    g_machine->WriteIntRegister(2,-1);	
	    if (error == OutOfMemory)
	      g_syscall_error->SetMsg((char*)"",error);
	    else
	      g_syscall_error->SetMsg(name,error);
	    break;
	  }
	  g_syscall_error->SetMsg((char*)"",NoError);
	  g_machine->WriteIntRegister(2,tid);	
	  break;
        }

	case SC_NEW_THREAD: {	  
	  // The newThread system call
	  // Create a new thread in the same address space
	  DEBUG('e', (char*)"Multithread: NewThread call.\n");
	  Thread *ptThread;
	  int name_addr;
	  int32_t fun;
	  int arg;
	  int err;
	  // Get the address of the string for the name of the thread
	  name_addr = g_machine->ReadIntRegister(4);
	  // Get the pointer to the function to be executed by the new thread
	  fun = g_machine->ReadIntRegister(5);
	  // Get the function parameters
	  arg = g_machine->ReadIntRegister(6);
	  // Build the name of the thread
	  int size = GetLengthParam(name_addr);
	  char thr_name[size];
	  GetStringParam(name_addr, thr_name, size);
	  //char *proc_name = g_current_thread->getProcessOwner()->getName();
	  // Finally start it
	  ptThread = new Thread(thr_name);
	  int32_t tid;
	  tid = g_object_ids->AddObject(ptThread);
	  err = ptThread->Start(g_current_thread->GetProcessOwner(),
				fun, arg);
	  if (err != NoError) {
	    g_machine->WriteIntRegister(2,-1);
	    g_syscall_error->SetMsg((char*)"",err);
	  }
	  else {
	    g_machine->WriteIntRegister(2,tid);	
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }
	  break;	   
	}
	
        case SC_JOIN: {
	  // The join system call
          // Wait for the thread idThread to finish
          DEBUG('e', (char*)"Process or thread: Join call.\n");	  
	  int32_t tid;
          Thread* ptThread;
	  tid = g_machine->ReadIntRegister(4);
	  ptThread = (Thread *)g_object_ids->SearchObject(tid);
	  if (ptThread
	      && ptThread->typeId == THREAD_TYPE_ID)
	    {
	      g_current_thread->Join(ptThread);
	      g_syscall_error->SetMsg((char*)"",NoError);
	      g_machine->WriteIntRegister(2,0);      
	    }
	  else
	    // Thread already terminated (typeId set to INVALID_TYPE_ID) or call on an object
	    // that is sot a thread
	    // Exit with no error code
	    {
	      g_syscall_error->SetMsg((char*)"",InvalidThreadId);
	      g_machine->WriteIntRegister(2,-1);      
	    }
	  DEBUG('e',(char*)"Fin Join");
	  break;	   
        }

	case SC_YIELD: {
	  DEBUG('e', (char*)"Process or thread: Yield call.\n");
	  ASSERT(g_current_thread->typeId == THREAD_TYPE_ID);
	  g_current_thread->Yield();
	  g_syscall_error->SetMsg((char*)"",NoError);
	  break;
	}
	
	case SC_PERROR: { 
	  // the PError system call
	  // print the last error message
	  DEBUG('e', (char*)"Debug: Perror call.\n");
	  int size;
	  int addr;
	  addr = g_machine->ReadIntRegister(4);
	  size = GetLengthParam(addr);
	  char ch[size];
	  GetStringParam(addr,ch,size);
	  g_syscall_error->PrintLastMsg(g_console_driver,ch);
	  break;
	}
	  
        case SC_CREATE: {
          // The create system call
          // Create a new file in nachos file system
          DEBUG('e', (char*)"Filesystem: Create call.\n");	 
	  int addr;
	  int size;
	  int ret;
	  int sizep;
	  // Get the name and initial size of the new file
	  addr = g_machine->ReadIntRegister(4);
          size = g_machine->ReadIntRegister(5);
          sizep = GetLengthParam(addr);
	  char ch[sizep];
          GetStringParam(addr,ch,sizep);
	  // Try to create it
          int err = g_file_system->Create(ch,size);
          if (err == NoError) {
	    g_syscall_error->SetMsg((char*)"",NoError);
	    ret = 0; 
	  }
          else { 
	      ret = -1; 
	      if (err == OutOfDisk) g_syscall_error->SetMsg((char*)"",err);
	      else g_syscall_error->SetMsg(ch,err);
	  }
	  g_machine->WriteIntRegister(2,ret);
          break;
        }
         
        case SC_OPEN: {
          // The open system call
          // Opens a file and returns an openfile identifier
          DEBUG('e', (char*)"Filesystem: Open call.\n");
	  int addr;
	  int ret;
	  int sizep;
	  // Get the file name
	  addr = g_machine->ReadIntRegister(4);
	  sizep = GetLengthParam(addr);
	  char ch[sizep];
	  GetStringParam(addr,ch,sizep);
	  // Try to open the file
	  OpenFile *file = g_open_file_table->Open(ch);
	  int32_t fid;
	  if (file == NULL) {
	    ret = -1;
	    g_syscall_error->SetMsg(ch,OpenFileError);
	  }
	  else {
	    fid = g_object_ids->AddObject(file);
	    ret = fid;
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }
	  g_machine->WriteIntRegister(2,ret); 
          break;         
        }
 
	case SC_READ: {
	 // The read system call
	 // Read in a file or the console
	 DEBUG('e', (char*)"Filesystem: Read call.\n");
         int addr;
         int size;
         int32_t f;
         int numread;
	 // Get the buffer address in the machine memory 
         addr = g_machine->ReadIntRegister(4);
	 // Get the requested size
         size = g_machine->ReadIntRegister(5);
	 // Get the openfile number or 0 (console)
         f = g_machine->ReadIntRegister(6);
         char buffer[size];

	 // Read in a file
         if (f != ConsoleInput) {
	   int32_t fid = f;
	   OpenFile *file = (OpenFile *)g_object_ids->SearchObject(fid);
	   if (file && file->typeId == FILE_TYPE_ID)
	     {
	       numread = file->Read(buffer,size);
	       g_syscall_error->SetMsg((char*)"",NoError);
	     }
	   else
	     {
	     numread = -1;
	     sprintf(msg,"%d",f);
	     g_syscall_error->SetMsg(msg,InvalidFileId);
	     }
	 }
	 // Read on the console
         else {
	   g_console_driver->GetString(buffer,size);
	   numread = size;
	   g_syscall_error->SetMsg((char*)"",NoError);
	 }         
         for (int i=0;i<numread;i++)
           { //copy the buffer into the emulator memory
             g_machine->mmu->WriteMem(addr++,1,buffer[i]);
           }         
         g_machine->WriteIntRegister(2,numread);
         break;
       }
 
	case SC_WRITE: {
	  // The write system call
	  // Write in a file or at the console
	 DEBUG('e', (char*)"Filesystem: Write call.\n");
         int addr;
         int size;
         int32_t f;
         int c;
         addr = g_machine->ReadIntRegister(4);
         size = g_machine->ReadIntRegister(5);
	 //f is the openfileid or 1 (console)
         f = g_machine->ReadIntRegister(6);        
         char buffer [size];
         for (int i=0;i<size;i++) {
	   g_machine->mmu->ReadMem(addr++,1,(int *)(&c),false);
	   buffer[i] = c;
	 }
         int numwrite;

	 // Write in a file
         if (f > ConsoleOutput) {
	   int32_t fid = f;
	   OpenFile *file = (OpenFile *)g_object_ids->SearchObject(fid);
	   if (file && file->typeId == FILE_TYPE_ID)
	     {
	       //write in file
	       numwrite = file->Write(buffer,size);
	       g_syscall_error->SetMsg((char*)"",NoError);
	     }
	   else
	     {
	       numwrite = -1;
	       sprintf(msg,"%d",f);
	       g_syscall_error->SetMsg(msg,InvalidFileId);
	     }
	 } 
	 // write at the console
         else {
	   if (f==ConsoleOutput) {
	     g_console_driver->PutString(buffer,size);
	     numwrite = size;
	     g_syscall_error->SetMsg((char*)"",NoError);
	   }
	   else {
	     numwrite = -1;
	     sprintf(msg,"%d",f);
	     g_syscall_error->SetMsg(msg,InvalidFileId);
	   }
	 }         
         g_machine->WriteIntRegister(2,numwrite);
         break;
       }
        
      case SC_SEEK:{
	// Seek to a given position in an opened file
	 DEBUG('e', (char*)"Filesystem: Seek call.\n");
         int offset;
         int32_t f;
	 int error;

	 // Get the offset into the file
         offset = g_machine->ReadIntRegister(4);
	 // Get the openfile number or 1 (console)
         f = g_machine->ReadIntRegister(5);

	 // Seek into a file
        if (f > ConsoleOutput) {
	   int32_t fid = f;
	   OpenFile *file = (OpenFile *)g_object_ids->SearchObject(fid);
	   if (file && file->typeId == FILE_TYPE_ID)
	     {
	       file->Seek(offset);
	       g_syscall_error->SetMsg((char*)"",NoError);
	     }
	   else
	     {
	       error = -1;
	       sprintf(msg,"%d",f);
	       g_syscall_error->SetMsg(msg,InvalidFileId);
	     }
	   g_machine->WriteIntRegister(2,error);
	 } 
         else {
	   g_machine->WriteIntRegister(2,-1);
	   sprintf(msg,"%d",f);
	   g_syscall_error->SetMsg(msg,InvalidFileId);
	 }
         break;
       }

	case SC_CLOSE: {
	  // The close system call
	  // Close a file
	  DEBUG('e', (char*)"Filesystem: Close call.\n");	
	  // Get the openfile number
	  int32_t fid = g_machine->ReadIntRegister(4);
	  OpenFile *file = (OpenFile *)g_object_ids->SearchObject(fid);
	  if (file && file->typeId == FILE_TYPE_ID)
	    {
	      g_open_file_table->Close(file->GetName());
	      g_object_ids->RemoveObject(fid);
	      delete file;
	      g_machine->WriteIntRegister(2,0);
	      g_syscall_error->SetMsg((char*)"",NoError);
	    }
	  else
	    { 
	      g_machine->WriteIntRegister(2,-1);
	      sprintf(msg,"%d",fid);
	      g_syscall_error->SetMsg(msg,InvalidFileId);
	    }
	  break;
	}
 
        case SC_REMOVE: {
	  // The Remove system call
	  // Remove a file from the file system
	  DEBUG('e', (char*)"Filesystem: Remove call.\n");
	  int ret;
	  int addr;
	  int sizep;
	  // Get the name of the file to be removes
	  addr = g_machine->ReadIntRegister(4);
	  sizep = GetLengthParam(addr);
	  char *ch = new char[sizep];
	  GetStringParam(addr,ch,sizep);
	  // Actually remove it
	  int err=g_open_file_table->Remove(ch);
	  if (err == NoError) {
	    ret = 0; 
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }
	  else {
	    ret = -1;
	    g_syscall_error->SetMsg(ch,err);
	  }
	  g_machine->WriteIntRegister(2,ret);
	  break;
	}

	case SC_MKDIR:{
	  // the Mkdir system call
	  // make a new directory in the file system 
	  DEBUG('e', (char*)"Filesystem: Mkdir call.\n");    
	  int addr;
	  int sizep;	  
	  addr = g_machine->ReadIntRegister(4);
	  sizep = GetLengthParam(addr);
	  char name[sizep];
	  GetStringParam(addr,name,sizep);         
	  // name is the name of the new directory
	  int good=g_file_system->Mkdir(name);
	  if (good != NoError) {
	    g_machine->WriteIntRegister(2,-1);
	    if (good == OutOfDisk) g_syscall_error->SetMsg((char*)"",good);
	    else g_syscall_error->SetMsg(name,good);
	  }
	  else {
	    g_machine->WriteIntRegister(2,((int)good));
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }
	  break;
	}
	
	case SC_RMDIR:{
	  // the Rmdir system call
	  // remove a directory from the file system
	  DEBUG('e', (char*)"Filesystem: Rmdir call.\n");      
	  int addr;
	  int sizep; 
	  addr = g_machine->ReadIntRegister(4);
	  sizep = GetLengthParam(addr);
	  char name[sizep];
	  GetStringParam(addr,name,sizep);
	  int good=g_file_system->Rmdir(name);
	  if (good != NoError) {
	    g_machine->WriteIntRegister(2,-1);
	    g_syscall_error->SetMsg(name,good);
	  }
	  else {
	    g_machine->WriteIntRegister(2,((int)good));
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }
	  break;
	}

	case SC_FSLIST: {
	  // The FSList system call
	  // Lists all the file and directories in the filesystem
	  g_file_system->List();
	  g_syscall_error->SetMsg((char*)"",NoError);
	  break;
	}
      
         
	case SC_TTY_SEND:{
	  // the TtySend system call	
	  // Sends some char by the serial line emulated
	  DEBUG('e', (char*)"ACIA: Send call.\n");
	  if (g_cfg->ACIA != ACIA_NONE) {
	    int result;
	    int c;
	    int i;
	    int addr=g_machine->ReadIntRegister(4);
	    char buff[MAXSTRLEN];
	    for(i=0;;i++)
	      {
		g_machine->mmu->ReadMem(addr+i,1,&c,false);
		buff[i]=(char) c;
		if (buff[i] == '\0') break;
	      }
	    result=g_acia_driver->TtySend(buff);
	    g_machine->WriteIntRegister(2,result);	
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }	
	  else {
	    g_machine->WriteIntRegister(2,-1);
	    g_syscall_error->SetMsg((char*)"",NoACIA);
	  }
	  break;
	}
	
	case SC_TTY_RECEIVE:{
	  // the TtyReceive system call	
	  // read some char on the serial line
	  DEBUG('e', (char*)"ACIA: Receive call.\n");
	  if (g_cfg->ACIA != ACIA_NONE) {    
	    int result;
	    int i=0;	 
	    int addr=g_machine->ReadIntRegister(4);
	    int length=g_machine->ReadIntRegister(5);
	    char buff[length+1];
	    result=g_acia_driver->TtyReceive(buff,length);
	    while ((i <= length)) {
	      g_machine->mmu->WriteMem(addr,1,buff[i]);
	      addr++;
	      i++;
	    }
	    g_machine->mmu->WriteMem(addr,1,0);
	    g_machine->WriteIntRegister(2,result);	
	    g_syscall_error->SetMsg((char*)"",NoError);
	  }	
	  else {
	    g_machine->WriteIntRegister(2,-1);
	    g_syscall_error->SetMsg((char*)"",NoACIA);
	  }
	  break;
	}


#ifdef ETUDIANTS_TP

		// ############### Semaphores ###############
		// Create a semaphore
		case SC_SEM_CREATE: {
			DEBUG('e', (char*)"SEM_CREATE syscall called\n");

			// The int to store the values got from the registers
			int debug_name, count;

			// Get the values from registers
			debug_name = g_machine->ReadIntRegister(4);  // Int representation of the pointer
			count = g_machine->ReadIntRegister(5);

			// Build the name of the semaphore using utilities methods
			int name_size = GetLengthParam(debug_name);
			char name[name_size];
			GetStringParam(debug_name, name, name_size);

			// Then create the semaphore
			Semaphore *sema = new Semaphore(name, count);

			// Add its id to the object_ids of the system
			int32_t tid = g_object_ids->AddObject(sema);

			// Put the return value into reg2
			g_machine->WriteIntRegister(2, tid);
			g_syscall_error->SetMsg((char*)"", NoError);
			break;
		}

		// Create a semaphore
		case SC_SEM_DESTROY: {
			DEBUG('e', (char*)"SEM_DESTROY syscall called\n");

			// Get the values from registers
			int sema = g_machine->ReadIntRegister(4);
			sema = (int32_t)sema;

			// We get the semaphore from system objects
			Semaphore *sema_pointer = (Semaphore *)g_object_ids->SearchObject(sema);

			// Check if this Semaphore exists and is really a Semaphore
			if ((sema_pointer == NULL) || (sema_pointer->typeId != SEMAPHORE_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidSemaphoreId);

			// If everything's ok
			} else {

				// Remove the Semaphore from the obj lists
				g_object_ids->RemoveObject(sema);

				// Destroy the semaphore
				delete sema_pointer;

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Call to a V on a semaphore
		case SC_V: {
			DEBUG('e', (char*)"V syscall called\n");

			// Get the values from registers
			int sema = g_machine->ReadIntRegister(4);
			sema = (int32_t)sema;

			// We get the semaphore from system objects
			Semaphore *sema_pointer = (Semaphore *)g_object_ids->SearchObject(sema);

			// Check if this Semaphore exists and is really a Semaphore
			if ((sema_pointer == NULL) || (sema_pointer->typeId != SEMAPHORE_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidSemaphoreId);

			// If everything's ok
			} else {

				// Call V on this Semaphore
				sema_pointer->V();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Call to a P on a semaphore
		case SC_P: {
			DEBUG('e', (char*)"P syscall called\n");

			// Get the values from registers
			int sema = g_machine->ReadIntRegister(4);
			sema = (int32_t)sema;

			// We get the semaphore from system objects
			Semaphore *sema_pointer = (Semaphore *)g_object_ids->SearchObject(sema);

			// Check if this Semaphore exists and is really a Semaphore
			if ((sema_pointer == NULL) || (sema_pointer->typeId != SEMAPHORE_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidSemaphoreId);

			// If everything's ok
			} else {

				// Call P on this Semaphore
				sema_pointer->P();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}


		// ############### Locks ###############
		// Create a lock
		case SC_LOCK_CREATE: {
			DEBUG('e', (char*)"LOCK_CREATE syscall called\n");

			// The int to store the values got from the registers
			int debug_name;

			// Get the values from registers
			debug_name = g_machine->ReadIntRegister(4);  // Int representation of the pointer

			// Build the name of the lock using utilities methods
			int name_size = GetLengthParam(debug_name);
			char name[name_size];
			GetStringParam(debug_name, name, name_size);

			// Then create the lock
			Lock *lock = new Lock(name);

			// Add its id to the object_ids of the system
			int32_t tid = g_object_ids->AddObject(lock);

			// Put the return value into reg2
			g_machine->WriteIntRegister(2, tid);
			g_syscall_error->SetMsg((char*)"", NoError);
			break;
		}

		// Destroy a lock
		case SC_LOCK_DESTROY: {
			DEBUG('e', (char*)"LOCK_DESTROY syscall called\n");

			// Get the values from registers
			int lock = g_machine->ReadIntRegister(4);
			lock = (int32_t)lock;

			// We get the Lock from system objects
			Lock *lock_pointer = (Lock *)g_object_ids->SearchObject(lock);

			// Check if this Lock exists and is really a Lock
			if ((lock_pointer == NULL) || (lock_pointer->typeId != LOCK_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidLockId);

			// If everything's ok
			} else {

				// Remove the Lock from the obj lists
				g_object_ids->RemoveObject(lock);

				// Destroy the Lock
				delete lock_pointer;

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Acquire a lock
		case SC_LOCK_ACQUIRE: {
			DEBUG('e', (char*)"LOCK_ACQUIRE syscall called\n");

			// Get the values from registers
			int lock = g_machine->ReadIntRegister(4);
			lock = (int32_t)lock;

			// We get the Lock from system objects
			Lock *lock_pointer = (Lock *)g_object_ids->SearchObject(lock);

			// Check if this Lock exists and is really a Lock
			if ((lock_pointer == NULL) || (lock_pointer->typeId != LOCK_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidLockId);

			// If everything's ok
			} else {

				// Acquire the lock
				lock_pointer->Acquire();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Release a lock
		case SC_LOCK_RELEASE: {
			DEBUG('e', (char*)"LOCK_RELEASE syscall called\n");

			// Get the values from registers
			int lock = g_machine->ReadIntRegister(4);
			lock = (int32_t)lock;

			// We get the Lock from system objects
			Lock *lock_pointer = (Lock *)g_object_ids->SearchObject(lock);

			// Check if this Lock exists and is really a Lock
			if ((lock_pointer == NULL) || (lock_pointer->typeId != LOCK_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidLockId);

			// If everything's ok
			} else {

				// Acquire the lock
				lock_pointer->Release();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}


		// ############### Conditions ###############
		// Create a condition
		case SC_COND_CREATE: {
			DEBUG('e', (char*)"COND_CREATE syscall called\n");

			// The int to store the values got from the registers
			int debug_name;

			// Get the values from registers
			debug_name = g_machine->ReadIntRegister(4);  // Int representation of the pointer

			// Build the name of the Condition using utilities methods
			int name_size = GetLengthParam(debug_name);
			char name[name_size];
			GetStringParam(debug_name, name, name_size);

			// Then create the Condition
			Condition *cond = new Condition(name);

			// Add its id to the object_ids of the system
			int32_t tid = g_object_ids->AddObject(cond);

			// Put the return value into reg2
			g_machine->WriteIntRegister(2, tid);
			g_syscall_error->SetMsg((char*)"", NoError);
			break;
		}

		// Destroy a condition
		case SC_COND_DESTROY: {
			DEBUG('e', (char*)"COND_DESTROY syscall called\n");

			// Get the values from registers
			int cond = g_machine->ReadIntRegister(4);
			cond = (int32_t)cond;

			// We get the Condition from system objects
			Condition *cond_pointer = (Condition *)g_object_ids->SearchObject(cond);

			// Check if this Condition exists and is really a Lock
			if ((cond_pointer == NULL) || (cond_pointer->typeId != CONDITION_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidConditionId);

			// If everything's ok
			} else {

				// Remove the Condition from the obj lists
				g_object_ids->RemoveObject(cond);

				// Destroy the Condition
				delete cond_pointer;

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Wait on a condition
		case SC_COND_WAIT: {
			DEBUG('e', (char*)"COND_WAIT syscall called\n");

			// Get the values from registers
			int cond = g_machine->ReadIntRegister(4);
			cond = (int32_t)cond;

			// We get the Condition from system objects
			Condition *cond_pointer = (Condition *)g_object_ids->SearchObject(cond);

			// Check if this Condition exists and is really a Lock
			if ((cond_pointer == NULL) || (cond_pointer->typeId != CONDITION_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidConditionId);

			// If everything's ok
			} else {

				// Run wait on the condition
				cond_pointer->Wait();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Signal on a condition (wake the first waiting)
		case SC_COND_SIGNAL: {
			DEBUG('e', (char*)"COND_SIGNAL syscall called\n");

			// Get the values from registers
			int cond = g_machine->ReadIntRegister(4);
			cond = (int32_t)cond;

			// We get the Condition from system objects
			Condition *cond_pointer = (Condition *)g_object_ids->SearchObject(cond);

			// Check if this Condition exists and is really a Lock
			if ((cond_pointer == NULL) || (cond_pointer->typeId != CONDITION_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidConditionId);

			// If everything's ok
			} else {

				// Run signal on the condition
				cond_pointer->Signal();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}

		// Broadcast on a condition (wake all those waiting)
		case SC_COND_BROADCAST: {
			DEBUG('e', (char*)"COND_BROADCAST syscall called\n");

			// Get the values from registers
			int cond = g_machine->ReadIntRegister(4);
			cond = (int32_t)cond;

			// We get the Condition from system objects
			Condition *cond_pointer = (Condition *)g_object_ids->SearchObject(cond);

			// Check if this Condition exists and is really a Lock
			if ((cond_pointer == NULL) || (cond_pointer->typeId != CONDITION_TYPE_ID)) {

				// Return ok and an error message
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"", InvalidConditionId);

			// If everything's ok
			} else {

				// Run broadcast on the condition
				cond_pointer->Broadcast();

				// Return ok and no error message
				g_machine->WriteIntRegister(2, 0);
				g_syscall_error->SetMsg((char*)"", NoError);
			}
			break;
		}


		// ############### MMap files managment ###############

		// Manage the exception call to take care of a mapped file
		case SC_MMAP: {
			DEBUG('e', (char*)"SC_MMAP syscall called\n");

			// Get parameters from registers
			OpenFile *file = (OpenFile *)g_machine->ReadIntRegister(4);
			int size = g_machine->ReadIntRegister(5);
			ASSERT(file != NULL);
			ASSERT(size > 0);

			// Get the current process
			Process *current_process = g_current_thread->GetProcessOwner();
			ASSERT(current_process != NULL);

			// Launch the method call
			int result = current_process->addrspace->Mmap(file, size);
			if (result < 0) {

				// Return an error if not correct
				g_machine->WriteIntRegister(2, -1);
				g_syscall_error->SetMsg((char*)"Call of MMap() method in MMap exception failed\n", OutOfMemory);
			}

			// Return ok and no error message
			g_machine->WriteIntRegister(2, result);
			g_syscall_error->SetMsg((char*)"", NoError);
			break;
		}

#endif


       	default:
	         printf("Invalid system call number : %d\n", type);
	         exit(-1);
	         break;
      	}

      } 

       // from now, the code is executed whatever system call is invoked
       // we increment the PC counter
       g_machine->int_registers[PREVPC_REG]=g_machine->int_registers[PC_REG];
       g_machine->int_registers[PC_REG]=g_machine->int_registers[NEXTPC_REG];
       g_machine->int_registers[NEXTPC_REG]+=4;

       break;
     
       // Other exceptions
       // ----------------
   case READONLY_EXCEPTION:
     printf("FATAL USER EXCEPTION (Thread %s, PC=0x%x):\n",
	    g_current_thread->GetName(), g_machine->ReadIntRegister(PC_REG));
     printf("\t*** Write to virtual address 0x%x on read-only page ***\n",
	    vaddr);
     g_machine->interrupt->Halt(-1);
     break;

  case BUSERROR_EXCEPTION:
    printf("FATAL USER EXCEPTION (Thread %s, PC=0x%x):\n",
	   g_current_thread->GetName(), g_machine->ReadIntRegister(PC_REG));
    printf("\t*** Bus error on access to virtual address 0x%x ***\n",
	   vaddr);
    g_machine->interrupt->Halt(-1);
    break;

  case ADDRESSERROR_EXCEPTION:
    printf("FATAL USER EXCEPTION (Thread %s, PC=0x%x):\n",
	   g_current_thread->GetName(), g_machine->ReadIntRegister(PC_REG));
    printf("\t*** Access to invalid or unmapped virtual address 0x%x ***\n",
	   vaddr);
    g_machine->interrupt->Halt(-1);
    break;

  case OVERFLOW_EXCEPTION:
    printf("FATAL USER EXCEPTION (Thread %s, PC=0x%x):\n",
	   g_current_thread->GetName(), g_machine->ReadIntRegister(PC_REG));
    printf("\t*** Overflow exception at address 0x%x ***\n",
	   vaddr);
    g_machine->interrupt->Halt(-1);
    break;

  case ILLEGALINSTR_EXCEPTION:
    printf("FATAL USER EXCEPTION (Thread %s, PC=0x%x):\n",
	   g_current_thread->GetName(), g_machine->ReadIntRegister(PC_REG));
    printf("\t*** Illegal instruction at virtual address 0x%x ***\n", vaddr);
    g_machine->interrupt->Halt(-1);
    break;
 
  case PAGEFAULT_EXCEPTION:
    ExceptionType e;
    e = g_page_fault_manager->PageFault(vaddr / g_cfg->PageSize);
    if (e!=NO_EXCEPTION) {
      printf("\t*** Page fault handling failed, ... exiting\n");
      g_machine->interrupt->Halt(-1);
    }
    break;
    
  default:
    printf("Unknown exception %d\n", exceptiontype);
    g_machine->interrupt->Halt(-1);
    break;
  }

 }
