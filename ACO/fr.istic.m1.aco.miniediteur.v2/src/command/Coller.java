package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;


/**
 * Coller command concrete class
 */
public class Coller implements CommandeInterface {

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
	public Coller(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
	}


	/**
	 * Execute the command
	 */
	public void execute() {

		// Execute the command on the engine from the text from the ihm
		this.moteur.coller();

		// Save this command if needed
		this.enregistreur.enregistrer(this);
	}



/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the command
	 * @return MementoInterface The Memento of the command
	 */
	public MementoInterface getMemento() {

		// Return empty memento
		return new MementoColler();
	}


	/**
	 * Set the state of the command
	 * @param m The memento to get the last state
	 */
	public void setMemento(MementoInterface m) { }



/* ################################### Memento Inner Class ################################### */
	/**
	 * The inner class for the Memento
	 */
	public class MementoColler implements MementoInterface {

		/**
		 * Empty constructor for the memento
		 */
		public MementoColler() { }

	}  // End of MementoColler class

}
