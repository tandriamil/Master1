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
#define MAX_I_VALUE 42  // Max i value


// Global vars
int i, set_jmp_counter;
jmp_buf env;


/**
 * The function to be thrown when the alarm is received
 */
void alarm_received() {
	fprintf(stderr, "%s%d.\n", "INFO: The current value of i is ", i);
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
	if (signal(SIGALRM, alarm_received) == SIG_ERR) fprintf(stderr, "%s\n", "FAIL: Signal treatment setting for alarm");
	else fprintf(stderr, "%s\n", "SUCCESS: Signal treatment setting for alarm");

	// Put the function to run when the signal of CTRL + C is got
	if (signal(SIGINT, ctrl_c_received) == SIG_ERR) fprintf(stderr, "%s\n", "FAIL: The signal treatment setting for CTRL-C");
	else fprintf(stderr, "%s\n", "SUCCESS: The signal treatment setting for CTRL-C");

	// Set the jmp point
	set_jmp_counter = setjmp(env);
	fprintf(stderr, "%s%d.\n", "INFO: The value of setjmp is ", set_jmp_counter);

	// Deblock the ctrl_c_received blocked signal
	if (set_jmp_counter > 0) {

		// Add the SIGINT to the sigset to deblock
		sigset_t sigtomask;
		if (sigaddset(&sigtomask, SIGINT) == 0) fprintf(stderr, "%s\n", "SUCCESS: Creating the sigset");
		else fprintf(stderr, "%s\n", "FAIL: Creating the sigset");

		// Mask the signal SIGINT
		if (sigprocmask(SIG_UNBLOCK, &sigtomask, NULL) == 0) fprintf(stderr, "%s\n", "SUCCESS: Masking the signal SIGINT");
		else fprintf(stderr, "%s\n", "FAIL: Masking the signal SIGINT");
	}

	// Set i to 0
	i = 0;

	// Infinite
	fprintf(stdout, "%s\n", "The program will automatically stop when i reach 42.");
	while (i <= MAX_I_VALUE);

	// End
	return 0;

}
