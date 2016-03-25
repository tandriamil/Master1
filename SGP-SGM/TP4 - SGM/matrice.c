/*!
	TP3 - SGM (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/


// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/mman.h>


// Constants
#define PAGES_PER_MATRIX 1
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 100
#define MATRIX_SIZE getpagesize() * PAGES_PER_MATRIX
#define FILENAME "mmap.txt"


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
	return beginning + ((y * MATRIX_SIZE + x) * sizeof(int));
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

		// We create a mapped file
		int *mapped_file = (int *)mmap(0, MATRIX_SIZE * 2 * sizeof(int), PROT_READ|PROT_WRITE, MAP_SHARED, file, 0);
		if (mapped_file == MAP_FAILED) fprintf(stderr, "Error during opening the mapped file\n");

		// Correct execution
		else {

			fprintf(stderr, "Mapped file correctly created at @%lo\n", (long)mapped_file);

			// We create our matrix
			int i, j;
			for (i = 0; i < MATRIX_SIZE; ++i) {
				for (j = 0; j < MATRIX_SIZE; ++j) {

					// Fill the matrix with random integers
					*(cell(mapped_file, i, j)) = random_int();
				}
			}

			// Check that the initialisation worked
			for (i = 0; i < MATRIX_SIZE; ++i) {
				for (j = 0; j < MATRIX_SIZE; ++j) {
					fprintf(stderr, "%d\n", *(cell(mapped_file, i, j)));
				}
			}
		}
	}

	return 0;
}