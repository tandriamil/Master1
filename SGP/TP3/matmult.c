/*
	TP3 - SGF (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <stdlib.h>
#include <time.h>

// Constants
#define MAT_SIZE 64
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 100

// The matrices as global vars
double m1[MAT_SIZE][MAT_SIZE], m2[MAT_SIZE][MAT_SIZE], result[MAT_SIZE][MAT_SIZE];


void display_matrice(double * m) {

	int x, y;
	for (x = 0; x < MAT_SIZE; ++x) {
		for (y = 0; y < MAT_SIZE; ++y) {
			fprintf(stderr, "[%f] ", m1[x][y]);
		}
		fprintf(stderr, "\n");
	}

}


double double_random() {
	return (rand()/(double)RAND_MAX) * (RAND_MAX_VALUE-RAND_MIN_VALUE) + RAND_MIN_VALUE;
}


void * matmult(void * arg) {

	// Get the thread number
	int thread_number = *((int *)arg);

	// Do the calculs
	int row, j, res_col;
	for (j = 0; j < 8; ++j) {
		row = thread_number * 8 + j;

		for (res_col = 0; res_col < 64; ++res_col) {
			result[row][res_col] += m1[res_col][row] * m2[row][res_col];
		}
	}

	// Return empty value
	return NULL;
}


void * populate_threads(void * arg) {

	// Get the pointer
	pthread_t *slaves = (pthread_t *)arg;

	// Create the eight processes, eight times
	int i;
	for (i = 0; i < 8; ++i) {
		if (pthread_create((pthread_t *)(slaves + i * sizeof(pthread_t)), NULL, &matmult, &i) == 0) fprintf(stderr, "SUCCESS: Creation of thread number %d at i=%d \n", (int)*(slaves + i * sizeof(pthread_t)), i);
		else fprintf(stderr, "FAIL: Creation of thread number %d at i=%d\n", (int)*(slaves + i * sizeof(pthread_t)), i);
	}

	// End
	return NULL;
}


/**
 * The main function to be thrown
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main() {

	// Initialize the srand value
	srand(time(NULL));

	// Initialize the two matrices
	int i, j;
	for (i = 0; i < MAT_SIZE; ++i) {
		for (j = 0; j < MAT_SIZE ; ++j) {
			m1[i][j] = double_random();
			m2[i][j] = double_random();
		}
	}

	// Display the state of the matrices
	fprintf(stderr, "\n%s\n", "First matrice:");
	display_matrice(m1);
	fprintf(stderr, "\n%s\n", "Second matrice:");
	display_matrice(m2);

	// Create the main thread
	pthread_t slaves[8];
	pthread_t main_thread;
	if (pthread_create(&main_thread, NULL, &populate_threads, &slaves) == 0) fprintf(stderr, "%s\n", "SUCCESS: Creation of the main thread");
	else fprintf(stderr, "%s\n", "FAIL: Creation of the main thread");

	// Wait the main thread
	if (pthread_join(main_thread, NULL) == 0) fprintf(stderr, "%s\n", "SUCCESS: Wait the end of the main thread");
	else fprintf(stderr, "%s\n", "FAIL: Wait the end of the main thread");

	// Wait the sons to terminate
	for (i = 0; i < 8; ++i) {
		if (pthread_join(slaves[i], NULL) == 0) fprintf(stderr, "%s%d\n", "SUCCESS: Wait the end of the thread n°", i);
		else fprintf(stderr, "%s%d\n", "FAIL: Wait the end of the thread n°", i);
	}

	// End
	return 0;

}