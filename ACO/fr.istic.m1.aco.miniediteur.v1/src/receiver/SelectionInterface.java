package receiver;


/**
 * Selection interface
 */
public interface SelectionInterface {

	/**
	 * Get the beginning of the selection
	 * @return int The beginning of the selection
	 */
	int getDebut();


	/**
	 * Set the beginning index
	 * @param d The beginning index
	 */
	void setDebut(int d);


	/**
	 * Get the length of the selection
	 * @return int The length of the selection
	 */
	int getLongueur();


	/**
	 * Set the length of the selection
	 * @param l The length of the selection
	 */
	void setLongueur(int l);

}
