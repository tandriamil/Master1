package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;
import memento.MementoInterface;
import caretaker.EnregistreurInterface;


/**
 * Saisir command concrete class
 */
public class Saisir implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;
	private String texte;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 * @param ei The caretaker to save a memento of this command
	 */
	public Saisir(MoteurEditeurInterface m, IhmInterface i, EnregistreurInterface ei) {
		this.moteur = m;
		this.ihm = i;
		this.enregistreur = ei;
		this.texte = "";
	}


	/**
	 * Set the value of the text
	 * @param t The text to save
	 */
	public void setTexte(String t) {
		this.texte = t;
	}


	/**
	 * Get the value of the text
	 * @return String The text stored in this command
	 */
	public String getTexte() {
		return this.texte;
	}


	/**
	 * Put the values into the command
	 */
	public void getValues() {

		// Ask the text
		System.out.print("Veuillez entrer le texte: ");
		this.setTexte(this.ihm.getText());
	}


	/**
	 * Execute the command
	 */
	public void execute() {

		// Execute the command on the engine from the text from the ihm
		this.moteur.saisir(this.texte);

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
		MementoSaisir mem = new MementoSaisir();

		// Put values
		mem.setTexte(this.texte);

		// Return this
		return mem;
	}


	/**
	 * Set the Memento states of the command
	 * @param m The Memento of the command
	 */
	public void setMemento(MementoInterface m) {

		// Put the value saved
		this.texte = ((MementoSaisir)m).getTexte();
	}



/* ################################### Memento Inner Class ################################### */
	/**
	 * The inner class for the Memento
	 */
	public class MementoSaisir implements MementoInterface {

		// The state to save
		private String texte;


		/**
		 * Empty constructor for the memento
		 */
		public MementoSaisir() { }


		/**
		 * Set the value of the memento
		 * @param t The text to save
		 */
		public void setTexte(String t) {
			this.texte = t;
		}


		/**
		 * Get the value of the memento
		 * @return String The text to save
		 */
		public String getTexte() {
			return this.texte;
		}

	}  // End of MementoSaisir class

}