/*
	TP2 - SGF (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <setjmp.h>


// Constants
#define MAX_SIZE 1024  // Max input size


// Global vars
int i, set_jmp_counter;
jmp_buf env;


/**
 * The function to be thrown when the alarm is received
 */
void alarm_received() {
	fprintf(stderr, "%s%d.\n", "The current value of i is ", i);
	++i;
	alarm(1);
}


/**
 * The function to be thrown when user press CTRL + C
 */
void ctrl_c_received() {
	set_jmp_counter++;
	longjmp(env, set_jmp_counter);
}


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Put the alarm
	alarm(1);

	// Put the function to run when the alarm is got
	if (signal(SIGALRM, alarm_received) == SIG_ERR) {
		fprintf(stderr, "%s\n", "The signal treatment setting for alarm failed.");
	} else {
		fprintf(stderr, "%s\n", "The signal treatment setting for alarm is correctly done.");
	}

	// Put the function to run when the signal of CTRL + C is got
	if (signal(SIGINT, ctrl_c_received) == SIG_ERR) {
		fprintf(stderr, "%s\n", "The signal treatment setting for CTRL C failed.");
	} else {
		fprintf(stderr, "%s\n", "The signal treatment setting for CTRL C is correctly done.");
	}

	// Set the jmp point
	set_jmp_counter = setjmp(env);
	fprintf(stderr, "%s%d.\n", "The value of setjmp is ", set_jmp_counter);

	// Deblock the ctrl_c_received blocked signal
	if (set_jmp_counter > 0) {
		if (sigprocmask(SIG_BLOCK, (int)(SIGINT), NULL) == 0) fprintf(stderr, "%s\n", "Maskage success.");
		else fprintf(stderr, "%s\n", "Maskage failed.");
	}

	// Set i to 0
	i = 0;

	// Infinite
	while (1);
	
	// End
	return 0;

}