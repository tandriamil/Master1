package receiver;

import caretaker.EnregistreurInterface;
import memento.MementoInterface;


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
	 * Execute the defaire command
	 * @param n The number of time we want to undo
	 */
	void defaire(int n);


	/**
	 * Execute the refaire command
	 * @param n The number of time we want to redo
	 */
	void refaire(int n);


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


	/**
	 * Check if the save states list is empty or not
	 * @return boolean true if no state saved, false if not
	 */
	public boolean canUndo();


	/**
	 * Check if there are some elements after the current state
	 * To know if we can go in the future of this sequence
	 * @return boolean true if we can go in the future, false if not
	 */
	public boolean canRedo();


	/**
	 * Give the Memento of the Motor 
	 * @return MementoInterface The Memento of the Motor
	 */
	MementoInterface getMemento();


	/**
	 * Set the Memento states of the Motor
	 * @param m The Memento of the Motor
	 */
	void setMemento(MementoInterface m);

}