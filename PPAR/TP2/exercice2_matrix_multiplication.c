#include <stdio.h>
#include <stdlib.h>

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0
#define RAND_MIN_VALUE 0
#define RAND_MAX_VALUE 10
#define MATRIX_MAX_SIZE 64
#define MATRIX_MIN_SIZE 0


/* ################################### Externs ################################### */
/**
 * 32-bits Random number generator U(0,1): MRG32k3a
 * Author: Pierre L'Ecuyer,
 * Source: 	Good Parameter Sets for Combined Multiple Recursive Random
 *			Number Generators,
 *			Shorter version in Operations Research,
 *			47, 1 (1999), 159--164.
 *
 * I just edited the commentary and design of the code
**/

// Constants
#define NORM 2.328306549295728e-10
#define M1 4294967087.0
#define M2 4294944443.0
#define A12 1403580.0
#define A13N 810728.0
#define A21 527612.0
#define A23N 1370589.0
#define SEED 12345


// Static variables
static double s10 = SEED, s11 = SEED, s12 = SEED, s20 = SEED, s21 = SEED, s22 = SEED;


/**
 * Get a pseudo-random double number
 * \return A pseudo-random double number
 */
double MRG32k3a() {

	long k;
	double p1, p2;

	/* Component 1 */
	p1 = A12 * s11 - A13N * s10;
	k = p1 / M1;
	p1 -= k * M1;
	if (p1 < 0.0) p1 += M1;
	s10 = s11;
	s11 = s12;
	s12 = p1;

	/* Component 2 */
	p2 = A21 * s22 - A23N * s20;
	k = p2 / M2;
	p2 -= k * M2;
	if (p2 < 0.0) p2 += M2;
	s20 = s21;
	s21 = s22;
	s22 = p2;

	/* Combination */
	if (p1 <= p2) return ((p1 - p2 + M1) * NORM);
	else return ((p1 - p2) * NORM);
}

/* ################################### End of Externs ################################### */

/**
 * Generate a random integer
 *
 * \return A random integer
 */
int random_int() {
	return (int)(MRG32k3a() * ((RAND_MAX_VALUE - RAND_MIN_VALUE) + RAND_MIN_VALUE));
}


/**
 * Simply display a matrix
 */
void display_matrice(int size, int m[size][size]) {
	
	// Variables used here
	int i, j;

	// Simply display the matrix
	for (i = 0; i < size; ++i) {
		for (j = 0; j < size; ++j) {
			fprintf(stderr, "[%d] ", m[i][j]);
		}
		fprintf(stderr, "\n");
	}
}


/**
 * Main function to estimate the mean of exponential random variable
 * 
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char** argv) {

	// The variables used here
	int n, i, j, k;

	// Check the arguments
	if (argc != 2) {
		printf("The correct syntax is: %s matrix_size\n", argv[0]);
		return ERROR_ENCOUNTERED;
	}

	// Get the size of the matrices
	n = atoi(argv[1]);

	// Check this size
	if ((n < MATRIX_MIN_SIZE) || (n > MATRIX_MAX_SIZE)) {
		fprintf(stderr, "%s: invalid value for the matrix size, it must be greater than %d and lesser than %d!\n", argv[0], MATRIX_MIN_SIZE, MATRIX_MAX_SIZE);
		return ERROR_ENCOUNTERED;
	}

	// Create the matrices
	int m1[n][n], m2[n][n], result[n][n];

	// Initialize the matrices
	for (i = 0; i < n; ++i) {
		for (j = 0; j < n; ++j) {
			m2[i][j] = random_int();
			m1[i][j] = random_int();
			result[i][j] = 0;
		}
	}

	// Display the matrices
	fprintf(stderr, "\nFirst matrix:\n");
	display_matrice(n, m1);
	fprintf(stderr, "\nSecond matrix:\n");
	display_matrice(n, m2);

	// Matrices multiplication is done in parallel
	#pragma omp parallel private(i, j, k) shared(n, m1, m2, result)
	{

		// The for loop done in dynamic schedule
		#pragma omp for schedule(dynamic)
		for (i = 0; i < n; ++i) {
			for (j = 0; j < n; ++j) {
				for (k = 0; k < n; ++k) {
					result[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
	}

	// Display the result
	fprintf(stderr, "\nResult matrix:\n");
	display_matrice(n, result);

	// Exit correctly
	return EXECUTION_OK;
}