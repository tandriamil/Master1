package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.InputMismatchException;

import receiver.*;
import invoker.*;
import memento.*;
import caretaker.*;


/**
 * Class test for Refaire command
 */
public class TestRefaire {

	// Attributes
	private Refaire refaire;
	private IhmInterface ihm;
	private MoteurEditeurInterface moteur;
	private EnregistreurInterface enregistreur;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);
		this.enregistreur = mock(Enregistreur.class);

		// And put them in the command
		this.refaire = new Refaire(this.moteur, this.ihm, this.enregistreur);
	}


	@Test
	public void testGetNombre() {

		// The return value
		int nb = 5;

		// Put the text
		this.refaire.setNombre(nb);

		// Check the text
		assertEquals("Content of Refaire not correct when calling getNombre()", this.refaire.getNombre(), nb);
	}


	@Test
	public void testSetNombre() {

		// The return value
		int nb = 5;

		// Put the text
		this.refaire.setNombre(nb);

		// Check the text
		assertEquals("Content of Refaire not correct when calling getNombre()", this.refaire.getNombre(), nb);
	}


	@Test
	public void testExecute() {

		// The return value
		int nb = 5;

		// The setup of the getText function
		when(this.ihm.getText()).thenReturn(String.valueOf(nb));

		// Get the values
		this.refaire.getValues();
		verify(this.ihm).getText();

		// Launch the function
		this.refaire.execute();

		// And check that the correct command was thrown
		verify(this.moteur).refaire(nb);
	}


	// Tests cases
	@Test(expected=InputMismatchException.class)
	public void testExecuteWithoutValues() {

		// Launch the function
		this.refaire.execute();

		// And check that the correct command was thrown
		verify(this.moteur).refaire(0);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.refaire.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Refaire.MementoRefaire);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}
