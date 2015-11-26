/**
 *
 * Simple test case for the locks synchronisation
 *
 * ANDRIAMILANTO Tompoariniaina
 * IHSINE Azzeddine
 *
 * TP SGP nachos
 * 2015 - 2016
 *
 */

// Nachos system calls
#include "userlib/syscall.h"
#define MAX_THREADS
ThreadId threads[MAX_THREADS];
int i = 1;


// Function to say who is each threads
void whoAmI(int l) {

	// Transform the lock
	(LockId)lock = l;

	// Take the lock
	if (LockAcquire(lock) < 0)
		n_printf("Error acquire lock");

	// Wait and then display who he is
	n_printf("I am the thread number " + i + "!\n");
	i++;

	// Release the lock
	if (LockRelease(lock) < 0)
		n_printf("Error release lock");

}


/**
 * Main function
 */
int main() {
	
	// Create a lock
	LockId lock = LockCreate("Lock");

	// Create a lot of threads
	int j;
	for(j = 0; j < MAX_THREADS; j++) {
		threads[j] = newThread("Threads j=" + j, whoAmI, (int)lock);
	}

	// Wait each threads
	for (j = 0; j < MAX_THREADS; j++) {
		if (Join(threads[j]) < 0)
			n_printf("Error joining thread j=" + j);
	}

	// Destroy the lock
	if (LockDestroy(lock) < 0)
		n_printf("Error joining thread j=" + j);

	// Exit the program
	Exi(0);
}