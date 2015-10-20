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
#define NB_ROWS_PER_THREAD 8
#define NB_SON_THREADS 8
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 100

// The matrices as global vars
double m1[MAT_SIZE][MAT_SIZE], m2[MAT_SIZE][MAT_SIZE], result[MAT_SIZE][MAT_SIZE];


void display_matrice(double m[MAT_SIZE][MAT_SIZE]) {

	int x, y;
	for (x = 0; x < MAT_SIZE; ++x) {
		for (y = 0; y < MAT_SIZE; ++y) {
			fprintf(stderr, "[%f] ", m[x][y]);
		}
		fprintf(stderr, "\n");
	}
}


double double_random() {
	return ((rand()/(double)RAND_MAX) * (RAND_MAX_VALUE-RAND_MIN_VALUE) + RAND_MIN_VALUE);
}


void * matmult(void * arg) {

	// Get the thread number
	int thread_number = *((int *)arg);
	fprintf(stderr, "INFO: Thread n° %d\n", thread_number);

	// Vars to do the maths
	int row, row_modulo, res_col, m_col;

	// For a given row
	for (row_modulo = 0; row_modulo < NB_ROWS_PER_THREAD; ++row_modulo) {
		row = thread_number * NB_ROWS_PER_THREAD + row_modulo;
		fprintf(stderr, "INFO: Row n° %d\n", row);

		// For each cols
		for (res_col = 0; res_col < MAT_SIZE; ++res_col) {
			//fprintf(stderr, "INFO: Thread n° %d and [row, col]=[%d, %d]\n", thread_number, row, res_col);

			for (m_col = 0; m_col < MAT_SIZE; ++m_col) {
				result[row][res_col] += m1[row][m_col] * m2[m_col][res_col];
			}
		}
	}

	// End
	pthread_exit(0);
}


void * populate_threads() {

	// The sons
	pthread_t slaves[NB_SON_THREADS];
	int sons_ids[NB_SON_THREADS];  // To avoid passing our increment value

	// Create the eight processes
	int son_id;
	for (son_id = 0; son_id < NB_SON_THREADS; ++son_id) {
		sons_ids[son_id] = son_id;
		if (pthread_create(&slaves[son_id], NULL, matmult, &sons_ids[son_id]) == 0) fprintf(stderr, "SUCCESS: Creation of thread number %d at son_id=%d \n", (int)slaves[sons_ids[son_id]], sons_ids[son_id]);
		else fprintf(stderr, "FAIL: Creation of thread number %d at son_id=%d\n", (int)slaves[sons_ids[son_id]], sons_ids[son_id]);
	}

	// Wait the sons to terminate
	int wait_son;
	for (wait_son = 0; wait_son < NB_SON_THREADS; ++wait_son) {
		if (pthread_join(slaves[wait_son], NULL) == 0) fprintf(stderr, "%s%d\n", "SUCCESS: Wait the end of the thread n°", wait_son);
		else fprintf(stderr, "%s%d\n", "FAIL: Wait the end of the thread n°", wait_son);
	}

	// End
	pthread_exit(0);
}


/**
 * The main function to be thrown
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main() {

	// Initialize the seed
	srand(time(0));

	// Initialize the two matrices
	int i, j;
	for (i = 0; i < MAT_SIZE; ++i) {
		for (j = 0; j < MAT_SIZE ; ++j) {
			m2[i][j] = double_random();
			m1[i][j] = double_random();
		}
	}

	// Initializes the result matrice
	for (i = 0; i < MAT_SIZE; ++i) {
		for (j = 0; j < MAT_SIZE ; ++j) {
			result[i][j] = 0.0;
		}
	}

	/* #################### Test of the correct result of the multiplication 

	m2[0][0] = 3;
	m2[0][1] = 1;
	m2[1][0] = 2;
	m2[1][1] = 1;

	m1[0][0] = 1;
	m1[0][1] = 0;
	m1[1][0] = -1;
	m1[1][1] = 3; */

	// Display the state of the matrices
	fprintf(stderr, "\n%s\n", "First matrice:");
	display_matrice(m1);
	fprintf(stderr, "\n%s\n", "Second matrice:");
	display_matrice(m2);

	// Create the main thread
	pthread_t main_thread;
	if (pthread_create(&main_thread, NULL, &populate_threads, NULL) == 0) fprintf(stderr, "%s\n", "SUCCESS: Creation of the main thread");
	else fprintf(stderr, "%s\n", "FAIL: Creation of the main thread");

	// Wait the main thread
	if (pthread_join(main_thread, NULL) == 0) fprintf(stderr, "%s\n", "SUCCESS: Wait the end of the main thread");
	else fprintf(stderr, "%s\n", "FAIL: Wait the end of the main thread");



	/* #################### This was just a test without using threads, no segfault ####################

	// Vars needed
	int row, res_col;

	// For a given row
	for (row = 0; row < MAT_SIZE; ++row) {

		// For each cols
		for (res_col = 0; res_col < MAT_SIZE; ++res_col) {
			//fprintf(stderr, "INFO: Thread n° %d and [row, col]=[%d, %d]\n", thread_number, row, res_col);
			result[row][res_col] += m1[res_col][row] * m2[row][res_col];
		}
	} */



	// Then display the result
	fprintf(stderr, "\n%s\n", "The result:");
	display_matrice(result);

	// End
	return 0;

}