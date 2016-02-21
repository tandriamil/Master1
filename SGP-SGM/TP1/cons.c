/*
	TP1 - SGF (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>


// Constants
#define MAX_SIZE 1024  // Max input size


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// The buffer for the i/o
	char buffer[MAX_SIZE];

	// Open the pipe
	int named_pipe = open("prod-cons", O_RDONLY);
	if (named_pipe == -1) {
		fprintf(stderr, "%s\n", "Error during the opening of the named pipe.");
		return -1;
	}

	// The result value
	int result_read;

	// Get the chars input
	do {

		// Clear the buffer
		bzero(buffer, MAX_SIZE);

		// Get input from the pipe
		result_read = read(named_pipe, buffer, MAX_SIZE);

		// Display the message received
		fprintf(stdout, "%s", buffer);

	} while(strcmp(buffer, "\n") != 0);

	// Display if an error occurred
	if (result_read == -1) {
		fprintf(stderr, "%s\n", "Error during the reading in the pipe.");
		return -1;
	}

	// End of process, close the fd
	close(named_pipe);

	// Remove the created file
	if (remove("prod-cons") != 0) {
		fprintf(stderr, "%s\n", "Error during the delete of the file created.");
		return -1;
	}

	return 0;

}