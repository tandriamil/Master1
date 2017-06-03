package receiver;


/**
 * PressePapier concrete class
 */
public class PressePapier implements PressePapierInterface {

	// Attributes
	private String contenu;


	/**
	 * The constructor
	 */
	public PressePapier() {
		this.contenu = "";
	}


	/**
	 * Get the content
	 * @return String The current content
	 */
	public String getContenu() {
		return this.contenu;
	}


	/**
	 * Set the content
	 * @param s The content
	 */
	public void setContenu(String s) {
		this.contenu = s;
	}

}
