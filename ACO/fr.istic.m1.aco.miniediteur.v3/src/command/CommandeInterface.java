package command;

import memento.MementoInterface;


/**
 * Commande interface
 */
public interface CommandeInterface {

	/**
	 * Execute the command
	 */
	void execute();


	/**
	 * Give the Memento of the command
	 * @return MementoInterface The Memento of the command
	 */
	MementoInterface getMemento();


	/**
	 * Set the Memento states of the command
	 * @param m The Memento of the command
	 */
	void setMemento(MementoInterface m);

}