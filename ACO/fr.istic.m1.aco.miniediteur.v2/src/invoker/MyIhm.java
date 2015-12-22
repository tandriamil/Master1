package invoker;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.InputStream;
import receiver.MoteurEditeurInterface;
import command.*;
import caretaker.EnregistreurInterface;
import caretaker.Enregistreur;


/**
 * An ihm to receive the user inputs
 */
public class MyIhm implements IhmInterface {

	// Constants
	private static final String HELP_CMD = "h";
	private static final String SELECT_CMD = "s";
	private static final String INSERT_CMD = "i";
	private static final String COPY_CMD = "c";
	private static final String CUT_CMD = "x";
	private static final String PASTE_CMD = "v";
	private static final String SAVE_CMD = "e";
	private static final String REPLAY_CMD = "r";
	private static final String QUIT_CMD = "q";

	// Attributes
	private MoteurEditeurInterface moteur;
	private EnregistreurInterface enregistreur;
	private Scanner scanner;

	// Commands
	private Map<String, CommandeInterface> commandes;


	/**
	 * The constructor
	 * @param inputStream The InputStream to get the users inputs
	 * @param m The engine to perform commands on
	 */
	public MyIhm(InputStream inputStream, MoteurEditeurInterface m) {

		// Initialize attributes
		this.scanner = new Scanner(inputStream);
		this.moteur = m;

		// The caretaker to save the commands
		this.enregistreur = new Enregistreur();

		// The commands
		this.commandes = new HashMap<String, CommandeInterface>();
		this.commandes.put(INSERT_CMD, new Saisir(this.moteur, this, this.enregistreur));
		this.commandes.put(SELECT_CMD, new Selectionner(this.moteur, this, this.enregistreur));
		this.commandes.put(COPY_CMD, new Copier(this.moteur, this, this.enregistreur));
		this.commandes.put(CUT_CMD, new Couper(this.moteur, this, this.enregistreur));
		this.commandes.put(PASTE_CMD, new Coller(this.moteur, this, this.enregistreur));
		this.commandes.put(SAVE_CMD, new Enregistrer(this.moteur, this, this.enregistreur));
		this.commandes.put(REPLAY_CMD, new Rejouer(this.moteur, this, this.enregistreur));
	}


	/**
	 * Give the text entered by the user
	 * @return String The text given
	 */
	public String getText() {
		return this.scanner.nextLine();
	}


	/**
	 * Just display the help page
	 */
	private void displayHelp() {

		// Display the commands
		System.out.println("Les commandes disponibles sont:");
		System.out.println("	" + HELP_CMD + "  -> Aide");
		System.out.println("	" + SELECT_CMD + "  -> Sélectionner");
		System.out.println("	" + INSERT_CMD + "  -> Insérer");
		System.out.println("	" + COPY_CMD + "  -> Copier");
		System.out.println("	" + CUT_CMD + "  -> Couper");
		System.out.println("	" + PASTE_CMD + "  -> Coller");

		// If currently saving
		if (this.enregistreur.isSaving()) {
			System.out.println("	" + SAVE_CMD + "  -> Fin enregistrement");
		} else {
			System.out.println("	" + SAVE_CMD + "  -> Début enregistrement");

			// Only if the saver isn't empty
			if (!this.enregistreur.isEmpty())
				System.out.println("	" + REPLAY_CMD + "  -> Rejouer");
		}

		System.out.println("	" + QUIT_CMD + "  -> Quitter");
	}


	/**
	 * Reads from the standard input until EOF. Parses user inputs as
	 * instructions for the editor. Vim-like shortcuts. Customize to
	 * your own preferences. Ctrl-D terminates the loop.
	 */
	public void loop() {

		// Create the Scanner from the standard input
		String input;

		// Display the help
		displayHelp();

		// Ask the user to enter a command
		System.out.print("Commande: ");

		// While he put an input
		while (scanner.hasNext()) {

			// Try in case of exceptions
			try {

				// Get the new input
				input = scanner.nextLine();

				// In function of the command that he akss
				switch (input) {

					// Help
					case HELP_CMD:
						// We always display help at the end
						break;


					// Select
					case SELECT_CMD:
						// Ask values first
						((Selectionner)this.commandes.get(SELECT_CMD)).getValues();
						this.commandes.get(SELECT_CMD).execute();
						break;


					// Insert
					case INSERT_CMD:
						// Ask values first
						((Saisir)this.commandes.get(INSERT_CMD)).getValues();
						this.commandes.get(INSERT_CMD).execute();
						break;


					// Copy
					case COPY_CMD:
						this.commandes.get(COPY_CMD).execute();
						break;


					// Cut
					case CUT_CMD:
						this.commandes.get(CUT_CMD).execute();
						break;


					// Paste
					case PASTE_CMD:
						this.commandes.get(PASTE_CMD).execute();
						break;


					// Save
					case SAVE_CMD:
						this.commandes.get(SAVE_CMD).execute();

						// Display a message
						if (this.enregistreur.isSaving())
							System.out.println("Enregistrement activé.");
						else
							System.out.println("Enregistrement bouclé.");
						break;


					// Replay
					case REPLAY_CMD:

						// Only if not empty and not in saving state
						if ((!this.enregistreur.isSaving()) && (!this.enregistreur.isEmpty()))
							this.commandes.get(REPLAY_CMD).execute();
						else {
							System.out.println("Vous devez enregistrer des commandes avant de pouvoir les réexecuter.");
							System.out.println("Veuillez sélectionner une autre commande.");
						}
						break;


					// Quit
					case QUIT_CMD:

						// Avoid leaks
						scanner.close();

						// Say goodbye
						System.out.println("\nMerci d'avoir utiliser notre merveilleux editeur. Au revoir.");
						System.exit(0);
						break;


					// Default action
					default:
						System.out.println("Commande non reconnue '" + input + "'");
				
				}  // End of switch

			}  // End of try

			// Invalid selection
			catch (InputMismatchException ime) {
				System.out.println("Sélection invalide car dépassant les bornes de l'application.\nMessage associé:\n" + ime.getMessage() + "\n");
			}

			// Invalid string given instead of numbers
			catch (NumberFormatException nfe) {
				System.out.println("Nombre invalide, veuillez recommencer.\nMessage associé:\n" + nfe.getMessage() + "\n");
			}

			// Display the current state
			System.out.println("Selection en cours: [" + this.moteur.getBuffer().getSelection().getDebut() + ", " + this.moteur.getBuffer().getSelection().getLongueur() + "]");
			System.out.println("Tampon en cours: " + this.moteur.getBuffer().getContenu().toString());
			System.out.println("Presse-papier en cours: " + this.moteur.getPressePapier().getContenu());
			System.out.println();

			// Ask a new command
			displayHelp();
			System.out.print("Commande: ");

		}  // End of while

	}  // End of loop() method

}