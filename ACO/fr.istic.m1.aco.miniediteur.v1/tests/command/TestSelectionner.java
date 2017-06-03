package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;


/**
 * Class test for Selectionner command
 */
public class TestSelectionner {

	// Attributes
	private Selectionner selectionner;
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);

		// And put them in the command
		this.selectionner = new Selectionner(this.moteur, this.ihm);
	}


	// Tests cases
	@Test
	public void testGetValues() {

		// The values
		int debut = 5, longueur = 2;

		// Put the values
		this.selectionner.setDebut(debut);
		this.selectionner.setLongueur(longueur);

		// Check the text
		assertEquals("Content of Selectionner's debut not correct when calling getDebut()", this.selectionner.getDebut(), debut);
		assertEquals("Content of Selectionner's longueur not correct when calling getLongueur()", this.selectionner.getLongueur(), longueur);
	}


	@Test
	public void testSetValues() {

		// The values
		int debut = 5, longueur = 2;

		// Put the values
		this.selectionner.setDebut(debut);
		this.selectionner.setLongueur(longueur);

		// Check the text
		assertEquals("Content of Selectionner's debut not correct when calling setDebut()", this.selectionner.getDebut(), debut);
		assertEquals("Content of Selectionner's longueur not correct when calling setLongueur()", this.selectionner.getLongueur(), longueur);
	}


	@Test
	public void testExecute() {

		// The values
		Integer d = new Integer(5);
		Integer l = new Integer(6);

		// The setup of the getText function
		when(this.ihm.getText()).thenReturn(d.toString()).thenReturn(l.toString());

		// Get the values
		this.selectionner.getValues();
		verify(this.ihm, times(2)).getText();

		// Launch the function
		this.selectionner.execute();

		// And check that the correct command was thrown
		verify(this.moteur).selectionner(d, l);
	}


	// Tests cases
	@Test
	public void testExecuteWithoutValues() {

		// Launch the function
		this.selectionner.execute();

		// And check that the correct command was thrown
		verify(this.moteur).selectionner(0, 0);
	}

}
