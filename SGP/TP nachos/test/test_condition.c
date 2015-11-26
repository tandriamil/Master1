/**
 *
 * Simple test case for the conditions synchronisation
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

// Table used here
#define NUM 30
int tab[NUM];


// Function to fill the table
void fillThread(int c) {

	// Transform the lock
	CondId cond = (int)c;

	tab[0] = 0;
	tab[1] = 1;
	
	int i;
	int j;
	for (i = 2; i < NUM; i++) {
		tab[i] = 0;

		for (j = (i-1); j > 0; j--) {
			tab[i] += tab[j];
		}
	}

	// Signal the cond
	if (CondSignal(cond) < 0)
		n_printf("Error signal condition");
}


// Function to read the table
void readThread(int c) {

	// Transform the lock
	CondId cond = (int)c;

	// Wait our condition
	if (CondWait(cond) < 0)
		n_printf("Error condition wait");
	
	int i;
	for (i = 0; i < NUM; i++) {
		n_printf("Tab[%d] = %d\n", i, tab[i]);
	}
}


/**
 * Main function
 */
int main() {

	// We create a condition
	CondId cond = CondCreate("TableCondition");

	// We create a thread to fill it and one to read it
	ThreadId thread_fill = newThread("FillThread", (int)fillThread, (int)cond);
	ThreadId thread_read = newThread("ReadThread", (int)readThread, (int)cond);

	// Wait the two threads before returning
	if (Join(thread_fill) < 0)
		n_printf("Error joining first thread");

	if (Join(thread_read) < 0)
		n_printf("Error joining second thread");

	// Delete the condition
	if (CondDestroy(cond) < 0)
		n_printf("Error destroy lock");

	// Exit the program
	return 0;
}