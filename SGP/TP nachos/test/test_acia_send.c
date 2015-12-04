/**
 *
 * Simple test case to send on tty
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
	//char message[1024];
	char *message = "Bonjour, ceci est un message à envoyer sur le terminal.";
	n_printf("Message correctly created\n");

	// Send this message
	int ret;
	ret = TtySend(message);
	n_printf("Message sent, with return value = %d\n", ret);

	// Exit the program
	Exit(0);
}