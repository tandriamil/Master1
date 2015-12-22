package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;
import java.util.InputMismatchException;


/**
 * Selectionner command concrete class
 */
public class Selectionner implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;
	private int debut, longueur;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 * @param ei The caretaker to save a memento of this command
	 */
	public Selectionner(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
		this.debut = 0;
		this.longueur = 0;
	}


	/**
	 * Set the value of the beginning of the selection
	 * @param d The beginning of the selection
	 */
	public void setDebut(int d) {
		this.debut = d;
	}


	/**
	 * Get the value of the beginning of the selection
	 * @return int The beginning of the selection
	 */
	public int getDebut() {
		return this.debut;
	}


	/**
	 * Set the value of the length of the selection
	 * @param l The length of the selection
	 */
	public void setLongueur(int l) {
		this.longueur = l;
	}


	/**
	 * Get the value of the length of the selection
	 * @return int The length of the selection
	 */
	public int getLongueur() {
		return this.longueur;
	}


	/**
	 * Put the values into the command
	 * @throws NumberFormatException If we enter non integer conversible characters
	 */
	public void getValues() throws NumberFormatException {

		// Ask the beginning
		System.out.print("Début de la sélection: ");
		this.setDebut(Integer.parseInt(this.ihm.getText()));

		// The length
		System.out.print("Longueur de la sélection: ");
		this.setLongueur(Integer.parseInt(this.ihm.getText()));
	}


	/**
	 * Execute the command
	 * @throws InputMismatchException If we're trying to select 
	 */
	public void execute() throws InputMismatchException {

		// Execute the command on the engine from the text from the ihm
		this.moteur.selectionner(this.debut, this.longueur);

		// Save this command if needed
		this.enregistreur.enregistrer(this);
	}



/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the command
	 * @return MementoInterface The Memento of the command
	 */
	public MementoInterface getMemento() {

		// Create the memento
		MementoSelectionner mem = new MementoSelectionner();

		// Put values
		mem.setDebut(this.debut);
		mem.setLongueur(this.longueur);

		// Return this
		return mem;
	}


	/**
	 * Set the Memento states of the command
	 * @param m The Memento of the command
	 */
	public void setMemento(MementoInterface m) {

		// Put the values saved
		this.debut = ((MementoSelectionner)m).getDebut();
		this.longueur = ((MementoSelectionner)m).getLongueur();
	}



/* ################################### Memento Inner Class ################################### */
	/**
	 * The inner class for the Memento
	 */
	public class MementoSelectionner implements MementoInterface {

		// The state to save
		private int debut;
		private int longueur;


		/**
		 * Empty constructor for the memento
		 */
		public MementoSelectionner() { }


		/**
		 * Set the value of the beginning of the selection
		 * @param d The beginning of the selection
		 */
		public void setDebut(int d) {
			this.debut = d;
		}


		/**
		 * Get the value of the beginning of the selection
		 * @return int The beginning of the selection
		 */
		public int getDebut() {
			return this.debut;
		}


		/**
		 * Set the value of the length of the selection
		 * @param l The length of the selection
		 */
		public void setLongueur(int l) {
			this.longueur = l;
		}


		/**
		 * Get the value of the memento
		 * @return int The length of the selection
		 */
		public int getLongueur() {
			return this.longueur;
		}

	}  // End of MementoSelectionner class

}