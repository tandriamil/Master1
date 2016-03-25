/*
  TP3 - SGM (groupe SR 1.1)
  ANDRIAMILANTO Tompoariniaina
  IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <error.h>   


// Constants
#define KEY_SEG 35
#define PROD_SEM 0
#define CONS_SEM 1
#define SHM_SEM 2
#define SIZE 5
#define LOOP 15


// Global variables
struct sembuf prod_P = {PROD_SEM, -1, 0};
struct sembuf prod_V = {PROD_SEM, 1, 0};
struct sembuf cons_P = {CONS_SEM, -1, 0};
struct sembuf cons_V = {CONS_SEM, 1, 0};
struct sembuf shm_P = {SHM_SEM, -1, 0};
struct sembuf shm_V = {SHM_SEM, 1, 0};
int buffer_flag = 0;


void producteur(int sem_id) {

	fprintf(stderr, "Producer launched\n");

	// Create a shared memory segment
	int shm_id = shmget(KEY_SEG, SIZE * sizeof(int), 0660|IPC_CREAT);
	if (shm_id == -1) fprintf(stderr, "Error during the creation of a shared memory segment\n");

	// Correct case
	else {

		// Get a pointer to the shm
		int *shared_int = (int *)shmat(shm_id, 0, SHM_RND);

		// We'll only turn x times here
		int count = 0;
		while(count < LOOP) {

			fprintf(stderr, "Producer loop is at %d\n", count);

			// Check that we can write into the buffer
			if (semop(sem_id, &prod_P, 1) == -1) fprintf(stderr, "Error during prod call to prod_P\n");



			/* ##### Critical section on the shm ##### */
			if (semop(sem_id, &shm_P, 1) == -1) fprintf(stderr, "Error during prod call to shm_P\n");

			// Write the current counter into the shm
			*((int *)(shared_int + buffer_flag * sizeof(int))) = count;

			// Increment the flag
			buffer_flag = (buffer_flag + 1)%SIZE;

			/* ##### End of critical section on the shm ##### */
			if (semop(sem_id, &shm_V, 1) == -1) fprintf(stderr, "Error during prod call to shm_V\n");



			// Say to the consumer that he can consume
			if (semop(sem_id, &cons_V, 1) == -1) fprintf(stderr, "Error during prod call to cons_V\n");

			// Increment the counter 
			++count;
		}

		// Detach the shared memory segment
		if (shmdt(shared_int)) fprintf(stderr, "Error during the detachment of the shared memory\n");
	}
} 


void consommateur(int sem_id) {

	fprintf(stderr, "Consumer launched\n");

	// Create a shared memory segment
	int shm_id = shmget(KEY_SEG, SIZE * sizeof(int), 0660|IPC_CREAT);
	if (shm_id == -1) fprintf(stderr, "Error during the creation of a shared memory segment\n");

	// Correct case
	else {

		// Get a pointer to the shm
		int *shared_int = (int *)shmat(shm_id, 0, SHM_RDONLY);

		// We'll only turn x times here, got from the producer
		int count;
		do {

			// Check that we can read from the buffer
			if (semop(sem_id, &cons_P, 1) == -1) fprintf(stderr, "Error during cons call to cons_P\n");



			/* ##### Critical section on the shm ##### */
			if (semop(sem_id, &shm_P, 1) == -1) fprintf(stderr, "Error during cons call to shm_P\n");

			// Read the current counter from the shm
			count = *((int *)(shared_int + buffer_flag * sizeof(int)));

			// Decrement the flag
			buffer_flag = (buffer_flag - 1)%SIZE;

			/* ##### End of critical section on the shm ##### */
			if (semop(sem_id, &shm_V, 1) == -1) fprintf(stderr, "Error during cons call to shm_V\n");


			fprintf(stderr, "Consumer is at loop number %d\n", count);


			// Say to the producer that he can produce
			if (semop(sem_id, &prod_V, 1) == -1) fprintf(stderr, "Error during cons call to prod_V\n");
		} while(count < LOOP);

		// Detach the shared memory segment
		if (shmdt(shared_int)) fprintf(stderr, "Error during the detachment of the shared memory\n");

		// Delete the shared memory segment
		if (shmctl(shm_id, 0, 0)) fprintf(stderr, "Error during the suppression of the shared memory\n");
	}

	// Close the semaphores (both of them)
	if (semctl(sem_id, 0, IPC_RMID, 0)) fprintf(stderr, "Error during the suppression of the semaphores\n");
}


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {
	
	// Here, we create the semaphore
	int sem_id = semget(IPC_PRIVATE, 3, 0660);
	if (sem_id == -1) fprintf(stderr, "Error during the creation of the semaphore\n");

	// Correct case
	else {

		// Initialise the value of the semaphore to SIZE and to 0 (basic prod/cons)
		if ((semctl(sem_id, PROD_SEM, SETVAL, 1) == -1) | (semctl(sem_id, CONS_SEM, SETVAL, 0) == -1) | (semctl(sem_id, SHM_SEM, SETVAL, 1) == -1)) fprintf(stderr, "Error during the setting of the value of the semaphore\n");

		// Correct case
		else {

			// Call to fork
			int id_pro = fork();
			
			// If the father
			if (id_pro == 0) {
				producteur(sem_id);
			} else {
				consommateur(sem_id);
			}
		}
	}

	// Quit the program
	return 0;
}