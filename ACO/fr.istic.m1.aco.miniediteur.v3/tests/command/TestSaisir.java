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
 * Class test for Saisir command
 */
public class TestSaisir {

	// Attributes
	private Saisir saisir;
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
		this.saisir = new Saisir(this.moteur, this.ihm, this.enregistreur);
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

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.saisir);
	}


	// Tests cases
	@Test
	public void testExecuteWithoutValues() {

		// Launch the function
		this.saisir.execute();

		// And check that the correct command was thrown
		verify(this.moteur).saisir("");

		// And that the command asked to be saved
		verify(this.enregistreur).enregistrer(this.saisir);
	}


	@Test
	public void testGetMemento() {

		// Put values into saisir
		String text = "Texte";
		this.saisir.setTexte(text);

		// Launch the function
		MementoInterface mem = this.saisir.getMemento();

		// Check that it's the correct class
		assertTrue(mem instanceof Saisir.MementoSaisir);

		// Check the value of the memento!
		assertEquals("Saisir's memento doesn't save correct value", ((Saisir.MementoSaisir)mem).getTexte(), text);
	}


	@Test
	public void testSetMemento() {

		// Put values into saisir
		String text = "Texte";
		this.saisir.setTexte(text);

		// Create a new memento
		Saisir.MementoSaisir mem = this.saisir.new MementoSaisir();

		// With another value
		String memText = "Memento";
		mem.setTexte(memText);

		// Check that the value of each one is correct before
		assertEquals("Saisir's text value isn't right before setMemento()", this.saisir.getTexte(), text);
		assertEquals("Memento's texte value isn't right before setMemento()", mem.getTexte(), memText);

		// Run the method
		this.saisir.setMemento(mem);

		// Check the values of each one after
		assertEquals("Saisir's text value isn't right after setMemento()", this.saisir.getTexte(), memText);
		assertEquals("Memento's texte value isn't right after setMemento()", mem.getTexte(), memText);
	}

}