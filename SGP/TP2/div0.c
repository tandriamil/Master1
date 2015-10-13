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
#define MAX_SIZE 1024  // Max input size


/**
 * The function to be thrown when the divby0 IT is got
 */
void div_by_zero() {
	fprintf(stderr, "%s\n", "SIGFPE catched! You tried to divide by 0!");
	exit(-1);
}


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Put the function to run when the signal of divby0 is got
	if (signal(SIGFPE, div_by_zero) == SIG_ERR) {
		fprintf(stderr, "%s\n", "The signal treatment setting failed.");
	} else {
		fprintf(stderr, "%s\n", "The signal treatment setting is correctly done.");
	}

	// Some variables
	int i = 0;
	int first, second;

	// Ask for user inputs
	do {

		if (i == 0) {
			// If first operand
			fprintf(stdout, "%s\n", "Please enter first number:");

			// Get input
			scanf("%d", &first);
			++i;
		} else {
			// If second operand
			fprintf(stdout, "%s\n", "Please enter second number:");

			// Get input
			scanf("%d", &second);

			// Display the result
			fprintf(stdout, "%s%d.\n", "The result is ", (first / second));
			--i;
		}

	} while(first > 0);
	
	// End
	fprintf(stderr, "%s\n", "End of the process.");
	return 0;

}