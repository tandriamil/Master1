package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;
import java.util.InputMismatchException;


/**
 * Defaire command concrete class
 */
public class Refaire implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;
	private int nb_redo;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 * @param ei The caretaker to save a memento of this command
	 */
	public Refaire(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
		this.nb_redo = 0;
	}


	/**
	 * Execute the command
	 * @throws InputMismatchException If we're trying to go forward to an inexistant state
	 */
	public void execute() throws InputMismatchException {

		// Just execute the action
		this.moteur.refaire(this.getNombre());

		// No need to save this command
	}


	/**
	 * Set the number of redo we want
	 * @param n The number of redo we want
	 */
	public void setNombre(int n) {
		this.nb_redo = n;
	}


	/**
	 * Get the number of redo we want
	 * @return int The number of redo we want
	 */
	public int getNombre() {
		return this.nb_redo;
	}


	/**
	 * Put the values into the command
	 */
	public void getValues() {

		// Ask the text
		System.out.print("Veuillez entrer le nombre de fois que vous souhaitez refaire: ");
		this.setNombre(Integer.parseInt(this.ihm.getText()));
	}



/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the command
	 */
	public MementoInterface getMemento() {

		// Return empty memento
		return new MementoRefaire();
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
	public class MementoRefaire implements MementoInterface {

		/**
		 * Empty constructor for the memento
		 */
		public MementoRefaire() { }

	}  // End of MementoRejouer class

}