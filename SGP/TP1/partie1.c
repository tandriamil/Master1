/*
	TP1 - SGF (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Do the fork here
	pid_t result_fork = fork();

	// If the SON
	if (result_fork == 0)  // Execute the function passed
		execl("./partie2", "./partie2", NULL);

	// If the FATHER
	else {

		// Wait the son to terminate
		int status;
		wait(&status);
		
		// Bad implementation, just wait() is enough
		//while ((result_wait = wait(&status)) > 0) { }

		// If an error occured
		fprintf(stdout, "%s%d.\n", "The son exited with code ", status);

		// Return the execution result
		return status;
	}

	// Normally, we never go here but needed for compilation
	return 0;

}