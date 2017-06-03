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
 * Class test for Couper command
 */
public class TestCouper {

	// Attributes
	private Couper couper;
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
		this.couper = new Couper(this.moteur, this.ihm, this.enregistreur);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.couper.execute();

		// And check that the correct command was thrown
		verify(this.moteur).couper();

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.couper);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.couper.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Couper.MementoCouper);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}
