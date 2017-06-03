#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "mpi.h"

// Constants
#define ERROR_ENCOUNTERED -1
#define EXECUTION_OK 0


/**
 * Check if the character is a number
 *
 * \param ascii_code The integer representation of the character
 */
int is_number(int ascii_code) {
	return ((ascii_code >= 48) && (ascii_code <= 57));
}


/**
 * Check if the character is an alphabetic letter
 *
 * \param ascii_code The integer representation of the character
 */
int is_alphabetic_letter(int ascii_code) {
	return (((ascii_code >= 65) && (ascii_code <= 90)) || ((ascii_code >= 97) && (ascii_code <= 122)));
}


/**
 * Check if the character is a counted special char
 *
 * \param ascii_code The integer representation of the character
 */
int is_counted_special_char(int ascii_code) {
	return ((ascii_code != 32) && (ascii_code != 46) && (ascii_code != 10));
}


/**
 * Print the number of letters of a word
 *
 * \param count A pointer to the counter of words
 */
void print_word_counter(int *count) {

	// If higher or equal than 10
	if (*count > 9) {

		// Print the two digits of the number
		printf("%d", (int)(*count / 10));
		printf("%d", (*count % 10));

	// If we just already counted word but less than 10
	} else if (*count > 0) {

		// Just print the counter
		printf("%d", *count);
	}

	// In the end, we put it back to 0
	*count = 0;
}


/**
 * Main function to change a text into a digit
 *
 * \param argc The number of aguments
 * \param argv The arguments provided to the program
 */
int main(int argc, char **argv) {

	// The different arguments needed here
	int i, text_size, ascii_code, count = 0;
	FILE *input;

	// We open the file in read mode
	input = fopen(argv[1], "r");
	if (!input) {
		fprintf(stderr, "Impossible to open file '%s', exiting ...\n", argv[1]);
		return ERROR_ENCOUNTERED;
	};

	// Get the size of the file
	fseek(input, 0, SEEK_END);
	text_size = ftell(input);
	rewind(input);

	// Put the text into a table
	char text[text_size + 1];
	for (i = 0; i < text_size; i++) text[i] = fgetc(input);

	/* ############### MPI part ############### */
	int ierr, nb_processes, proc_id, beginning, tmp_letter, ending;

	// Initialize MPI.
	ierr = MPI_Init(&argc, &argv);
	if (ierr != 0) fprintf(stderr, "MPI_Init() caught an error, return code %d", ierr);

	// Get the number of processes.
	ierr = MPI_Comm_size(MPI_COMM_WORLD, &nb_processes);
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Get the individual process proc_id.
	ierr = MPI_Comm_rank(MPI_COMM_WORLD, &proc_id);
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Get the beginning
	beginning = ((nb_processes / nb_processes) * proc_id) - 1;

	// If the first character
	if (beginning == -1) {
		beginning = 0;
	} else {

		// While we're in a word
	 	do {
			++beginning;
			tmp_letter = (int)text[beginning];
		} while (is_alphabetic_letter(tmp_letter));
	}

	// Get the ending
	ending = ((nb_processes / nb_processes) * (proc_id + 1)) - 1;

	// While we're in a letter
	do {
		++ending;
		tmp_letter = (int)text[ending];
	} while ((ending < nb_processes) && is_alphabetic_letter(tmp_letter));

	// Convert the text into digits
	for (i = beginning; i < text_size; i++) {

		// Get the current ascii code
		ascii_code = (int)text[i];

		// If it's a number
		if (is_number(ascii_code)) {

			// Print the current counter
			print_word_counter(&count);

			// Print the digit number
			printf("%c", text[i]);
		}

		// If an alphabetic letter
		else if (is_alphabetic_letter(ascii_code)) {
			++count;
		}

		// If special char
		else {

			// Print the current counter
			print_word_counter(&count);

			// If we count it
			if (is_counted_special_char(ascii_code)) printf("%d", 0);
		}

	}

	// Print the current counter
	print_word_counter(&count);

	// Close the opened file
	fclose(input);

	// Close MPI
	ierr = MPI_Finalize();
	if (ierr != 0) fprintf(stderr, "MPI_Finalize() caught an error, return code %d", ierr);

	// Exit with correct state
	return EXECUTION_OK;
}
