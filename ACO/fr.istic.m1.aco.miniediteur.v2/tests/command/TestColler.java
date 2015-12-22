package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;
import memento.*;
import caretaker.*;


/**
 * Class test for Coller command
 */
public class TestColler {

	// Attributes
	private Coller coller;
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;
	private EnregistreurInterface enregistreur;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);
		this.enregistreur = mock(Enregistreur.class);

		// And put them in the command
		this.coller = new Coller(this.moteur, this.ihm, this.enregistreur);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.coller.execute();

		// And check that the correct command was thrown
		verify(this.moteur).coller();

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.coller);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.coller.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Coller.MementoColler);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}