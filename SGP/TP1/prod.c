/*
	TP1 - SGF (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
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

	// Create a pipe here
	if (mknod("prod-cons-bis", S_IFIFO | 0666, 0) == -1) {
		fprintf(stderr, "%s\n", "Error during the creation of the named pipe.");
		return -1;
	}

	// Open the pipe
	int named_pipe = open("prod-cons", O_WRONLY);
	if (named_pipe == -1) {
		fprintf(stderr, "%s\n", "Error during the opening of the named pipe.");
		return -1;
	}

	// The buffer for the i/o
	char buffer[MAX_SIZE];

	// The result value
	int result_write;

	// Get the chars input
	do {

		// Clear the buffer
		bzero(buffer, MAX_SIZE);

		// Get input
		fgets(buffer, (MAX_SIZE/sizeof(char)), stdin);

		// Send the message to the pipe
		result_write = write(named_pipe, buffer, MAX_SIZE);

	} while(strcmp(buffer, "\n") != 0);

	// Display if an error occurred
	if (result_write == -1) {
		fprintf(stderr, "%s\n", "Error during the reading in the pipe.");
		return -1;
	}

	return 0;

}