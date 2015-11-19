// Includes
#include "userlib/syscall.h"
#include "userlib/libnachos.h"

// Constants
#define MAT_SIZE 64
#define NB_ROWS_PER_THREAD 8
#define NB_SON_THREADS 8
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 100

// The matrices as global vars
int m1[MAT_SIZE][MAT_SIZE], m2[MAT_SIZE][MAT_SIZE], result[MAT_SIZE][MAT_SIZE];
int inc = 0;


/**
 * Display the matrice
 * Param: - int table of int
 */
void display_matrice(int m[MAT_SIZE][MAT_SIZE]) {

	int x, y;
	for (x = 0; x < MAT_SIZE; ++x) {
		for (y = 0; y < MAT_SIZE; ++y) {
			n_printf("[%f] ", m[x][y]);
		}
		n_printf("\n");
	}
}


/**
 * Generate a random int
 * Return: A random int
 */
int int_random() {
	return inc++;
}


/**
 * Function to calculate the rows for each son
 * Param: - A pointer to the id of the son
 */
void * matmult(void * arg) {

	// Get the thread number
	int thread_number = *((int *)arg);
	n_printf("INFO: Thread n° %d\n", thread_number);

	// Vars to do the maths
	int row, row_modulo, res_col, m_col;

	// For a given row
	for (row_modulo = 0; row_modulo < NB_ROWS_PER_THREAD; ++row_modulo) {
		row = thread_number * NB_ROWS_PER_THREAD + row_modulo;
		n_printf("INFO: Row n° %d\n", row);

		// For each cols
		for (res_col = 0; res_col < MAT_SIZE; ++res_col) {
			//n_printf("INFO: Thread n° %d and [row, col]=[%d, %d]\n", thread_number, row, res_col);

			for (m_col = 0; m_col < MAT_SIZE; ++m_col) {
				result[row][res_col] += m1[row][m_col] * m2[m_col][res_col];
			}
		}
	}

	// End
	// ThreadId_exit(0);
}


/**
 * Function for the main thread to generate the sons
 */
void * populate_threads() {

	// The sons
	ThreadId slaves[NB_SON_THREADS];
	int sons_ids[NB_SON_THREADS];  // To avoid passing our increment value

	// Create the eight processes
	int son_id;
	for (son_id = 0; son_id < NB_SON_THREADS; ++son_id) {
		sons_ids[son_id] = son_id;
		slaves[son_id] = newThread("Fils", (int)matmult, (int)&sons_ids[son_id]);
		if (slaves[son_id] != 0) n_printf("SUCCESS: Creation of thread number %d at son_id=%d \n", (int)slaves[sons_ids[son_id]], sons_ids[son_id]);
		else n_printf("FAIL: Creation of thread number %d at son_id=%d\n", (int)slaves[sons_ids[son_id]], sons_ids[son_id]);
	}

	// Wait the sons to terminate
	int wait_son;
	for (wait_son = 0; wait_son < NB_SON_THREADS; ++wait_son) {
		if (Join(slaves[wait_son]) == 0) n_printf("%s%d\n", "SUCCESS: Wait the end of the thread n°", wait_son);
		else n_printf("%s%d\n", "FAIL: Wait the end of the thread n°", wait_son);
	}

	// End
	// ThreadId_exit(0);
}


/**
 * The main function to be thrown
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main() {

	// Some vars needed for initialization
	int i, j;

	// Initializes the result matrice
	for (i = 0; i < MAT_SIZE; ++i) {
		for (j = 0; j < MAT_SIZE ; ++j) {
			result[i][j] = 0.0;
		}
	}

	// Initialize the two matrices
	for (i = 0; i < MAT_SIZE; ++i) {
		for (j = 0; j < MAT_SIZE ; ++j) {
			m2[i][j] = int_random();
			m1[i][j] = int_random();
		}
	}

	/* #################### Test of the correct result of the multiplication with a matrice of length equals to 2

		// Put the constants with those values
		// MAT_SIZE = 2
		// NB_ROWS_PER_THREAD = 1
		// NB_SON_THREADS = 1

		// Initializes the valus of the matrices
		m2[0][0] = 3;
		m2[0][1] = 1;
		m2[1][0] = 2;
		m2[1][1] = 1;

		// m2 = (3 1)
		//      (2 1)

		m1[0][0] = 1;
		m1[0][1] = 0;
		m1[1][0] = -1;
		m1[1][1] = 3;

		// m1 = (1  0)
		//      (-1 3)

		// The result should be
		// result = (3 1)
		//          (3 2)
	*/

	// Display the state of the matrices
	n_printf("\n%s\n", "First matrice:");
	display_matrice(m1);
	n_printf("\n%s\n", "Second matrice:");
	display_matrice(m2);

	// Create the main thread
	ThreadId main_thread;
	main_thread = newThread("main", (int)&populate_threads, 0);
	if (main_thread == 0) n_printf("%s\n", "SUCCESS: Creation of the main thread");
	else n_printf("%s\n", "FAIL: Creation of the main thread");

	// Wait the main thread
	if (Join(main_thread) == 0) n_printf("%s\n", "SUCCESS: Wait the end of the main thread");
	else n_printf("%s\n", "FAIL: Wait the end of the main thread");

	// Then display the result
	n_printf("\n%s\n", "The result:");
	display_matrice(result);

	// End
	return 0;

}