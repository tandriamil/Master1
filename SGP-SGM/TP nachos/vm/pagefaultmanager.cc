/*! \file pagefaultmanager.cc
Routines for the page fault managerPage Fault Manager
*/
//
//  Copyright (c) 1999-2000 INSA de Rennes.
//  All rights reserved.  
//  See copyright_insa.h for copyright notice and limitation 
//  of liability and disclaimer of warranty provisions.
//

#include "kernel/thread.h"
#include "vm/swapManager.h"
#include "vm/physMem.h"
#include "vm/pagefaultmanager.h"

PageFaultManager::PageFaultManager() {
}

// PageFaultManager::~PageFaultManager()
/*! Nothing for now
*/
PageFaultManager::~PageFaultManager() {
}

// ExceptionType PageFault(int virtualPage)
/*! 	
//	This method is called by the Memory Management Unit when there is a 
//      page fault. This method loads the page from :
//      - read-only sections (text,rodata) $\Rightarrow$ executive
//        file
//      - read/write sections (data,...) $\Rightarrow$ executive
//        file (1st time only), or swap file
//      - anonymous mappings (stack/bss) $\Rightarrow$ new
//        page from the MemoryManager (1st time only), or swap file
//
//	\param virtualPage the virtual page subject to the page fault
//	  (supposed to be between 0 and the
//        size of the address space, and supposed to correspond to a
//        page mapped to something [code/data/bss/...])
//	\return the exception (generally the NO_EXCEPTION constant)
*/
#ifndef ETUDIANTS_TP
ExceptionType PageFaultManager::PageFault(int virtualPage) 
{
  printf("**** Warning: page fault manager is not implemented yet\n");
    exit(-1);
    return ((ExceptionType)0);
}
#endif

#ifdef ETUDIANTS_TP
ExceptionType PageFaultManager::PageFault(int virtualPage) {

	// If we have to get the section from the memory
	int position_on_disk = g_machine->mmu->translationTable->getAddrDisk(virtualPage);
	if (position_on_disk != -1) {

		// Read it then
		if (!g_machine->mmu->ReadMem(virtualPage, g_cfg->PageSize, (int *)&g_machine->mainMemory[g_machine->mmu->translationTable->getPhysicalPage(virtualPage) * g_cfg->PageSize], false)) {
			return PAGEFAULT_EXCEPTION;
		}

	} else {

		// Fill with 0
		memset(&(g_machine->mainMemory[g_machine->mmu->translationTable->getPhysicalPage(virtualPage) * g_cfg->PageSize]), 0, g_cfg->PageSize);

	}

	// If everything's fine
	return NO_EXCEPTION;

}
#endif