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
#define MQ_RIGHTS 0666
#define MQ_KEY 42
#define BUFFER_SIZE 1024
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
		struct message mess;
		while (mess.type != MSG_END_TYPE) {

			fprintf(stderr, "%s\n", "Waiting for message");

			// Receive the message
			if (msgrcv(mq_id, &mess, BUFFER_SIZE, 0, 0) == -1) fprintf(stderr, "Error during receiving the message with key %d and id %d\n", MQ_KEY, mq_id);

			// Print the message
			printf("%s\n", mess.msg);

			// Wait a little for the sender
			sleep(5);
		}

		// Delete the message queue
		if (msgctl(mq_id, IPC_RMID, 0) == -1) fprintf(stderr, "Error deleting the message queue with key %d and id %d\n", MQ_KEY, mq_id);
	}

	return 0;
}
