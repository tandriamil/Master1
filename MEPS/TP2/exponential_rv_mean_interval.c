#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0


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

// My constants
#define C90 1.645
#define C95 1.96
#define C99 2.58


/**
 * Main function to estimate the mean of exponential random variable
 * 
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char** argv) {

	// Check the arguments
	if (argc != 3) {
		printf("The correct syntax is: %s nb_runs lambda\n", argv[0]);
		return ERROR_ENCOUNTERED;
	}

	// The result to return and the random value got
	double random, variance, result = 0, nb_runs = atoi(argv[1]), lambda = atof(argv[2]);

	// Runs nb_runs tries
	int i;
	for (i = 0; i < nb_runs; i++) {

		// Get a new random variable
		random = MRG32k3a();

		// Sum the result and the derivative
		result += -1.0 * (log(1.0 - random) / lambda);

	}

	// Get the variance
	variance = sqrt((result - (nb_runs * pow((result / nb_runs), 2))) / (nb_runs - 1));

	// Divide by the number of tries
	result = result / nb_runs;

	// Display the result
	printf("Estimation of the mean: %lf.\n", result);
	printf("Confidence interval with 90 percent rate: [%lf, %lf].\n", result - C90*(variance/sqrt(nb_runs)), result + C90*(variance/sqrt(nb_runs)));
	printf("Confidence interval with 95 percent rate: [%lf, %lf].\n", result - C95*(variance/sqrt(nb_runs)), result + C95*(variance/sqrt(nb_runs)));
	printf("Confidence interval with 99 percent rate: [%lf, %lf].\n", result - C99*(variance/sqrt(nb_runs)), result + C99*(variance/sqrt(nb_runs)));

	// Exit correctly
	return EXECUTION_OK;
}