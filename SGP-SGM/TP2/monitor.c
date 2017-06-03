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
#include <sys/ptrace.h>
#include <sys/wait.h>


// Constants
#define MAX_I_VALUE 42  // Max i value


/**
 * The function to be thrown when the alarm is received
 */
void alarm_received() {
	alarm(2);
}


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// The value of the counter
	int i;

	// Do the fork here
	pid_t result_fork = fork();

	// If the SON
	if (result_fork == 0) {
		ptrace(PTRACE_TRACEME, NULL, NULL, NULL);

		// Set i to 0
		i = 0;

		// Infinite
		while (i <= MAX_I_VALUE) {
			alarm(1);
			++i;
			sleep(1);
		}
	}

	// If the FATHER
	else {

		// Put the function to run when the alarm is got
		if (signal(SIGALRM, alarm_received) == SIG_ERR) fprintf(stderr, "%s\n", "FAIL: Signal treatment setting for alarm");
		else fprintf(stderr, "%s\n", "SUCCESS: Signal treatment setting for alarm");

		// Put the alarm
		alarm(2);

		while (i <= MAX_I_VALUE) {

			// Wait the son to terminate
			wait(NULL);

			// The son just terminated, we'll just watch the current state
			int result_ptrace = (int)ptrace(PTRACE_PEEKDATA, result_fork, &i, NULL);
			if (result_ptrace == -1) fprintf(stderr, "%s\n", "FAIL: Status reading on the son");
			else fprintf(stderr, "%s\n", "SUCCESS: Status reading on the son");

			// Display the value of i
			fprintf(stdout, "%s%d.\n", "The value of i read by the father is ", result_ptrace);

			// And in the end, tell the son that he can continue
			if (ptrace(PTRACE_CONT, result_fork, NULL, NULL) == -1) fprintf(stderr, "%s\n", "FAIL: Letting the son continue");
			else fprintf(stderr, "%s\n", "SUCCESS: Letting the son continue");
		}

	}

	// End
	return 0;

}
