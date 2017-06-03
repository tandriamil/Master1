/*!
	TP4 - SGM (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/


// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/mman.h>


// Constants
#define PAGES_PER_MATRIX 1
#define ROW_SIZE getpagesize() * PAGES_PER_MATRIX
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE ROW_SIZE
#define NB_THREADS 8
#define NB_ACCESS_TRIES 10
#define FILENAME "mmap.txt"


// Matrix, array and counter as global variable
int * matrix;
int access_tries = 0;


/*!
 * Generate a random int
 * \return A random int
 */
int random_int() {
	return rand()%(RAND_MAX_VALUE - RAND_MIN_VALUE) + RAND_MIN_VALUE;
}


/*!
 * Convert a two dimensional matrix into a one dimensional one
 *
 * \param beginning The beginning for the offset
 * \param x The x position of the cell
 * \param y The y position of the cell
 *
 * \return A pointer to the wanted memory cell
 */
int * cell(int * beginning, int x, int y) {
	return beginning + y * ROW_SIZE + x;
}


/*!
 * Initialize the matrix
 */
void init_matrix() {

	// Allocate the matrix
	matrix = malloc(ROW_SIZE * ROW_SIZE * sizeof(int));

	// Initialize it
	int i;
	for (i = 0; i < (ROW_SIZE * ROW_SIZE); ++i) matrix[i] = random_int();
}


void new_sigsev_action(int sig, siginfo_t *siginfo, void *context) {

	// Check the signal got
	if (sig != SIGSEGV) fprintf(stderr, "Wrong signal caught! Problem...\n");

	fprintf(stderr, "New signal handler launched with signal %d and access_tries = %d and @%p\n", sig, access_tries, siginfo->si_addr);

	// Increment the access tries counter
	++access_tries;

	// Put back the write rights for this memory zone
	if (mprotect(siginfo->si_addr, sizeof(int), PROT_READ|PROT_WRITE) == -1) fprintf(stderr, "Error during the restablissment of the rights over the @%p\n", siginfo->si_addr);

	// Wait a little before leaving
	sleep(1);
}


/*!
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Open a file
	int file = open(FILENAME, O_RDWR|O_TRUNC|O_CREAT, S_IRUSR|S_IWUSR);
	if (file == -1) fprintf(stderr, "Error during opening the file named %s\n", FILENAME);

	// Correct execution
	else {

		fprintf(stderr, "File correctly created with fd = %d\n", file);

		// Here, initialise the matrix
		init_matrix();

		fprintf(stderr, "Matrix initialized\n");

		// Write it in the file
		if (write(file, matrix, ROW_SIZE * ROW_SIZE * sizeof(int)) == -1) fprintf(stderr, "Error during writing the matrix into the file named %s\n", FILENAME);

		// Correct execution
		else {

			fprintf(stderr, "File correctly initialized with fd = %d\n", file);

			// We create a mapped file
			int mapped_file_size = ROW_SIZE * ROW_SIZE * sizeof(int);
			int *mapped_file = (int *)mmap(0, mapped_file_size, PROT_READ|PROT_WRITE, MAP_SHARED, file, 0);
			if (mapped_file == MAP_FAILED) fprintf(stderr, "Error during opening the mapped file\n");

			// Correct execution
			else {

				fprintf(stderr, "Mapped file correctly created at @%p\n", mapped_file);

				// Remove all the rights over this mapped file memory
				if (mprotect(mapped_file, mapped_file_size, PROT_NONE) == -1) fprintf(stderr, "Error during the removing of the rights over the mapped file memory\n");

				// Correct execution
				else {

					fprintf(stderr, "Rights correctly removed on the mapped file memory\n");

					// Create the storage for the old action that was connected to SIGSEGV
					struct sigaction old_action;
					old_action.sa_flags = SA_SIGINFO;
					//old_action.sa_sigaction = new_sigsev_action;

					// Create the action to connect the SIGSEGV signal to
					struct sigaction action_to_connect;
					action_to_connect.sa_flags = SA_SIGINFO;
					action_to_connect.sa_sigaction = new_sigsev_action;
					sigemptyset(&action_to_connect.sa_mask);

					// Redirect the SIGSEGV signal
					if (sigaction(SIGSEGV, &action_to_connect, &old_action) < 0) fprintf(stderr, "Error during the branchment of the new SIGSEGV action\n");

					// Correct execution
					else {

						fprintf(stderr, "SIGSEGV signal's handler correctly put\n");

						// Try to access to the unrighted memory
						int x = random_int(), y = random_int(), i = 0, never_used;
						do {

							fprintf(stderr, "Access try n°%d to @%p\n", i, cell(mapped_file, x, y));

							never_used = *(cell(mapped_file, x, y));

							fprintf(stderr, "Normally, we never go here but it's just to avoid unused variable warning for never_used = %d\n", never_used);

							x = random_int();
							y = random_int();

							++i;
						} while (i < NB_ACCESS_TRIES);

						// Calculate the percentage number of access tries
						int access_tries_percentage = (int)((access_tries / ROW_SIZE) * 100);  // Because ROW_SIZE * ROW_SIZE pages

						// Display the final result
						printf("And the percentage of access tries is equal to %d\n", access_tries_percentage);
					}
				}

				// Remove the mapped file
				if (munmap(mapped_file, mapped_file_size) == -1) fprintf(stderr, "Error during the unmap of the mapped file\n");
			}
		}

		// Close the file
		if (close(file) == -1) fprintf(stderr, "Error during closing the file with fd = %d\n", file);
	}

	// Free the matrix memory
	free(matrix);

	// Correct execution
	return 0;
}
