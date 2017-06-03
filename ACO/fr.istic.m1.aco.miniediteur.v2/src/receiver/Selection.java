package receiver;


/**
 * Selection concrete class
 */
public class Selection implements SelectionInterface {

	// Attributes
	private int debut;
	private int longueur;


	/**
	 * The constructor
	 */
	public Selection() {
		this.debut = 0;
		this.longueur = 0;
	}


	/**
	 * Get the beginning of the selection
	 * @return int The beginning of the selection
	 */
	public int getDebut() {
		return this.debut;
	}


	/**
	 * Set the beginning index
	 * @param d The beginning index
	 */
	public void setDebut(int d) {
		this.debut = d;
	}


	/**
	 * Get the length of the selection
	 * @return int The length of the selection
	 */
	public int getLongueur() {
		return this.longueur;
	}


	/**
	 * Set the length of the selection
	 * @param l The length of the selection
	 */
	public void setLongueur(int l) {
		this.longueur = l;
	}

}
