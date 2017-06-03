package receiver;

import java.util.InputMismatchException;


/**
 * MoteurEditeur concrete class
 */
public class MoteurEditeur implements MoteurEditeurInterface {

	// Attributes
	private BufferInterface buffer;
	private PressePapierInterface pressePapier;


	/**
	 * The constructor
	 */
	public MoteurEditeur() {
		this.buffer = new Buffer();
		this.pressePapier = new PressePapier();
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

}
