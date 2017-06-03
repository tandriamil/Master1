#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0
#define ARRIVAL 0
#define DEPARTURE 1


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
 * To get a random value from exponential time distribution
 *
 * \param lambda The parameter for the exponential time distribution
 */
double exponential(double lambda) {
   return -1.0 * (log(MRG32k3a()) / lambda);
}


/**
 * Main function to estimate the mean of exponential random variable
 *
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char** argv) {

	// Check the arguments
	if (argc != 4) {
		printf("The correct syntax is: %s lambda mu t_max\n", argv[0]);
		return ERROR_ENCOUNTERED;
	}

	// The parameters for this simulation
	double lambda = atoi(argv[1]), mu = atoi(argv[2]), t_max = atoi(argv[3]),
	arrival_t, departure_t, current_t, previous_t,  // The different times to save
	area = 0.0;
	int event, nb_users = 0;

	// Initialize times
	arrival_t = exponential(lambda);
	departure_t = arrival_t + exponential(mu);
	previous_t = 0.0;

	// While we didn't reach the limit time
	while ((arrival_t < t_max) && (departure_t < t_max)) {

		// Arrival comes before departure
		if (arrival_t <= departure_t) {

			// Update the time
			current_t = arrival_t;

			// Put the event that happened
			event = ARRIVAL;

		} else {  // Departure comes before

			// Update the time
			current_t = departure_t;

			// Put the event that happened
			event = DEPARTURE;

		}

		// Update the area
		area += nb_users * (current_t - previous_t);

		// In function of the event that occured
		switch (event) {

			case ARRIVAL:
				++nb_users;
				arrival_t += exponential(lambda);
				break;

			case DEPARTURE:
				--nb_users;
				if (nb_users > 0) departure_t += exponential(mu);
				else departure_t = arrival_t + exponential(mu);
				break;

		}

	} // End of while

	// Update the area
	area += nb_users * (t_max - previous_t);

	// The area is the total time that each user spend in the system
	// nb_users is the number of users at each time in the system

	// Display the result
	printf("Estimation of the mean backlog: %lf.\n", (area / t_max));

	// Exit correctly
	return EXECUTION_OK;
}
