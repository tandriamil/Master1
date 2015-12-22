package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;


/**
 * Enregistrer command concrete class
 */
public class Enregistrer implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 * @param ei The caretaker to save a memento of this command
	 */
	public Enregistrer(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
	}


	/**
	 * Execute the command
	 */
	public void execute() {

		// Just execute the command on the engine
		this.moteur.enregistrer(this.enregistreur);

		// No need to save this command
	}



/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the command
	 * @return MementoInterface The Memento of the command
	 */
	public MementoInterface getMemento() {

		// Return empty memento
		return new MementoEnregistrer();
	}


	/**
	 * Set the Memento states of the command
	 * @param m The Memento of the command
	 */
	public void setMemento(MementoInterface m) { }



/* ################################### Memento Inner Class ################################### */
	/**
	 * The inner class for the Memento
	 */
	public class MementoEnregistrer implements MementoInterface {

		/**
		 * Empty constructor for the memento
		 */
		public MementoEnregistrer() { }

	}  // End of MementoEnregistrer class

}