/*! \file synch.cc 
//  \brief Routines for synchronizing threads.  
//
//      Three kinds of synchronization routines are defined here: 
//      semaphores, locks and condition variables.
//
// Any implementation of a synchronization routine needs some
// primitive atomic operation. We assume Nachos is running on
// a uniprocessor, and thus atomicity can be provided by
// turning off interrupts. While interrupts are disabled, no
// context switch can occur, and thus the current thread is guaranteed
// to hold the CPU throughout, until interrupts are reenabled.
//
// Because some of these routines might be called with interrupts
// already disabled (Semaphore::V for one), instead of turning
// on interrupts at the end of the atomic operation, we always simply
// re-set the interrupt state back to its original value (whether
// that be disabled or enabled).
*/
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation 
// of liability and disclaimer of warranty provisions.


#include "kernel/system.h"
#include "kernel/scheduler.h"
#include "kernel/synch.h"

//----------------------------------------------------------------------
// Semaphore::Semaphore
/*! 	Initializes a semaphore, so that it can be used for synchronization.
//
// \param debugName is an arbitrary name, useful for debugging only.
// \param initialValue is the initial value of the semaphore.
*/
//----------------------------------------------------------------------
Semaphore::Semaphore(char* debugName, int initialValue)
{
  name = new char[strlen(debugName)+1];
  strcpy(name,debugName);
  value = initialValue;
  queue = new Listint;
  typeId = SEMAPHORE_TYPE_ID;
}

//----------------------------------------------------------------------
// Semaphore::Semaphore
/*! 	De-allocates a semaphore, when no longer needed.  Assume no one
//	is still waiting on the semaphore!
*/
//----------------------------------------------------------------------
Semaphore::~Semaphore()
{
  typeId = INVALID_TYPE_ID;
  if (!queue->IsEmpty()) {
    DEBUG('s', (char *)\n"Destructor of semaphore \"%s\", queue is not empty!!\n",name);
    Thread *t =  (Thread *)queue->Remove();
    DEBUG('s', (char *)\n"Queue contents %s\n",t->GetName());
    queue->Append((void *)t);
  }
  ASSERT(queue->IsEmpty());
  delete [] name;
  delete queue;
}

//----------------------------------------------------------------------
// Semaphore::P
/*!
//      Decrement the value, and wait if it becomes < 0. Checking the
//	value and decrementing must be done atomically, so we
//	need to disable interrupts before checking the value.
//
//	Note that Thread::Sleep assumes that interrupts are disabled
//	when it is called.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void
Semaphore::P() {
  printf(\n"**** Warning: method Semaphore::P is not implemented yet\n");
  exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Semaphore::P() {

	DEBUG('s', (char *)\n"A thread called P on %s semaphore\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Decrement value
	--value;

	// If no more tickets
	if (value < 0) {

		// Add this thread to the waiting ones
		queue->Append(g_current_thread);

		// And put it in sleep mode
		g_current_thread->Sleep();
	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif

//----------------------------------------------------------------------
// Semaphore::V
/*! 	Increment semaphore value, waking up a waiting thread if any.
//	As with P(), this operation must be atomic, so we need to disable
//	interrupts.  Scheduler::ReadyToRun() assumes that interrupts
//	are disabled when it is called.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void
Semaphore::V() {
   printf(\n"**** Warning: method Semaphore::V is not implemented yet\n");
    exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Semaphore::V() {

	DEBUG('s', (char *)\n"A thread called V on %s semaphore\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Decrement value
	++value;

	// If there are waiting threads
	if (!queue->IsEmpty()) {

		// Get the more ancient thread and put him in the ready list
		Thread *waiting_thread = (Thread *)queue->Remove();

		// And put it in ready threads list
		g_scheduler->ReadyToRun(waiting_thread);
	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif

//----------------------------------------------------------------------
// Lock::Lock
/*! 	Initialize a Lock, so that it can be used for synchronization.
//      The lock is initialy free
//  \param \n"debugName\n" is an arbitrary name, useful for debugging.
*/
//----------------------------------------------------------------------
Lock::Lock(char* debugName) {
  name = new char[strlen(debugName)+1];
  strcpy(name,debugName);
  sleepqueue = new Listint;
  free = true;
  owner = NULL;
  typeId = LOCK_TYPE_ID;
}


//----------------------------------------------------------------------
// Lock::~Lock
/*! 	De-allocate lock, when no longer needed. Assumes that no thread
//      is waiting on the lock.
*/
//----------------------------------------------------------------------
Lock::~Lock() {
  typeId = INVALID_TYPE_ID;
  ASSERT(sleepqueue->IsEmpty());
  delete [] name;
  delete sleepqueue;
}

//----------------------------------------------------------------------
// Lock::Acquire
/*! 	Wait until the lock become free.  Checking the
//	state of the lock (free or busy) and modify it must be done
//	atomically, so we need to disable interrupts before checking
//	the value of free.
//
//	Note that Thread::Sleep assumes that interrupts are disabled
//	when it is called.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void Lock::Acquire() {
   printf(\n"**** Warning: method Lock::Acquire is not implemented yet\n");
    exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Lock::Acquire() {

	DEBUG('s', (char *)\n"A thread asked to acquire lock on %s lock\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Check that the lock is free or not
	if (!free) {

		// Add this thread to the waiting ones
		sleepqueue->Append(g_current_thread);

		// And put it in sleep mode
		g_current_thread->Sleep();

	} else {  // If free

		// It takes the lock then
		free = false;

	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif

//----------------------------------------------------------------------
// Lock::Release
/*! 	Wake up a waiter if necessary, or release it if no thread is waiting.
//      We check that the lock is held by the g_current_thread.
//	As with Acquire, this operation must be atomic, so we need to disable
//	interrupts.  Scheduler::ReadyToRun() assumes that threads
//	are disabled when it is called.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void Lock::Release() {
    printf(\n"**** Warning: method Lock::Release is not implemented yet\n");
    exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Lock::Release() {

	DEBUG('s', (char *)\n"A thread asked to release lock on %s lock\n", getName());

	// Check that the lock is held by the current thread
	ASSERT(isHeldByCurrentThread());  // Normal because only the holder can call this method

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Put the state to free
	free = true;

	// If there are waiting threads
	if (!sleepqueue->IsEmpty()) {

		// Get the more ancient thread and put him in the ready list
		Thread *waiting_thread = (Thread *)sleepqueue->Remove();

		// And put it in ready threads list
		g_scheduler->ReadyToRun(waiting_thread);
	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif

//----------------------------------------------------------------------
// Lock::isHeldByCurrentThread
/*! To check if current thread hold the lock
*/
//----------------------------------------------------------------------
bool Lock::isHeldByCurrentThread() {return (g_current_thread == owner);}	

//----------------------------------------------------------------------
// Condition::Condition
/*! 	Initializes a Condition, so that it can be used for synchronization.
//
//    \param  \n"debugName\n" is an arbitrary name, useful for debugging.
*/
//----------------------------------------------------------------------
Condition::Condition(char* debugName) { 
  name = new char[strlen(debugName)+1];
  strcpy(name,debugName);
  waitqueue = new Listint;
  typeId = CONDITION_TYPE_ID;
}

//----------------------------------------------------------------------
// Condition::~Condition
/*! 	De-allocate condition, when no longer needed.
//      Assumes that nobody is waiting on the condition.
*/
//----------------------------------------------------------------------
Condition::~Condition() {
  typeId = INVALID_TYPE_ID;
  ASSERT(waitqueue->IsEmpty());
  delete [] name;
  delete waitqueue;
}

//----------------------------------------------------------------------
// Condition::Wait
/*! Block the calling thread (put it in the wait queue).
//  This operation must be atomic, so we need to disable interrupts.
*/	
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void Condition::Wait() { 
    printf(\n"**** Warning: method Condition::Wait is not implemented yet\n");
    exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Condition::Wait() {

	DEBUG('s', (char *)\n"A thread is put to wait on the %s condition\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Put the current thread in waiting list
	waitqueue->Append(g_current_thread);

	// And put it to sleep
	g_current_thread->Sleep();

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);

}
#endif

//----------------------------------------------------------------------
// Condition::Signal

/*! Wake up the first thread of the wait queue (if any). 
// This operation must be atomic, so we need to disable interrupts.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void Condition::Signal() { 
    printf(\n"**** Warning: method Condition::Signal is not implemented yet\n");
    exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Condition::Signal() {

	DEBUG('s', (char *)\n"Wake the first waiting thread on the %s condition\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// If there are waiting threads
	if (!waitqueue->IsEmpty()) {

		// Get the more ancient thread and put him in the ready list
		Thread *waiting_thread = (Thread *)waitqueue->Remove();

		// And put it in ready threads list
		g_scheduler->ReadyToRun(waiting_thread);
	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif

//----------------------------------------------------------------------
/*! Condition::Broadcast
// wake up all threads waiting in the waitqueue of the condition
// This operation must be atomic, so we need to disable interrupts.
*/
//----------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void Condition::Broadcast() { 
  printf(\n"**** Warning: method Condition::Broadcast is not implemented yet\n");
  exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void Condition::Broadcast() {

	DEBUG('s', (char *)\n"Wake the first waiting thread on the %s condition\n", getName());

	// Disable interrupts and save the previous state
	IntStatus previous_int_status = g_machine->interrupt->SetStatus(INTERRUPTS_OFF);

	// Wake them all
	Thread *waiting_thread;
	while (!waitqueue->IsEmpty()) {

		// Get the more ancient thread and put him in the ready list
		waiting_thread = (Thread *)waitqueue->Remove();

		// And put it in ready threads list
		g_scheduler->ReadyToRun(waiting_thread);
	}

	// Put back the previous state of interrupts
	g_machine->interrupt->SetStatus(previous_int_status);
}
#endif