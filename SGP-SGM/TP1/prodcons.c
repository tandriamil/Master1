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


// Constants
#define MAX_SIZE 1024  // Max input size


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Create a pipe here
	int fd[2];
	if (pipe(fd) < 0) {
		fprintf(stderr, "%s\n", "Error during the creation of the pipe.");
		return 2;
	}

	// Do the fork here
	pid_t result_fork = fork();

	// The buffer for the i/o
	char buffer[MAX_SIZE];



	// If the SON (the consumer)
	if (result_fork == 0) {

		// Close the unused write end
		close(fd[1]);

		// The result value
		int result_read;

		// Get the chars input
		do {

			// Clear the buffer
			bzero(buffer, MAX_SIZE);

			// Get input from the pipe
			result_read = read(fd[0], buffer, MAX_SIZE);

			// Display the message received
			fprintf(stdout, "%s", buffer);

		} while(result_read > 0);

		// End of process, close the fd
		close(fd[0]);

		// Display if an error occurred
		if (result_read == -1) {
			fprintf(stderr, "%s\n", "Error during the reading in the pipe.");
			return -1;
		}

	}



	// If the FATHER (the producer)
	else {

		// Close the unused read end
		close(fd[0]);

		// The result value
		int result_write;

		// Get the chars input
		do {

			// Clear the buffer
			bzero(buffer, MAX_SIZE);

			// Get input
			fgets(buffer, (MAX_SIZE/sizeof(char)), stdin);

			// Send the message to the pipe
			result_write = write(fd[1], buffer, MAX_SIZE);

		} while(strcmp(buffer, "\n") != 0);

		// End of process, close the fd
		close(fd[1]);

		// Display if an error occurred
		if (result_write == -1) {
			fprintf(stderr, "%s\n", "Error during the reading in the pipe.");
			return -1;
		}

	}

	// Normally, we never go here but needed for compilation
	return 0;

}
