package receiver;


/**
 * Buffer interface
 */
public interface BufferInterface {

	/**
	 * Get the content of the buffer
	 * @return StringBuilder The current content
	 */
	StringBuilder getContenu();


	/**
	 * Set the content of the buffer
	 * @param s The content to set
	 */
	void setContenu(StringBuilder s);


	/**
	 * Get the current selection
	 * @return SelectionInterface The current selection
	 */
	SelectionInterface getSelection();


	/**
	 * Set the selection
	 * @param s The selection to set
	 */
	void setSelection(SelectionInterface s);


	/**
	 * Clone the buffer to save its state
	 * @return BufferInterface The buffer cloned
	 */
	BufferInterface clone();

}