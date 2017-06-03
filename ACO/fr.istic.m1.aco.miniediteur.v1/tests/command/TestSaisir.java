package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;


/**
 * Class test for Saisir command
 */
public class TestSaisir {

	// Attributes
	private Saisir saisir;
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);

		// And put them in the command
		this.saisir = new Saisir(this.moteur, this.ihm);
	}


	// Tests cases
	@Test
	public void testGetTexte() {

		// The return value
		String ret = "Test";

		// Put the text
		this.saisir.setTexte(ret);

		// Check the text
		assertEquals("Content of Saisir not correct when calling getTexte()", this.saisir.getTexte(), ret);
	}


	@Test
	public void testSetTexte() {

		// The return value
		String ret = "Test";

		// Put the text
		this.saisir.setTexte(ret);

		// Check the text
		assertEquals("Content of Saisir not correct when calling setTexte()", this.saisir.getTexte(), ret);
	}


	@Test
	public void testExecute() {

		// The return value
		String ret = "Test";

		// The setup of the getText function
		when(this.ihm.getText()).thenReturn(ret);

		// Get the values
		this.saisir.getValues();
		verify(this.ihm).getText();

		// Launch the function
		this.saisir.execute();

		// And check that the correct command was thrown
		verify(this.moteur).saisir(ret);
	}


	// Tests cases
	@Test
	public void testExecuteWithoutValues() {

		// Launch the function
		this.saisir.execute();

		// And check that the correct command was thrown
		verify(this.moteur).saisir("");
	}

}
