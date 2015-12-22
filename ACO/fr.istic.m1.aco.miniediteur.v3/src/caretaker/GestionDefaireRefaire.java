package caretaker;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import receiver.MoteurEditeurInterface;
import memento.MementoInterface;


/**
 * GestionDefaire class
 */
public class GestionDefaireRefaire implements GestionDefaireRefaireInterface {

	// Attributes
	private List<MementoInterface> engineStates;
	private ListIterator<MementoInterface> currentState;


	/**
	 * Just initialize the list
	 */
	public GestionDefaireRefaire() {
		
		// Initialize the list
		this.engineStates = new ArrayList<MementoInterface>();

		// We put the current iterator
		this.currentState = this.engineStates.listIterator();
	}


	/**
	 * Check if the save states list is empty or not
	 * @return boolean true if no state saved, false if not
	 */
	public boolean canUndo() {
		return ((this.currentState.hasPrevious()) && (this.currentState.previousIndex() > 0));
	}


	/**
	 * Check if there are some elements after the current state
	 * To know if we can go in the future of this sequence
	 * @return boolean true if we can go in the future, false if not
	 */
	public boolean canRedo() {
		return this.currentState.hasNext();
	}


	/**
	 * Save an engine state
	 * @param mei The engine's state to save
	 */
	public void sauverMoteur(MoteurEditeurInterface mei) {

		// If we have an alternate future, we delete it
		while (this.currentState.hasNext()) {

			// And we go to the next element
			this.currentState.next();

			// Delete each alternate future state
			this.currentState.remove();
		}

		// We save the engine state
		this.engineStates.add(mei.getMemento());

		// And we put the current iterator on it
		this.currentState = this.engineStates.listIterator(this.engineStates.size());
	}


	/**
	 * Return to the precedent state
	 * @param n The number of state to go back to, and the engine that we will change
	 * @param mei The engine on which we'll put the new state
	 * @return true if we achieved it, false if we couldn't (invalid state for example)
	 */
	public boolean defaire(int n, MoteurEditeurInterface mei) {

		// We check that the number entered is greater than what we should get
		if ((this.currentState.previousIndex() - n) >= 0) {

			// We have to go one more time in the past to get the correct element
			n++;

			// The element to take
			MementoInterface mementoMoteur = null;

			// We go to last elements till we stop finding one
			while ((n > 0) && (this.currentState.hasPrevious())) {
				mementoMoteur = this.currentState.previous();
				n--;
			}

			// We go back to the correct position then
			mementoMoteur = this.currentState.next();

			// We put the saved state
			mei.setMemento(mementoMoteur);

			// And we return true
			return true;

		// If we can go back to this state, return false
		} else return false;

	}


	/**
	 * Redo some actions
	 * But if we edit the past, we can't go to the alternate future anymore
	 * @param n The number of state to go to the future, and the engine that we will change
	 * @param mei The engine on which we'll put the new state
	 * @return true if we achieved it, false if we couldn't (invalid state for example)
	 */
	public boolean refaire(int n, MoteurEditeurInterface mei) {

		// We check that the number entered is greater than what we should get
		if ((this.engineStates.size() - (this.currentState.previousIndex() + n)) > 0) {

			// The element to take
			MementoInterface mementoMoteur = null;

			// We go to last elements till we stop finding one
			while ((n > 0) && (this.currentState.hasNext())) {
				mementoMoteur = this.currentState.next();
				n--;
			}

			// We put the saved state
			mei.setMemento(mementoMoteur);

			// And we return true
			return true;

		// If we can go back to this state, return false
		} else return false;

	}

}