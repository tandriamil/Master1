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
 * Class test for Rejouer command
 */
public class TestRejouer {

	// Attributes
	private Rejouer rejouer;
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
		this.rejouer = new Rejouer(this.moteur, this.ihm, this.enregistreur);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.rejouer.execute();

		// And check that the correct command was thrown
		verify(this.moteur).rejouer(this.enregistreur);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.rejouer.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Rejouer.MementoRejouer);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}
