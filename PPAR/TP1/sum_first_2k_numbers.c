#include <stdio.h>
#include <stdlib.h>

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0
#define MAX_K 31
#define MIN_K 0


/**
 * Main function to estimate the mean of exponential random variable
 * 
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char** argv) {
	
	// The variables used here
	int k, i, n = 1, res = 0;

	// Get the argument k
	if (argc != 2) {
		fprintf(stderr, "Correct syntax is: %s k\n", argv[0]);
		return ERROR_ENCOUNTERED;
	}

	// The value of k
	k = atoi(argv[1]);

	// Check the k argument given
	if ((k < MIN_K) || (k > MAX_K)) {
		fprintf(stderr, "Invalid value for k, must be greater than %d and lesser than %d!\n", MIN_K, MAX_K);
		return ERROR_ENCOUNTERED;
	}

	// Put 2^k into n and create a table of this size
	n = n << k;
	int a[n];

	// Compute the sum
	for (i = 0; i < n; ++i) {
		a[i] = i + 1;
		res += i + 1;
	}

	// Display the table
	printf("The table a contains [");
	for (i = 0; i < (n - 1); ++i) {
		printf("%d, ", a[i]);
	}
	printf("%d]\n", a[i]);

	// Display results
	printf("The result of the sum is %d\n", res);

	// Exit correctly
	return EXECUTION_OK;
}