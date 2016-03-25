/**
	TP3 - SGM (groupe SR 1.1)
	ANDRIAMILANTO Tompoariniaina
	IHSINE Azzeddine
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

// Constants
#define LOOP 5
#define MQ_RIGHTS 0666|IPC_CREAT
#define MQ_KEY 42
#define BUFFER_SIZE 1024
#define MSG_TYPE 35
#define MSG_END_TYPE 37

struct message {
	long type;
	char msg[BUFFER_SIZE-sizeof(long)];
};


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Create the message queue
	int mq_id = msgget(MQ_KEY, MQ_RIGHTS);
	if (mq_id == -1) fprintf(stderr, "Error creating the message queue with key %d\n", MQ_KEY);

	// If really created
	else {

		// Here, send continuously messages
		int count = 0;
		struct message mess;
		mess.type = MSG_TYPE;
		while (count < LOOP) {

			// Put the message into the buffer
			snprintf(mess.msg, sizeof(mess.msg), "Je suis le message numéro %d", count);
			fprintf(stderr, "%s\n", mess.msg);
			
			// Increment the counter
			++count;

			// If the last message
			if (count == LOOP) {
				mess.type = MSG_END_TYPE;
				strcpy(mess.msg, "Fin de la transmission");
			}

			// Send it
			if (msgsnd(mq_id, &mess, BUFFER_SIZE, 0) == -1) fprintf(stderr, "Error during sending the message n°%d with key %d and id %d\n", count, MQ_KEY, mq_id);

			// Wait a little before sending another message to the receiver
			sleep(5);
		}

		// Delete the message queue
		//if (msgctl(mq_id, IPC_RMID, 0) == -1) fprintf(stderr, "Error deleting the message queue with key %d and id %d\n", MQ_KEY, mq_id);
		
		// We let the receiver delete the queue
	}

	return 0;
}