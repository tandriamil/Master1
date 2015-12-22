package caretaker;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;
import command.*;


/**
 * Class test for the caretaker Enregistreur
 */
public class TestEnregistreur {

	// Attributes
	private MoteurEditeurInterface moteur;
	private EnregistreurInterface enregistreur;
	private IhmInterface ihm;
	private CommandeInterface com;
	private CommandeInterface sel;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);
		this.enregistreur = new Enregistreur();

		// The command object used here
		this.com = mock(Coller.class);
		this.sel = mock(Selectionner.class);
	}


	// Tests cases
	@Test
	public void testIsSaving() {

		// Check that we're not saving
		assertFalse(this.enregistreur.isSaving());

		// We change the state to false
		this.enregistreur.setSavingState(true);

		// Check that we're saving
		assertTrue(this.enregistreur.isSaving());

		// We change the state to false
		this.enregistreur.setSavingState(false);

		// Check that we're not saving
		assertFalse(this.enregistreur.isSaving());

		// We change the state to false again
		this.enregistreur.setSavingState(false);

		// Check that we're not saving
		assertFalse(this.enregistreur.isSaving());
	}


	@Test
	public void testIsEmpty() {

		// Check that we're not saving and that the caretaker is empty
		assertFalse(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());

		// We change the state and save a command
		this.enregistreur.setSavingState(true);
		this.enregistreur.enregistrer(this.com);

		// Check that we're saving and that the caretaker isn't empty
		assertTrue(this.enregistreur.isSaving());
		assertFalse(this.enregistreur.isEmpty());
	}


	@Test
	public void testSetSavingState() {

		// Check that we're not saving and that the caretaker is empty
		assertFalse(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());

		// We change the state to true
		this.enregistreur.setSavingState(true);

		// Check that we're saving
		assertTrue(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());

		// We save a command and check that it took it
		this.enregistreur.enregistrer(this.com);
		assertFalse(this.enregistreur.isEmpty());

		// We change the state to false and check that we're not saving
		this.enregistreur.setSavingState(false);
		assertFalse(this.enregistreur.isSaving());

		// And we change it back to true to clear it
		this.enregistreur.setSavingState(true);
		assertTrue(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());
	}


	@Test
	public void testClear() {

		// Check that we're not saving and that the caretaker is empty
		assertFalse(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());

		// We change the state to true
		this.enregistreur.setSavingState(true);

		// Check that we're saving
		assertTrue(this.enregistreur.isSaving());
		assertTrue(this.enregistreur.isEmpty());

		// We save some commands and check that it took it
		this.enregistreur.enregistrer(this.com);
		this.enregistreur.enregistrer(this.sel);

		// Check that it's still recording and that it isn't empty
		assertFalse(this.enregistreur.isEmpty());
		assertTrue(this.enregistreur.isSaving());

		// Then we clear it
		this.enregistreur.clear();

		// Check that it's still recording and that it is empty
		assertTrue(this.enregistreur.isEmpty());
		assertTrue(this.enregistreur.isSaving());

		// Then we clear it again
		this.enregistreur.clear();

		// Check that it's still recording and that it is empty
		assertTrue(this.enregistreur.isEmpty());
		assertTrue(this.enregistreur.isSaving());
	}


	@Test
	public void testEnregistrerWhileNotSaving() {

		// Check that we're not saving
		assertFalse(this.enregistreur.isSaving());

		// We save a command
		this.enregistreur.enregistrer(this.com);

		// We're not currenctly saving so no command saved
		assertTrue(this.enregistreur.isEmpty());
	}


	@Test
	public void testEnregistrer() {

		// Check that we're not saving and then change the state
		assertFalse(this.enregistreur.isSaving());
		this.enregistreur.setSavingState(true);
		assertTrue(this.enregistreur.isSaving());

		// We save a command
		this.enregistreur.enregistrer(this.com);

		// Check that we saved it
		assertFalse(this.enregistreur.isEmpty());

		// Check that we asked memento on it
		verify(this.com).getMemento();
	}


	@Test
	public void testRejouerEmpty() {

		// Check that we're not saving
		assertFalse(this.enregistreur.isSaving());

		// We try to replay it
		this.enregistreur.rejouer();
	}


	@Test
	public void testRejouerAfterClear() {

		// Check that we're not saving and then change the state
		assertFalse(this.enregistreur.isSaving());
		this.enregistreur.setSavingState(true);
		assertTrue(this.enregistreur.isSaving());

		// We save some commands
		this.enregistreur.enregistrer(this.com);
		this.enregistreur.enregistrer(this.sel);
		verify(this.com).getMemento();
		verify(this.sel).getMemento();

		// Check that we saved it
		assertFalse(this.enregistreur.isEmpty());

		// Then clear and try to replay them
		this.enregistreur.clear();
		this.enregistreur.rejouer();

		// We check that we didn't call execute methods on them
		verify(this.sel, never()).execute();
		verify(this.sel, never()).execute();
	}


	@Test
	public void testRejouer() {

		// Check that we're not saving and then change the state
		assertFalse(this.enregistreur.isSaving());
		this.enregistreur.setSavingState(true);
		assertTrue(this.enregistreur.isSaving());

		// We save some commands
		this.enregistreur.enregistrer(this.com);
		this.enregistreur.enregistrer(this.sel);
		verify(this.com).getMemento();
		verify(this.sel).getMemento();

		// Check that we saved them and try to replay them
		assertFalse(this.enregistreur.isEmpty());
		this.enregistreur.rejouer();

		// We check that we didn't call execute methods on them
		verify(this.sel).execute();
		verify(this.sel).execute();
	}

}