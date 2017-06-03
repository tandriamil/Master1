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
 * Class test for Selectionner command
 */
public class TestSelectionner {

	// Attributes
	private Selectionner selectionner;
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
		this.selectionner = new Selectionner(this.moteur, this.ihm, this.enregistreur);
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

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.selectionner);
	}


	// Tests cases
	@Test
	public void testExecuteWithoutValues() {

		// Launch the function
		this.selectionner.execute();

		// And check that the correct command was thrown
		verify(this.moteur).selectionner(0, 0);

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.selectionner);
	}


	@Test
	public void testGetMemento() {

		// Put values into selectionner
		int debut = 2, longueur = 3;
		this.selectionner.setDebut(2);
		this.selectionner.setLongueur(3);

		// Launch the function
		MementoInterface mem = this.selectionner.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Selectionner.MementoSelectionner);

		// Check the value of the memento!
		assertEquals("Selectionner's memento doesn't save correct debut value", ((Selectionner.MementoSelectionner)mem).getDebut(), debut);
		assertEquals("Selectionner's memento doesn't save correct longueur value", ((Selectionner.MementoSelectionner)mem).getLongueur(), longueur);
	}


	@Test
	public void testSetMemento() {

		// Put values into selectionner
		int debut = 2, longueur = 3;
		this.selectionner.setDebut(2);
		this.selectionner.setLongueur(3);

		// Create a new memento
		Selectionner.MementoSelectionner mem = this.selectionner.new MementoSelectionner();

		// With another value
		int memDebut = 1, memLongueur = 4;
		mem.setDebut(memDebut);
		mem.setLongueur(memLongueur);

		// Check that the value of each one is correct before
		assertEquals("Selectionner's debut value isn't right before setMemento()", this.selectionner.getDebut(), debut);
		assertEquals("Selectionner's longueur value isn't right before setMemento()", this.selectionner.getLongueur(), longueur);
		assertEquals("Selectionner's memento doesn't save correct debut value before setMemento()", ((Selectionner.MementoSelectionner)mem).getDebut(), memDebut);
		assertEquals("Selectionner's memento doesn't save correct longueur value before setMemento()", ((Selectionner.MementoSelectionner)mem).getLongueur(), memLongueur);

		// Run the method
		this.selectionner.setMemento(mem);

		// Check the values of each one after
		assertEquals("Selectionner's debut value isn't right after setMemento()", this.selectionner.getDebut(), memDebut);
		assertEquals("Selectionner's longueur value isn't right after setMemento()", this.selectionner.getLongueur(), memLongueur);
		assertEquals("Selectionner's memento doesn't save correct debut value after setMemento()", ((Selectionner.MementoSelectionner)mem).getDebut(), memDebut);
		assertEquals("Selectionner's memento doesn't save correct longueur value after setMemento()", ((Selectionner.MementoSelectionner)mem).getLongueur(), memLongueur);
	}

}
