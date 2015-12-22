package caretaker;

import receiver.MoteurEditeurInterface;


/**
 * GestionDefaireRefaire interface
 */
public interface GestionDefaireRefaireInterface {

	/**
	 * Save an engine state
	 * @param mei The engine's state to save
	 */
	void sauverMoteur(MoteurEditeurInterface mei);


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
	 * Return to the precedent state
	 * @param n The number of state to go back to, and the engine that we will change
	 * @param mei The engine on which we'll put the new state
	 * @return true if we achieved it, false if we couldn't (invalid state for example)
	 */
	boolean defaire(int n, MoteurEditeurInterface mei);


	/**
	 * Redo some actions
	 * But if we edit the past, we can't go to the alternate future anymore
	 * @param n The number of state to go to the future, and the engine that we will change
	 * @param mei The engine on which we'll put the new state
	 * @return true if we achieved it, false if we couldn't (invalid state for example)
	 */
	boolean refaire(int n, MoteurEditeurInterface mei);

}