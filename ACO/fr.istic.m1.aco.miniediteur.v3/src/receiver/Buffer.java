package receiver;


/**
 * Buffer concrete class
 */
public class Buffer implements BufferInterface {

	// Attributes
	private StringBuilder contenu;
	private SelectionInterface selection;


	/**
	 * The constructor
	 */
	public Buffer() {
		this.contenu = new StringBuilder("");
		this.selection = new Selection();
	}


	/**
	 * Get the content of the buffer
	 * @return StringBuilder The current content
	 */
	public StringBuilder getContenu() {
		return this.contenu;
	}


	/**
	 * Set the content of the buffer
	 * @param s The content to set
	 */
	public void setContenu(StringBuilder s) {
		this.contenu = s;
	}


	/**
	 * Get the current selection
	 * @return SelectionInterface The current selection
	 */
	public SelectionInterface getSelection() {
		return this.selection;
	}


	/**
	 * Set the selection
	 * @param s The selection to set
	 */
	public void setSelection(SelectionInterface s) {
		this.selection = s;
	}


	/**
	 * Clone the buffer to save its state
	 * @return BufferInterface The buffer cloned
	 */
	public BufferInterface clone() {

		// Create the clone object
		BufferInterface clone = new Buffer();

		// Create its cloned attributes
		Selection sel_clone = new Selection();
		sel_clone.setDebut(this.selection.getDebut());
		sel_clone.setLongueur(this.selection.getLongueur());

		StringBuilder cont_clone = new StringBuilder(this.contenu);

		// And put them
		clone.setSelection(sel_clone);
		clone.setContenu(cont_clone);

		// And in the end, return the clone
		return clone;
	}

}