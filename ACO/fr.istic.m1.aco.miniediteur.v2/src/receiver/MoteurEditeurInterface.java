package receiver;

import caretaker.EnregistreurInterface;


/**
 * MoteurEditeur interface
 */
public interface MoteurEditeurInterface {

	/**
	 * Execute the cut command
	 */
	void couper();


	/**
	 * Execute the copy command
	 */
	void copier();


	/**
	 * Execute the paste command
	 */
	void coller();


	/**
	 * Execute the write command
	 * @param s The String to write
	 */
	void saisir(String s);


	/**
	 * Execute the selection command
	 * @param d The beginning of the selection
	 * @param l The length of the selection
	 * @throws InputMismatchException If we're trying to select
	 */
	void selectionner(int d, int l);


	/**
	 * Execute the enregistrer command
	 * @param ei The caretaker on which we'll ask to begin to save commands
	 */
	void enregistrer(EnregistreurInterface ei);


	/**
	 * Execute the rejouer command
	 * @param ei The caretaker on which we'll ask to replay commands
	 */
	void rejouer(EnregistreurInterface ei);


	/**
	 * Get the buffer
	 * @return BufferInterface The buffer
	 */
	BufferInterface getBuffer();


	/**
	 * Set the buffer
	 * @param b The buffer
	 */
	void setBuffer(BufferInterface b);


	/**
	 * Get the pastebin
	 * @return PressePapierInterface The pastebin
	 */
	PressePapierInterface getPressePapier();


	/**
	 * Set the pastebin
	 * @param p The pastebin
	 */
	void setPressePapier(PressePapierInterface p);

}
