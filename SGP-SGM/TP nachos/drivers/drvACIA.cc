/* \file drvACIA.cc
   \brief Routines of the ACIA device driver
//
//      The ACIA is an asynchronous device (requests return 
//      immediately, and an interrupt happens later on).  
//      This is a layer on top of the ACIA.
//      Two working modes are to be implemented in assignment 2:
//      a Busy Waiting mode and an Interrupt mode. The Busy Waiting
//      mode implements a synchronous IO whereas IOs are asynchronous
//      IOs are implemented in the Interrupt mode (see the Nachos
//      roadmap for further details).
//
//  Copyright (c) 1999-2000 INSA de Rennes.
//  All rights reserved.  
//  See copyright_insa.h for copyright notice and limitation 
//  of liability and disclaimer of warranty provisions.
//
*/


/* Includes */
#include "kernel/system.h"         // for the ACIA object
#include "kernel/synch.h"
#include "machine/ACIA.h"
#include "drivers/drvACIA.h"


//-------------------------------------------------------------------------
// DriverACIA::DriverACIA()
/*! Constructor.
  Initialize the ACIA driver. In the ACIA Interrupt mode, 
  initialize the reception index and semaphores and allow 
  reception and emission interrupts. 
  In the ACIA Busy Waiting mode, simply inittialize the ACIA 
  working mode and create the semaphore.
  */
//-------------------------------------------------------------------------
#ifndef ETUDIANTS_TP
DriverACIA::DriverACIA()
{
  printf("**** Warning: contructor of the ACIA driver not implemented yet\n");
  exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
DriverACIA::DriverACIA() {

	// Assert that we are allowing ACIA driver
	ASSERT(g_cfg->ACIA != ACIA_NONE);


	// Clear the buffers
	bzero(send_buffer, BUFFER_SIZE);
	bzero(receive_buffer, BUFFER_SIZE);

	// In function of the working mode got from nachos config file
	if (g_cfg->ACIA == ACIA_BUSY_WAITING)
		g_machine->acia->SetWorkingMode(BUSY_WAITING);
	else if (g_cfg->ACIA == ACIA_INTERRUPT)
		g_machine->acia->SetWorkingMode(REC_INTERRUPT | SEND_INTERRUPT);


	/* ######################### Busy Waiting Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == BUSY_WAITING) {

		// Initialize semaphores
		send_sema = new Semaphore((char*)"ACIASendSema", 1);
		receive_sema = new Semaphore((char*)"ACIAReceiveSema", 1);

	}


	/* ######################### Reception Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == REC_INTERRUPT) {

	}


	/* ######################### Send Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == SEND_INTERRUPT) {
		
	}

}
#endif


//-------------------------------------------------------------------------
// DriverACIA::TtySend(char* buff)
/*! Routine to send a message through the ACIA (Busy Waiting or Interrupt mode)
  */
//-------------------------------------------------------------------------
#ifndef ETUDIANTS_TP
int DriverACIA::TtySend(char* buff)
{ 
  printf("**** Warning: method Tty_Send of the ACIA driver not implemented yet\n");
  exit(-1);
  return 0;
}
#endif

#ifdef ETUDIANTS_TP
int DriverACIA::TtySend(char* buff) {

	/* ######################### Busy Waiting Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == BUSY_WAITING) {

		// Block with sema
		send_sema->P();

		// The index to parse the buffer passed
		int buf_ind = 0;

		// Busy waiting
		while (g_machine->acia->GetOutputStateReg() == FULL);

		// Transfer until we got the end of file char
		do {

			// Put the char
			g_machine->acia->PutChar(buff[buf_ind]);

			// Increment
			buf_ind++;

		} while (buff[buf_ind-1] != '\0');

		// Unblock with sema
		send_sema->V();

	}


	/* ######################### Reception Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == REC_INTERRUPT) {

	}


	/* ######################### Send Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == SEND_INTERRUPT) {

	}

	// Return ok then
	return 0;

}
#endif

//-------------------------------------------------------------------------
// DriverACIA::TtyReceive(char* buff,int length)
/*! Routine to reveive a message through the ACIA 
//  (Busy Waiting and Interrupt mode).
  */
//-------------------------------------------------------------------------
#ifndef ETUDIANTS_TP
int DriverACIA::TtyReceive(char* buff,int lg)
{
   printf("**** Warning: method Tty_Receive of the ACIA driver not implemented yet\n");
  exit(-1);
  return 0;
}
#endif

#ifdef ETUDIANTS_TP
int DriverACIA::TtyReceive(char *buff, int lg) {

	/* ######################### Busy Waiting Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == BUSY_WAITING) {

		// Block with sema
		receive_sema->P();

		// The index to parse the buffer passed
		int buf_ind = 0;

		// Busy waiting
		while (g_machine->acia->GetInputStateReg() == EMPTY);

		// Transfer until we got the end of file char
		do {

			// Get the char
			buff[buf_ind] = g_machine->acia->GetChar();

			// Increment
			buf_ind++;

		} while (buff[buf_ind-1] != '\0');

		// Before leaving, put the length
		*(&lg) = buf_ind;

		// Unblock with sema
		receive_sema->V();

	}


	/* ######################### Reception Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == REC_INTERRUPT) {

	}


	/* ######################### Send Interrupts Mode ######################### */
	if (g_machine->acia->GetWorkingMode() == SEND_INTERRUPT) {

	}

	// Return ok then
	return 0;
}
#endif


//-------------------------------------------------------------------------
// DriverACIA::InterruptSend()
/*! Emission interrupt handler.
  Used in the ACIA Interrupt mode only. 
  Detects when it's the end of the message (if so, releases the send_sema semaphore), else sends the next character according to index ind_send.
  */
//-------------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void DriverACIA::InterruptSend()
{
  printf("**** Warning: send interrupt handler not implemented yet\n");
  exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void DriverACIA::InterruptSend() {

	// Assert that we are not in busy waiting
	ASSERT(g_machine->acia->GetWorkingMode() != BUSY_WAITING);

}
#endif


//-------------------------------------------------------------------------
// DriverACIA::Interrupt_receive()
/*! Reception interrupt handler.
  Used in the ACIA Interrupt mode only. Reveices a character through the ACIA. 
  Releases the receive_sema semaphore and disables reception 
  interrupts when the last character of the message is received 
  (character '\0').
  */
//-------------------------------------------------------------------------
#ifndef ETUDIANTS_TP
void DriverACIA::InterruptReceive()
{
  printf("**** Warning: receive interrupt handler not implemented yet\n");
  exit(-1);
}
#endif

#ifdef ETUDIANTS_TP
void DriverACIA::InterruptReceive() {

	// Assert that we are not in busy waiting
	ASSERT(g_machine->acia->GetWorkingMode() != BUSY_WAITING);

}
#endif