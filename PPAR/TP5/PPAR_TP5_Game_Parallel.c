/*!
 * The Conway's game of life
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "mpi.h"

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0
#define N 32
#define MAX_ITERATIONS 1


/*!
 * Allocate the memory for the table
 *
 * \return A pointer to the memory allocated
 */
unsigned int* allocate() {
	return (unsigned int*)calloc(N*N, sizeof(unsigned int));
}


/*!
 * Convert a cell into a one-dimensional array
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param dx The x translation
 * \param dy The y translation
 *
 * \return The ident of the wanted cell
 */
int code(int x, int y, int dx, int dy) {
	int i = (x + dx)%N;
	int j = (y + dy)%N;
	if (i < 0) i = N + i;
	if (j < 0) j = N + j;
	return i * N + j;
}


/*!
 * Write a value into a cell
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param value The value to write into this cell
 * \param *world The table in which we want to write
 *
 * \return The ident of the wanted cell
 */
void write_cell(int x, int y, unsigned int value, unsigned int *world) {
	world[code(x, y, 0, 0)] = value;
}


/*!
 * Initialize the world randomly
 *
 * \return A pointer to the world created
 */
unsigned int* initialize_random() {

	// Variables used here
	int x, y;
	unsigned int cell;
	unsigned int *world;

	// Allocate the world
	world = allocate();

	// Initialize randomly each cell
	for (x = 0; x < N; ++x) {
		for (y = 0; y < N; ++y) {

			// Initialize cell value from rand() function
			if (rand()%5 != 0) cell = 0;
			else if (rand()%2 == 0) cell = 1;
			else cell = 2;

			// Write this generated cell
			write_cell(x, y, cell, world);
		}
	}

	// Return a pointer to the world
	return world;
}


/*!
 * Initialize the world by a dummy way
 *
 * \return A pointer to the world created
 */
unsigned int* initialize_dummy() {

	// Variables used here
	int x, y;
	unsigned int *world;

	// Allocate the world
	world = allocate();

	// Initialize by a dummy way each cell
	for (x = 0; x < N; ++x) {
		for (y = 0; y < N; ++y) {
			write_cell(x, y, x%3, world);  // Only write x modulo 3 as value
		}
	}

	// Return a pointer to the world
	return world;
}


/*!
 * Initialize the world with a glider in the center
 *
 * \return A pointer to the world created
 */
unsigned int* initialize_glider() {

	// Variables used here
	int x, y, mx, my;
	unsigned int *world;

	// Allocate the world
	world = allocate();

	// Fill the world with emptyness
	for (x = 0; x < N; ++x) {
		for (y = 0; y < N; ++y) {
			write_cell(x, y, 0, world);
		}
	}

	// Here, draw a glider
	mx = N/2 - 1;
	my = N/2 - 1;
	x = mx;
	y = my + 1;
	write_cell(x, y, 1, world);

	x = mx + 1;
	y = my + 2;
	write_cell(x, y, 1, world);

	x = mx + 2;
	y = my;
	write_cell(x, y, 1, world);

	y = my + 1;
	write_cell(x, y, 1, world);

	y = my + 2;
	write_cell(x, y, 1, world);

	// Return a pointer to the world
	return world;
}


/*!
 * Initialize the world with a small exploder in the center
 *
 * \return A pointer to the world created
 */
unsigned int* initialize_small_exploder() {

	// Variables used here
	int x, y, mx, my;
	unsigned int *world;

	// Allocate the world
	world = allocate();

	// Fill the world with emptyness
	for (x = 0; x < N; ++x) {
		for (y = 0; y < N; ++y) {
			write_cell(x, y, 0, world);
		}
	}

	// Here, draw a small exploder
	mx = N/2 - 2;
	my = N/2 - 2;
	x = mx;
	y = my + 1;
	write_cell(x, y, 2, world);

	x = mx + 1;
	y = my;
	write_cell(x, y, 2, world);

	y = my + 1;
	write_cell(x, y, 2, world);

	y = my + 2;
	write_cell(x, y, 2, world);

	x = mx + 2;
	y = my;
	write_cell(x, y, 2, world);

	y = my + 2;
	write_cell(x, y, 2, world);

	x = mx + 3;
	y = my + 1;
	write_cell(x, y, 2, world);

	// Return a pointer to the world
	return world;
}


/*!
 * Read a value from a cell
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param dx The x translation
 * \param dy The y translation
 * \param *world The world in which we'll read
 *
 * \return The value into the given cell
 */
int read_cell(int x, int y, int dx, int dy, unsigned int *world) {
	return world[code(x, y, dx, dy)];
}


/*!
 * Update the counters of the neighbours cells
 * From a single cell
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param dx The x translation
 * \param dy The y translation
 * \param *world The world in which we'll read
 * \param *nn The pointer where to write the number of living neighbours
 * \param *n1 The pointer where to write the number of living neighbours of 'x' type
 * \param *n2 The pointer where to write the number of living neighbours of 'o' type
 */
void update(int x, int y, int dx, int dy, unsigned int *world, int *nn, int *n1, int *n2) {
	
	// Get the value of the cell
	unsigned int cell = read_cell(x, y, dx, dy, world);

	// In a living cell
	if (cell != 0) {
		(*nn)++;
		if (cell == 1) (*n1)++;  // 'x' type
		else (*n2)++;  // 'o' type
	}
}


/*!
 * Looks around the given cell and update its counters
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param *world The world in which we'll read
 * \param *nn The pointer where to write the number of living neighbours
 * \param *n1 The pointer where to write the number of living neighbours of 'x' type
 * \param *n2 The pointer where to write the number of living neighbours of 'o' type
 */
void neighbors(int x, int y, unsigned int *world, int *nn, int *n1, int *n2) {
	
	// The variables used here
	int dx, dy;

	// Initialize counters
	(*nn) = 0;
	(*n1) = 0;
	(*n2) = 0;

	// Check the same line
	dx = -1;
	dy = 0;
	update(x, y, dx, dy, world, nn, n1, n2);
	dx = 1;
	dy = 0;
	update(x, y, dx, dy, world, nn, n1, n2);

	// Check the bottom line
	dx = -1;
	dy = 1;
	update(x, y, dx, dy, world, nn, n1, n2);
	dx = 0;
	dy = 1;
	update(x, y, dx, dy, world, nn, n1, n2);
	dx = 1;
	dy = 1;
	update(x, y, dx, dy, world, nn, n1, n2);

	// Check the upper line
	dx = -1;
	dy = -1;
	update(x, y, dx, dy, world, nn, n1, n2);
	dx = 0;
	dy = -1;
	update(x, y, dx, dy, world, nn, n1, n2);
	dx = 1;
	dy = -1;
	update(x, y, dx, dy, world, nn, n1, n2);
}


/*!
 * Get the next generation
 *
 * \param x The x position of the cell
 * \param y The y position of the cell
 * \param *world The world in which we'll read
 * \param *nn The pointer where to write the number of living neighbours
 * \param *n1 The pointer where to write the number of living neighbours of 'x' type
 * \param *n2 The pointer where to write the number of living neighbours of 'o' type
 */
short newgeneration(unsigned int *world1, unsigned int *world2, int xstart, int xend) {
	
	// Variables used here
	int x, y, nn, n1, n2;
	short change = 0;

	// Fill the world with emptyness
	for (x = 0; x < N; ++x) {
		for (y = 0; y < N; ++y) {
			write_cell(x, y, 0, world2);
		}
	}

	// Generate the new world
	for (x = xstart; x < xend; ++x) {
		for (y = 0; y < N; ++y) {

			// Check the neighbours of each cell
			neighbors(x, y, world1, &nn, &n1, &n2);

			//If the cell is alive
			if (world1[code(x, y, 0, 0)] != 0) {

				// If less than 2 neighbours : death
				if (nn < 2) change = 1;

				// If more than 3 neighbours : death
				else if (nn > 3) change = 1;

				// If exaclty 2 or 3 neighbours : live
				else write_cell(x, y, world1[code(x, y, 0, 0)], world2);
			}

			// Else if it's an empty cell with 3 living neighbours : birth
			else if (nn == 3) {

				// In function of the number of living cells' type
				if (n1 > n2) write_cell(x, y, 1, world2);
				else write_cell(x, y, 2, world2);
				change = 1;
			}

		}  // End of for y
	}  // End of for x

	// Return the boolean value to know if there was a change or not
	return change;
}


/*!
 * Clean the screen
 */
void cls() {
	int i;
	for (i = 0; i < 10; ++i) {
		printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
}


/*!
 * Display the world
 *
 * \param *world The world to display
 */
void print(unsigned int *world) {
	
	// Clean the screen
	cls();

	// Display the upper line
	int i;
	for (i = 0; i < N; ++i) printf("-");

	// Display the table
	for (i = 0; i < N*N; ++i) {
		if (i%N == 0) printf("\n");

		switch(world[i]) {
			case 0:
				printf(" ");
				break;
			case 1:
				printf("o");
				break;
			case 2:
				printf("x");
				break;
		}
	}
	printf("\n");

	// Display the bottom line
	for (i = 0; i < N; i++) printf("-");  
	printf("\n");

	// Sleep one second until displaying again
	sleep(1);
}


/**
 * Main function to compute the game of life
 * 
 * \param *argc The number of aguments
 * \param **argv The arguments provided to the program
 */
int main(int argc, char **argv) {

	// Variables used here
	//int it = 0, change = 1;
	int mode = 0, it = 0, start, end, prev, next;
	unsigned int *world1, *world2;
	unsigned int *worldaux;

	// If we have an argument, put it as the mode
	if (argc == 2) mode = atoi(argv[1]);


	/* ############### MPI part ############### */
	int ierr, nb_processes, proc_id;
	MPI_Request request1, request2, request3, request4;
	MPI_Status status;

	// Initialize MPI.
	ierr = MPI_Init(&argc, &argv);
	if (ierr != 0) fprintf(stderr, "MPI_Init() caught an error, return code %d", ierr);

	// Get the number of processes.
	ierr = MPI_Comm_size(MPI_COMM_WORLD, &nb_processes);
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Get the individual process proc_id.
	ierr = MPI_Comm_rank(MPI_COMM_WORLD, &proc_id);
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Check if the number of rows is a multiple of the number of processors
	if (N%nb_processes != 0) {
		fprintf(stderr, "Number of rows %d not dividable by the number of processors %d\n", N, nb_processes);
		exit(ERROR_ENCOUNTERED);
	}

	// All the processors allocate their temporary world
	world2 = allocate();

	// The master create the world, sends it and prints it
	if (proc_id == 0) {

		// Initialize the first world by the way we want
		//world1 = initialize_dummy();
		//world1 = initialize_random();
		//world1 = initialize_glider();
		world1 = initialize_small_exploder();

		// Sends this world to all the other processors by using broadcast
		MPI_Bcast((void *)world1, N*N, MPI_INT, 0, MPI_COMM_WORLD);

		// Prints the initial world
		print(world1);

	} else {  // The other processors just wait to receive the datas

		// But first, allocate the first world
		world1 = allocate();

		MPI_Recv((void *)world1, N*N, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
	}

	// Get the beginning and end
	start = (N/nb_processes) * proc_id;
	end = start + N/nb_processes -1;

	fprintf(stderr, "Initialization done for processor n°%d\n", proc_id);

	// The iterations into the world
	//while (change && (it < MAX_ITERATIONS)) {
	while (it < MAX_ITERATIONS) {

		fprintf(stderr, "Processor n°%d entered iteration n°%d\n", proc_id, it);

		// Get the new generation
		newgeneration(world1, world2, start, end);
		
		// Switch world1 and world2
		worldaux = world1;
		world1 = world2;
		world2 = worldaux;

		// Its two neighbours id
		next = (proc_id+1)%nb_processes;
		prev = proc_id - 1;
		if (prev == -1) prev = nb_processes - 1;

		fprintf(stderr, "Processor n°%d on iteration n°%d is just before the sendings\n", proc_id, it);



		// In function of the mode
		switch(mode) {

			// ############################## All in synchronous mode ##############################
			case 0: {

				// Send its last row to the next processor
				MPI_Send((void *)&world1[code(end, 0, 0, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its next processor n°%d\n", proc_id, it, next);

				// Receive from its previous processor
				MPI_Recv((void *)&world1[code(start, 0, -1, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its previous processor n°%d\n", proc_id, it, prev);

				// Send its first row to the previous processor
				MPI_Send((void *)&world1[code(start, 0, 0, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its next processor
				MPI_Recv((void *)&world1[code(end, 0, +1, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its next processor n°%d\n", proc_id, it, next);
				break;
			}

			// ############################## Send in asynchronous mode ##############################
			case 1: {

				// Send its last row to the next processor
				MPI_Isend((void *)&world1[code(end, 0, 0, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, &request1);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its next processor n°%d\n", proc_id, it, next);

				// Send its first row to the previous processor
				MPI_Isend((void *)&world1[code(start, 0, 0, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, &request2);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its previous processor
				MPI_Recv((void *)&world1[code(start, 0, -1, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its next processor
				MPI_Recv((void *)&world1[code(end, 0, +1, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its next processor n°%d\n", proc_id, it, next);

				// Free the requests
				MPI_Request_free(&request1);
				MPI_Request_free(&request2);
				fprintf(stderr, "Processor n°%d on iteration n°%d just freed its requests\n", proc_id, it);
				break;
			}

			// ############################## Recv in asynchronous mode ##############################
			case 2: {

				// Send its last row to the next processor
				MPI_Send((void *)&world1[code(end, 0, 0, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its next processor n°%d\n", proc_id, it, next);

				// Receive from its previous processor
				MPI_Irecv((void *)&world1[code(start, 0, -1, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, &request3);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its previous processor
				MPI_Recv((void *)&world1[code(start, 0, -1, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its next processor
				MPI_Irecv((void *)&world1[code(end, 0, +1, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, &request4);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its next processor n°%d\n", proc_id, it, next);

				// Free the requests
				MPI_Request_free(&request3);
				MPI_Request_free(&request4);
				fprintf(stderr, "Processor n°%d on iteration n°%d just freed its requests\n", proc_id, it);
				break;
			}

			// ############################## Both in asynchronous mode ##############################
			case 3: {

				// Send its last row to the next processor
				MPI_Isend((void *)&world1[code(end, 0, 0, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, &request1);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its next processor n°%d\n", proc_id, it, next);

				// Receive from its previous processor
				MPI_Irecv((void *)&world1[code(start, 0, -1, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, &request3);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its previous processor n°%d\n", proc_id, it, prev);

				// Send its first row to the previous processor
				MPI_Isend((void *)&world1[code(start, 0, 0, 0)], N, MPI_INT, prev, 0, MPI_COMM_WORLD, &request2);
				fprintf(stderr, "Processor n°%d on iteration n°%d sent row to its previous processor n°%d\n", proc_id, it, prev);

				// Receive from its next processor
				MPI_Irecv((void *)&world1[code(end, 0, +1, 0)], N, MPI_INT, next, 0, MPI_COMM_WORLD, &request4);
				fprintf(stderr, "Processor n°%d on iteration n°%d received row from its next processor n°%d\n", proc_id, it, next);

				// Free the requests
				MPI_Request_free(&request1);
				MPI_Request_free(&request2);
				MPI_Request_free(&request3);
				MPI_Request_free(&request4);
				fprintf(stderr, "Processor n°%d on iteration n°%d just freed its requests\n", proc_id, it);
				break;
			}

		}



		// Increment the iteration counter
		++it;
	}

	// All processors exept master send their rows to master
	if (proc_id != 0) {
		MPI_Send((void *)&world1[code(start, 0, 0, 0)], (N/nb_processes)*N, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	// Master receive the rows for each processor
	else {

		// Receive each row
		for (it = 1; it < nb_processes; ++it) {
			MPI_Recv((void *)&world1[code((N/nb_processes)*it, 0, 0, 0)], (N/nb_processes)*N, MPI_INT, it, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}

		// And prints it
		print(world1);
	}

	// Everyone free the two worlds
	free(world2);
	free(world1);

	// Close MPI
	ierr = MPI_Finalize();
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Correct execution
	return 0;
}