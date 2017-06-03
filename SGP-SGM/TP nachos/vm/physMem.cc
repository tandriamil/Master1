//-----------------------------------------------------------------
/*! \file mem.cc
//  \brief Routines for the physical page management
*/
//
//  Copyright (c) 1999-2000 INSA de Rennes.
//  All rights reserved.
//  See copyright_insa.h for copyright notice and limitation
//  of liability and disclaimer of warranty provisions.
//-----------------------------------------------------------------

#include <unistd.h>
#include "vm/physMem.h"

//-----------------------------------------------------------------
// PhysicalMemManager::PhysicalMemManager
//
/*! Constructor. It simply clears all the page flags and inserts them in the
// free_page_list to indicate that the physical pages are free
*/
//-----------------------------------------------------------------
PhysicalMemManager::PhysicalMemManager() {

  long i;

  tpr = new struct tpr_c[g_cfg->NumPhysPages];

  for (i=0;i<g_cfg->NumPhysPages;i++) {
    tpr[i].free=true;
    tpr[i].locked=false;
    tpr[i].owner=NULL;
    free_page_list.Append((void*)i);
  }
  i_clock=-1;
}

PhysicalMemManager::~PhysicalMemManager() {
  // Empty free page list
  int64_t page;
  while (!free_page_list.IsEmpty()) page =  (int64_t)free_page_list.Remove();

  // Delete physical page table
  delete[] tpr;
}

//-----------------------------------------------------------------
// PhysicalMemManager::RemovePhysicalToVitualMapping
//
/*! This method releases an unused physical page by clearing the
//  corresponding bit in the page_flags bitmap structure, and adding
//  it in the free_page_list.
//
//  \param num_page is the number of the real page to free
*/
//-----------------------------------------------------------------
void PhysicalMemManager::RemovePhysicalToVirtualMapping(long num_page) {

  // Check that the page is not already free
  ASSERT(!tpr[num_page].free);

  // Update the physical page table entry
  tpr[num_page].free=true;
  tpr[num_page].locked=false;
  if (tpr[num_page].owner->translationTable!=NULL)
    tpr[num_page].owner->translationTable->clearBitValid(tpr[num_page].virtualPage);

  // Insert the page in the free list
  free_page_list.Prepend((void*)num_page);
}

//-----------------------------------------------------------------
// PhysicalMemManager::UnlockPage
//
/*! This method unlocks the page numPage, after
//  checking the page is in the locked state. Used
//  by the page fault manager to unlock at the
//  end of a page fault (the page cannot be evicted until
//  the page fault handler terminates).
//
//  \param num_page is the number of the real page to unlock
*/
//-----------------------------------------------------------------
void PhysicalMemManager::UnlockPage(long num_page) {
  ASSERT(num_page<g_cfg->NumPhysPages);
  ASSERT(tpr[num_page].locked==true);
  ASSERT(tpr[num_page].free==false);
  tpr[num_page].locked = false;
}

//-----------------------------------------------------------------
// PhysicalMemManager::ChangeOwner
//
/*! Change the owner of a page
//
//  \param owner is a pointer on new owner (Thread *)
//  \param numPage is the concerned page
*/
//-----------------------------------------------------------------
void PhysicalMemManager::ChangeOwner(long numPage, Thread* owner) {
  // Update statistics
  g_current_thread->GetProcessOwner()->stat->incrMemoryAccess();
  // Change the page owner
  tpr[numPage].owner = owner->GetProcessOwner()->addrspace;
}

//-----------------------------------------------------------------
// PhysicalMemManager::AddPhysicalToVirtualMapping
//
/*! This method returns a new physical page number. If there is no
//  page available, it evicts one page (page replacement algorithm).
//
//  NB: this method locks the newly allocated physical page such that
//      it is not stolen during the page fault resolution. Don't forget
//      to unlock it
//
//  \param owner address space (for backlink)
//  \param virtualPage is the number of virtualPage to link with physical page
//  \return A new physical page number.
*/
//-----------------------------------------------------------------
#ifndef ETUDIANTS_TP
int PhysicalMemManager::AddPhysicalToVirtualMapping(AddrSpace* owner,int virtualPage)
{
  printf("**** Warning: function AddPhysicalToVirtualMapping is not implemented\n");
  exit(-1);
  return (0);
}
#endif

#ifdef ETUDIANTS_TP
int PhysicalMemManager::AddPhysicalToVirtualMapping(AddrSpace* owner, int virtualPage) {

	// Assert that the virtual page is locked for the page fault process
	ASSERT(g_machine->mmu->translationTable->getBitIo(virtualPage));

	// Get a page in physical memory, evict one if none free
	int pp = FindFreePage();
	if (pp == -1) pp = EvictPage();

	// Lock the page for us
	tpr[pp].locked = true;

	// Link this page to the given virtual page
	tpr[pp].virtualPage = virtualPage;
	tpr[pp].owner = owner;

	// Return the index of the physical page
	return pp;
}
#endif


//-----------------------------------------------------------------
// PhysicalMemManager::FindFreePage
//
/*! This method returns a new physical page number, if it finds one
//  free. If not, return -1. Does not run the clock algorithm.
//
//  \return A new free physical page number.
*/
//-----------------------------------------------------------------
int PhysicalMemManager::FindFreePage() {
  int64_t page;

  // Check that the free list is not empty
  if (free_page_list.IsEmpty())
    return -1;

  // Update statistics
  g_current_thread->GetProcessOwner()->stat->incrMemoryAccess();

  // Get a page from the free list
  page = (int64_t)free_page_list.Remove();

  // Check that the page is really free
  ASSERT(tpr[page].free);

  // Update the physical page table
  tpr[page].free = false;

  return page;
}


//-----------------------------------------------------------------
// PhysicalMemManager::EvictPage
//
/*! This method implements page replacement, using the well-known
//  clock algorithm.
//
//  \return A new free physical page number.
*/
//-----------------------------------------------------------------
#ifndef ETUDIANTS_TP
int PhysicalMemManager::EvictPage() {
  printf("**** Warning: page replacement algorithm is not implemented yet\n");
    exit(-1);
    return (0);
}
#endif

#ifdef ETUDIANTS_TP
int PhysicalMemManager::EvictPage() {

	DEBUG('m', (char *)"Entering EvictPage() with iClock = %d\n", i_clock);

	// Get the global i_clock and put it into our system
	int local_i_clock = (i_clock + 1) % g_cfg->NumPhysPages,
	beginning = (local_i_clock - 1) % g_cfg->NumPhysPages;

	// Search a page that isn't locked or used recently
	while ((tpr[local_i_clock].owner->translationTable->getBitU(tpr[local_i_clock].virtualPage)) || (tpr[local_i_clock].locked)) {

		// Put the U bit back to 0
		tpr[local_i_clock].owner->translationTable->clearBitU(tpr[local_i_clock].virtualPage);

		// Go to the next physical page in circular way
		local_i_clock = (local_i_clock + 1) % g_cfg->NumPhysPages;

		// If we got back to the beginning, we didn't found a page
		if (local_i_clock == beginning) {

			// Put the current thread at the end of the active thread lists
			// Run all the other active threads until going back to this one
			g_current_thread->Yield();

		}
	}

	// Invalid the virtual page associated to it and lock the page for us
	tpr[local_i_clock].owner->translationTable->clearBitValid(tpr[local_i_clock].virtualPage);
	tpr[local_i_clock].locked = true;

	// Update the global clock
	i_clock = local_i_clock;

	// If the physical page was modified
	if (tpr[local_i_clock].owner->translationTable->getBitM(tpr[local_i_clock].virtualPage)) {

		// If this page is already in the swap
		if (tpr[local_i_clock].owner->translationTable->getBitSwap(tpr[local_i_clock].virtualPage)) {

			// Change the swap page index
			g_swap_manager->PutPageSwap(tpr[local_i_clock].owner->translationTable->getAddrDisk(tpr[local_i_clock].virtualPage), (char *)&g_machine->mainMemory[local_i_clock * g_cfg->PageSize]);
		}

		// If not, put it in then
		else {

			// Check if this page is in a mapped file
			OpenFile *mapped_file = tpr[local_i_clock].owner->findMappedFile(tpr[local_i_clock].virtualPage * g_cfg->PageSize);

			// If in a mapped file
			if (mapped_file != NULL) {

				// Write the datas into the mapped file
				mapped_file->WriteAt(
					(char *)&g_machine->mainMemory[local_i_clock * g_cfg->PageSize],
					g_cfg->PageSize,
					tpr[local_i_clock].owner->translationTable->getAddrDisk(tpr[local_i_clock].virtualPage)
				);

				// Update virtual page state as stored into swap
				tpr[local_i_clock].owner->translationTable->clearBitM(tpr[local_i_clock].virtualPage);
			}

			// If not in mapped file
			else {

				// Put this page into the swap
				int swap_sector = g_swap_manager->PutPageSwap(-1, (char *)&g_machine->mainMemory[local_i_clock * g_cfg->PageSize]);

				// If there was an error
				if (swap_sector == -1) {
					DEBUG('h', (char *)"Tryed to put a swap page in EvictPage() method but failed, return code is %d\n", swap_sector);
					g_machine->interrupt->Halt(-1);
				}

				// Update virtual page state as stored into swap
				tpr[local_i_clock].owner->translationTable->setAddrDisk(tpr[local_i_clock].virtualPage, swap_sector);
				tpr[local_i_clock].owner->translationTable->setBitSwap(tpr[local_i_clock].virtualPage);
				tpr[local_i_clock].owner->translationTable->clearBitM(tpr[local_i_clock].virtualPage);
			}
		}

	}

	// Return the free physical page
	return local_i_clock;
}
#endif


//-----------------------------------------------------------------
// PhysicalMemManager::Print
//
/*! print the current status of the table of physical pages
//
//  \param rpage number of real page
*/
//-----------------------------------------------------------------

void PhysicalMemManager::Print(void) {
  int i;

  printf("Contents of TPR (%d pages)\n",g_cfg->NumPhysPages);
  for (i=0;i<g_cfg->NumPhysPages;i++) {
    printf("Page %d free=%d locked=%d virtpage=%d owner=%lx U=%d M=%d\n",
	   i,
	   tpr[i].free,
	   tpr[i].locked,
	   tpr[i].virtualPage,
	   (long int)tpr[i].owner,
	   (tpr[i].owner!=NULL) ? tpr[i].owner->translationTable->getBitU(tpr[i].virtualPage) : 0,
	   (tpr[i].owner!=NULL) ? tpr[i].owner->translationTable->getBitM(tpr[i].virtualPage) : 0);
  }
}
