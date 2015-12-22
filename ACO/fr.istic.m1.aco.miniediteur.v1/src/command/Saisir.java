package command;

import receiver.MoteurEditeurInterface;
import invoker.IhmInterface;


/**
 * Saisir command concrete class
 */
public class Saisir implements CommandeInterface {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private String texte;


	/**
	 * The constructor
	 * @param m The engine of this command
	 * @param i Its Ihm to interact with the user
	 */
	public Saisir(MoteurEditeurInterface m, IhmInterface i) {
		this.moteur = m;
		this.ihm = i;
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
	}

}