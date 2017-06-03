package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;


/**
 * Couper command concrete class
 */
public class Couper implements CommandeInterface {

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
	public Couper(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
	}


	/**
	 * Execute the command
	 */
	public void execute() {

		// Execute the command on the engine from the text from the ihm
		this.moteur.couper();

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
		return new MementoCouper();
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
	public class MementoCouper implements MementoInterface {

		/**
		 * Empty constructor for the memento
		 */
		public MementoCouper() { }

	}  // End of MementoCouper class

}
