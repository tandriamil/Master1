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


// Constants
#define MAX_I_VALUE 42  // Max i value


// Global var for i
int i = 0;


/**
 * The function to be thrown when the alarm is received
 */
void alarm_received() {
	fprintf(stderr, "%s%d.\n", "INFO: The current value of i is ", i);
	++i;
	alarm(1);
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
	if (signal(SIGALRM, alarm_received) == SIG_ERR) fprintf(stderr, "%s\n", "FAIL: Signal treatment setting");
	else fprintf(stderr, "%s\n", "SUCCESS: Signal treatment setting");

	// Infinite
	fprintf(stdout, "%s\n", "The program will automatically stop when i reach 42.");
	while (i <= MAX_I_VALUE);

	// End
	return 0;

}
