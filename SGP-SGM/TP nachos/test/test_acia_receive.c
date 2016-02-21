/**
 *
 * Simple test case to receive on tty
 *
 * ANDRIAMILANTO Tompoariniaina
 * IHSINE Azzeddine
 *
 * TP SGP nachos
 * 2015 - 2016
 *
 */

// Nachos system calls
#include "userlib/syscall.h"
#include "userlib/libnachos.h"


/**
 * Main function
 */
int main() {

	// Create a message
	char message[1024];

	// Send this message
	int ret, length = 0;
	ret = TtyReceive((char*)message, length);
	n_printf("Message received is %s\n", message);
	n_printf("Message receive, with return value = %d and length of %d\n", ret, length);

	// Exit the program
	Exit(0);
}