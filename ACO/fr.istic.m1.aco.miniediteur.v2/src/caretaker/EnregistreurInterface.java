package caretaker;

import command.CommandeInterface;


/**
 * Enregistreur interface
 */
public interface EnregistreurInterface {

	/**
	 * Save a command
	 */
	void enregistrer(CommandeInterface ce);


	/**
	 * Play again saved commands
	 */
	void rejouer();


	/**
	 * Clear the saves
	 */
	void clear();


	/**
	 * Get the saving state to on
	 * @return boolean The current saving state
	 */
	boolean isSaving();


	/**
	 * If the saver is empty
	 * @return boolean Check if the saver is empty or not
	 */
	boolean isEmpty();


	/**
	 * Put the saving state
	 * Clear the saver after between each macros
	 * @param b The new saving state
	 */
	void setSavingState(boolean b);

}
