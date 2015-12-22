package receiver;

import java.util.InputMismatchException;
import caretaker.EnregistreurInterface;
import caretaker.GestionDefaireRefaireInterface;
import caretaker.GestionDefaireRefaire;
import memento.MementoInterface;


/**
 * MoteurEditeur concrete class
 */
public class MoteurEditeur implements MoteurEditeurInterface {

	// Attributes
	private BufferInterface buffer;
	private PressePapierInterface pressePapier;
	private GestionDefaireRefaireInterface caretaker;


	/**
	 * The constructor
	 */
	public MoteurEditeur() {

		// Initialize our attributes
		this.buffer = new Buffer();
		this.pressePapier = new PressePapier();
		this.caretaker = new GestionDefaireRefaire();

		// Create the first element of undo/redo list
		this.caretaker.sauverMoteur(this);
	}


	/**
	 * Execute the cut command
	 */
	public void couper() {

		// Get the selection and the content of the buffer
		SelectionInterface si = this.buffer.getSelection();
		StringBuilder sb = this.buffer.getContenu();

		// Only if there's a correct selection
		if (si.getLongueur() > 0) {

			// Put the content of the pastebin
			this.pressePapier.setContenu(sb.substring(si.getDebut(), (si.getDebut() + si.getLongueur())));

			// And then, remove it from the buffer
			this.buffer.setContenu(sb.delete(si.getDebut(), (si.getDebut() + si.getLongueur())));

			// Decrement the selection
			si.setLongueur(0);
		}

		// Save engine's state here
		this.caretaker.sauverMoteur(this);
	}


	/**
	 * Execute the copy command
	 */
	public void copier() {

		// Get the selection and the content of the buffer
		SelectionInterface si = this.buffer.getSelection();
		StringBuilder sb = this.buffer.getContenu();

		// Put the content of the pastebin only if there's a selection
		if (si.getLongueur() > 0) {
			this.pressePapier.setContenu(sb.substring(si.getDebut(), (si.getDebut() + si.getLongueur())));	
		}
	}


	/**
	 * Execute the paste command
	 */
	public void coller() {

		// Get the selection of the buffer
		SelectionInterface si = this.buffer.getSelection();
		StringBuilder sb = this.buffer.getContenu();

		// Only if there's a content into the pastebin
		if (this.pressePapier.getContenu().length() > 0) {

			// Append the content of the pastebin
			this.buffer.setContenu(sb.replace(si.getDebut(), (si.getDebut() + si.getLongueur()), this.pressePapier.getContenu()));

			// Increment the selection
			si.setDebut(si.getDebut() + this.pressePapier.getContenu().length());

			// Put select length to 0!
			si.setLongueur(0);
		}

		// Save engine's state here
		this.caretaker.sauverMoteur(this);
	}


	/**
	 * Execute the write command
	 * @param s The String to write
	 */
	public void saisir(String s) {

		// Get the selection of the buffer and the buffer itself
		SelectionInterface si = this.buffer.getSelection();
		StringBuilder sb = this.buffer.getContenu();

		// And append the text
		this.buffer.setContenu(sb.replace(si.getDebut(), (si.getDebut() + si.getLongueur()), s));

		// Increment the selection and put the length to 0
		si.setDebut(si.getDebut() + s.length());
		si.setLongueur(0);

		// Save engine's state here
		this.caretaker.sauverMoteur(this);
	}


	/**
	 * Execute the selection command
	 * @param d The beginning of the selection
	 * @param l The length of the selection
	 * @throws InputMismatchException If we're trying to select 
	 */
	public void selectionner(int d, int l) throws InputMismatchException {

		// If the selection is over the border of the buffer
		if ((d + l) > this.buffer.getContenu().length())
			throw new InputMismatchException("Invalid selection, over boundaries");

		// If one of the values is negative
		if ((d < 0) || (l < 0))
			throw new InputMismatchException("A selection can't accept negative values");

		// Change the values of the current selection
		SelectionInterface si = this.buffer.getSelection();
		si.setDebut(d);
		si.setLongueur(l);
	}


	/**
	 * Execute the enregistrer command
	 * @param ei The caretaker on which we'll ask to begin to save commands
	 */
	public void enregistrer(EnregistreurInterface ei) {

		// Get the current state
		boolean currentState = ei.isSaving();

		// Switch it
		ei.setSavingState(!currentState);
	}


	/**
	 * Execute the rejouer command
	 * @param ei The caretaker on which we'll ask to replay commands
	 */
	public void rejouer(EnregistreurInterface ei) {

		// Just ask to replay commands
		ei.rejouer();
	}


	/**
	 * Execute the defaire command
	 * @param n The number of time we want to undo
	 * @throws InputMismatchException If we're trying to go back to an inexistant state
	 */
	public void defaire(int n) throws InputMismatchException {

		// Just execute the action on the caretaker
		if (!this.caretaker.defaire(n, this))
			throw new InputMismatchException("Etat non existant, veuillez spécifier un autre nombre.");
	}


	/**
	 * Execute the refaire command
	 * @param n The number of time we want to redo
	 * @throws InputMismatchException If we're trying to go forward to an inexistant state
	 */
	public void refaire(int n) throws InputMismatchException {

		// Just execute the action on the caretaker
		if (!this.caretaker.refaire(n, this))
			throw new InputMismatchException("Etat non existant, veuillez spécifier un autre nombre.");
	}


	/**
	 * Get the buffer
	 * @return BufferInterface The buffer
	 */
	public BufferInterface getBuffer() {
		return this.buffer;
	}


	/**
	 * Set the buffer
	 * @param b The buffer
	 */
	public void setBuffer(BufferInterface b) {
		this.buffer = b;
	}


	/**
	 * Get the pastebin
	 * @return PressePapierInterface The pastebin
	 */
	public PressePapierInterface getPressePapier() {
		return this.pressePapier;
	}


	/**
	 * Set the pastebin
	 * @param p The pastebin
	 */
	public void setPressePapier(PressePapierInterface p) {
		this.pressePapier = p;
	}


	/**
	 * Check if the save states list is empty or not
	 * @return boolean true if no state saved, false if not
	 */
	public boolean canUndo() {
		return this.caretaker.canUndo();
	}


	/**
	 * Check if there are some elements after the current state
	 * To know if we can go in the future of this sequence
	 * @return boolean true if we can go in the future, false if not
	 */
	public boolean canRedo() {
		return this.caretaker.canRedo();
	}


	/* ################################### Memento Part ################################### */
	/**
	 * Give the Memento of the Motor 
	 * @return MementoInterface The Memento of the command
	 */
	public MementoInterface getMemento() {

		// Create the memento
		MementoInterface mem = new MementoMoteurEditeur();

		// Put values
		((MementoMoteurEditeur)mem).setBuffer(this.buffer.clone());

		// Return this memento
		return mem;
	}


	/**
	 * Set the Memento states of the command
	 * @param m The Memento of the command
	 */
	public void setMemento(MementoInterface m) {

		// Put the value saved
		this.buffer = ((MementoMoteurEditeur)m).getBuffer();
	}



	/* ################################### Memento Inner Class ################################### */
	/**
	 * The inner class for the Memento
	 */
	public class MementoMoteurEditeur implements MementoInterface {

		// The state to save
		private BufferInterface buffer;


		/**
		 * Empty constructor for the memento
		 */
		public MementoMoteurEditeur() { }


		/**
		 * Set the value of the memento
		 * @param b The buffer to save
		 */
		public void setBuffer(BufferInterface b) {
			this.buffer = b;
		}


		/**
		 * Get the value of the memento
		 * @return BufferInterface The buffer to save
		 */
		public BufferInterface getBuffer() {
			return this.buffer;
		}

	}  // End of MementoMoteurEditeur class
	
}