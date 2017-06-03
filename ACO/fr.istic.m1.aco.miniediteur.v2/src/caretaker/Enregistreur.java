package caretaker;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import memento.MementoInterface;
import command.CommandeInterface;


/**
 * Enregistreur class
 */
public class Enregistreur implements EnregistreurInterface {

	// Attributes
	private Map<CommandeInterface, MementoInterface> mementos;
	private Collection<CommandeInterface> commandes;
	private boolean saveState;


	/**
	 * Empty constructor
	 */
	public Enregistreur() {
		this.mementos = new HashMap<CommandeInterface, MementoInterface>();
		this.commandes = new ArrayList<CommandeInterface>();
		this.saveState = false;
	}


	/**
	 * Save a command only if currently in saving state
	 * @param com The command to save
	 */
	public void enregistrer(CommandeInterface com) {

		// Only if currently saving
		if (this.saveState) {

			// Just add the command memento to the list
			this.commandes.add(com);
			this.mementos.put(com, com.getMemento());
		}
	}


	/**
	 * Play again saved commands
	 */
	public void rejouer() {

		// Just play each commands
		for (CommandeInterface c : this.commandes) {

			// Get the saved memento of this command
			MementoInterface mem = this.mementos.get(c);

			// Restore the saved state
			c.setMemento(mem);

			// Play the command
			c.execute();
		}
	}


	/**
	 * Clear the saves
	 */
	public void clear() {
		this.mementos.clear();
		this.commandes.clear();
	}


	/**
	 * Get the saving state to on
	 * @return boolean The current saving state
	 */
	public boolean isSaving() {
		return this.saveState;
	}


	/**
	 * If the saver is empty
	 * @return boolean Check if the saver is empty or not
	 */
	public boolean isEmpty() {
		return (this.commandes.isEmpty() && this.mementos.isEmpty());
	}


	/**
	 * Put the saving state
	 * Clear the saver between each macros
	 * @param b The new saving state
	 */
	public void setSavingState(boolean b) {
		this.saveState = b;

		// If we put it to true and it's not empty, clear the saver
		if ((this.saveState) && (!this.isEmpty()))
			this.clear();
	}

}
