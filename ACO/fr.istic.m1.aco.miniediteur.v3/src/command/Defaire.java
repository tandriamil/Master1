package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;
import java.util.InputMismatchException;


/**
 * Defaire command concrete class
 */
public class Defaire implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;
	private int nb_undo;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 * @param ei The caretaker to save a memento of this command
	 */
	public Defaire(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
		this.nb_undo = 0;
	}


	/**
	 * Set the number of undo we want
	 * @param n The number of undo we want
	 */
	public void setNombre(int n) {
		this.nb_undo = n;
	}


	/**
	 * Get the number of undo we want
	 * @return int The number of undo we want
	 */
	public int getNombre() {
		return this.nb_undo;
	}


	/**
	 * Put the values into the command
	 */
	public void getValues() {

		// Ask the text
		System.out.print("Veuillez entrer le nombre de fois que vous souhaitez défaire: ");
		this.setNombre(Integer.parseInt(this.ihm.getText()));
	}


	/**
	 * Execute the command
	 * @throws InputMismatchException If we're trying to go back to an inexistant state
	 */
	public void execute() throws InputMismatchException {

		// Just execute the action
		this.moteur.defaire(this.getNombre());

		// No need to save this command
	}



/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the command
	 */
	public MementoInterface getMemento() {

		// Return empty memento
		return new MementoDefaire();
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
	public class MementoDefaire implements MementoInterface {

		/**
		 * Empty constructor for the memento
		 */
		public MementoDefaire() { }

	}  // End of MementoRejouer class

}