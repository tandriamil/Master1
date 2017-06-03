#include <stdio.h>
#include <stdlib.h>

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0


/**
 * Main function to estimate the mean of exponential random variable
 *
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char** argv) {

	// The variables used here
	int nb, me;

	// Done in parallel by OpenMP
	#pragma omp parallel private(nb, me)
	{

		// Get the specific values
		nb = omp_get_num_threads();
		me = omp_get_thread_num();

		// Display the hello world message
		printf("Hello world from thread n°%d\n", me);

		// Only the master prints this one
		#pragma omp master
		{
			printf("There is %d threads printing Hello world\n", nb);
		}
	}

	// Exit correctly
	return EXECUTION_OK;
}
