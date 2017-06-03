package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;


/**
 * Selectionner command concrete class
 */
public class Selectionner implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private int debut, longueur;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 */
	public Selectionner(MoteurEditeurInterface m, IhmInterface i) {
		this.moteur = m;
		this.ihm = i;
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
	 */
	public void execute() {

		// Execute the command on the engine from the text from the ihm
		this.moteur.selectionner(this.debut, this.longueur);
	}

}
