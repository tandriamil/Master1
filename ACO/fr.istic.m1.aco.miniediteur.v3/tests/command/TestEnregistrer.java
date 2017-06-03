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
 * Class test for Enregistrer command
 */
public class TestEnregistrer {

	// Attributes
	private Enregistrer enregistrer;
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
		this.enregistrer = new Enregistrer(this.moteur, this.ihm, this.enregistreur);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.enregistrer.execute();

		// And check that the correct command was thrown
		verify(this.moteur).enregistrer(this.enregistreur);
	}


	// Tests cases
	@Test
	public void testExecuteSwitchOff() {

		// Launch the function
		this.enregistrer.execute();

		// And check that the correct command was thrown
		verify(this.moteur).enregistrer(this.enregistreur);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.enregistrer.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Enregistrer.MementoEnregistrer);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}
