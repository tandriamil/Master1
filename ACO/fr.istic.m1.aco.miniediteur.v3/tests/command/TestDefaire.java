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
 * Class test for Defaire command
 */
public class TestDefaire {

	// Attributes
	private Defaire defaire;
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
		this.defaire = new Defaire(this.moteur, this.ihm, this.enregistreur);
	}


	@Test
	public void testGetNombre() {

		// The return value
		int nb = 5;

		// Put the text
		this.defaire.setNombre(nb);

		// Check the text
		assertEquals("Content of Defaire not correct when calling getNombre()", this.defaire.getNombre(), nb);
	}


	@Test
	public void testSetNombre() {

		// The return value
		int nb = 5;

		// Put the text
		this.defaire.setNombre(nb);

		// Check the text
		assertEquals("Content of Defaire not correct when calling getNombre()", this.defaire.getNombre(), nb);
	}


	@Test
	public void testExecute() {

		// The return value
		int nb = 5;

		// The setup of the getText function
		when(this.ihm.getText()).thenReturn(String.valueOf(nb));

		// Get the values
		this.defaire.getValues();
		verify(this.ihm).getText();

		// Launch the function
		this.defaire.execute();

		// And check that the correct command was thrown
		verify(this.moteur).defaire(nb);
	}


	// Tests cases
	@Test(expected=InputMismatchException.class)
	public void testExecuteWithoutValues() {

		// Launch the function
		this.defaire.execute();

		// And check that the correct command was thrown
		verify(this.moteur).defaire(0);
	}


	@Test
	public void testGetMemento() {

		// Launch the function
		MementoInterface mem = this.defaire.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Defaire.MementoDefaire);
	}


	@Test
	public void testSetMemento() {
		// Empty test because no action
	}

}
