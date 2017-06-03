package client;

import receiver.MoteurEditeurInterface;
import receiver.MoteurEditeur;
import invoker.IhmInterface;
import invoker.MyIhm;


/**
 * Our client class, Editeur
 */
public class Editeur {

	// Attributes
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;


	/**
	 * The constructor
	 */
	public Editeur() {

		// Initialize attributes
		this.moteur = new MoteurEditeur();
		this.ihm = new MyIhm(System.in, this.moteur);
	}


	/**
	 * The main loop
	 * @param args The arguments in a String table
	 */
	public static void main(String[] args) {
		Editeur editeur = new Editeur();
		editeur.ihm.loop();
	}

}
