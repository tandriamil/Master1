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


/**
 * The function to be thrown when the divby0 IT is got
 */
void div_by_zero() {
	fprintf(stdout, "%s\n", "SIGFPE catched! You tried to divide by 0!");
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
	if (signal(SIGFPE, div_by_zero) == SIG_ERR) fprintf(stderr, "%s\n", "FAIL: Signal treatment setting");
	else fprintf(stderr, "%s\n", "SUCCESS: Signal treatment setting");

	// Some variables
	int i = 0;
	int first, second;

	// Ask for user inputs
	do {

		if (i == 0) {

			// New division
			fprintf(stdout, "\n%s\n", "New division,");

			// If first operand
			fprintf(stdout, "%s\n", "Please enter the dividend (a negative one quit the program):");

			// Get input
			scanf("%d", &first);
			++i;
		} else {
			// If second operand
			fprintf(stdout, "%s\n", "Please enter the divisor:");

			// Get input
			scanf("%d", &second);

			// Display the result
			fprintf(stdout, "%s%d.\n", "The result of the division is ", (first / second));
			--i;
		}

	} while(first > 0);
	
	// End
	return 0;

}