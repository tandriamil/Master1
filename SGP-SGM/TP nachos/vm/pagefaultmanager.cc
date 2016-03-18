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

	// Here, the thread is trying to take care of a page fault
	// which another thread is already taking care
	// This thread then should wait till the other one finished
	while (g_machine->mmu->translationTable->getBitIo(virtualPage)) {

		// Put the current thread at the end of the active thread lists
		// Run all the other active threads until going back to this one
		g_current_thread->Yield();

	}

	// Block this virtual page from getting resolved by other processes
	g_machine->mmu->translationTable->setBitIo(virtualPage);

	// Create a temporary page here
	char temporary_page[g_cfg->PageSize];

	// Get a physical page
	int phys_page_id = g_physical_mem_manager->AddPhysicalToVirtualMapping(g_current_thread->GetProcessOwner()->addrspace, virtualPage);

	// If it's stored in the swap (swap bit = 1)
	if (g_machine->mmu->translationTable->getBitSwap(virtualPage)) {

		// A page stealer is dealing with the current page
		while (g_machine->mmu->translationTable->getAddrDisk(virtualPage) == -1) {

			// Put the current thread at the end of the active thread lists
			// Run all the other active threads until going back to this one
			g_current_thread->Yield();

		}

		// Get the real page from the swap
		g_swap_manager->GetPageSwap(g_machine->mmu->translationTable->getAddrDisk(virtualPage), temporary_page);

	} else {  // If stored in the disk (swap bit = 0)

		// If anonymous page
		if (g_machine->mmu->translationTable->getAddrDisk(virtualPage) == -1) {

			// Fill with 0
			memset(&(g_machine->mainMemory[phys_page_id * g_cfg->PageSize]), 0, g_cfg->PageSize);

		} else {

			DEBUG('p', (char *)"Searching @%d in the page fault from disk management\n", virtualPage);
			// Check if mapped file  (OR MAYBE HERE IT IS virtualPage * g_cfg->PageSize)
			OpenFile *mapped_file = g_current_thread->GetProcessOwner()->addrspace->findMappedFile(virtualPage);

			// If in a mapped file
			if (mapped_file != NULL) {

				DEBUG('p', (char *)"Entered in the mapped file read at\n");

				// Read it from the mapped file
				mapped_file->ReadAt(
					temporary_page,
					g_cfg->PageSize,
					g_machine->mmu->translationTable->getAddrDisk(virtualPage)
				);
			}

			// If an executable read from the disk
			else {

				// Read it from the disk
				g_current_thread->GetProcessOwner()->exec_file->ReadAt(
					temporary_page,
					g_cfg->PageSize,
					g_machine->mmu->translationTable->getAddrDisk(virtualPage)
				);
			}
		}
	}

	// Copy the temporary page into the real page
	memcpy(&g_machine->mainMemory[phys_page_id * g_cfg->PageSize], temporary_page, g_cfg->PageSize);

	// Put this physical page as the one for this virtual page
	g_machine->mmu->translationTable->setPhysicalPage(virtualPage, phys_page_id);

	// Put the valid bit to 1 and the io bit to 0 (unlock the page)
	g_machine->mmu->translationTable->setBitValid(virtualPage);
	g_machine->mmu->translationTable->clearBitIo(virtualPage);

	// Unlock the physical page
	g_physical_mem_manager->UnlockPage(phys_page_id);

	// If everything's fine
	return NO_EXCEPTION;

}
#endif