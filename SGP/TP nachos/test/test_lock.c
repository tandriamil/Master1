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
#include "userlib/libnachos.h"
#define MAX_THREADS 10
ThreadId threads[MAX_THREADS];
int i = 1;


// Function to say who is each threads
void whoAmI(int l) {

	// Transform the lock
	LockId lock = (LockId)l;
	int x;

	// Take the lock
	if (LockAcquire(lock) < 0)
		n_printf("Error acquire lock");

	// Wait and then display who he is
	n_printf("I am the thread number %d!\n", i);
	x = i;
	i++;

	// Release the lock
	if (LockRelease(lock) < 0)
		n_printf("Error release lock");

	n_printf("End of thread n°%d\n", x);

	Exit(0);

}


/**
 * Main function
 */
int main() {
	
	// Create a lock
	LockId lock = LockCreate("Lock");

	// Take the lock
	if (LockAcquire(lock) < 0)
		n_printf("Error acquire lock");

	// Create a lot of threads
	int j;
	for(j = 0; j < MAX_THREADS; j++) {
		n_printf("Thread n°%d, lock = %d and whoAmI = %d \n", j, (int)lock, (int)whoAmI);
		threads[j] = newThread("Thread", (int)whoAmI, (int)lock);
	}

	// Release the lock
	n_printf("End of threads creating, now we release the lock\n");
	if (LockRelease(lock) < 0)
		n_printf("Error release lock");
	n_printf("The main lock is finally released\n");

	// Wait each threads
	for (j = 0; j < MAX_THREADS; j++) {
		if (Join(threads[j]) < 0)
			n_printf("Error joining thread j=%d", j);
	}

	// Destroy the lock
	if (LockDestroy(lock) < 0)
		n_printf("Error destroying the lock");

	// Exit the program
	return 0;
}