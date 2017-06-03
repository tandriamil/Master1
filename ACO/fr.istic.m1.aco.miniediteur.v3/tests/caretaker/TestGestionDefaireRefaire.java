package caretaker;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.io.*;

import receiver.*;
import invoker.*;
import memento.*;
import command.*;


/**
 * Class test for GestionDefaireRefaire interface
 */
public class TestGestionDefaireRefaire {

	// Attributes
	private MoteurEditeurInterface moteur;
	private GestionDefaireRefaireInterface gestionDefaireRefaire;
	private IhmInterface ihm;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = new MoteurEditeur();
		this.ihm = new MyIhm(mock(InputStream.class), this.moteur);
		this.gestionDefaireRefaire = new GestionDefaireRefaire();

		// And we save the first state
		// Exactly like the engine does
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
	}


	// Test cases
	@Test
	public void testCanUndoInitialization() {

		// Check that we can't undo at the beginning
		assertFalse(this.gestionDefaireRefaire.canUndo());
	}


	// Test cases
	@Test
	public void testCanRedoInitialization() {

		// Check that we can't redo at the beginning
		assertFalse(this.gestionDefaireRefaire.canRedo());
	}


	@Test
	public void testSauverMoteurInitialization() {

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// And we save this state
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// We check that we can undo but not redo
		assertTrue(this.gestionDefaireRefaire.canUndo());
		assertFalse(this.gestionDefaireRefaire.canRedo());
	}


	@Test
	public void testDefaire() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// And we save this state
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// We check that we can undo but not redo
		assertTrue(this.gestionDefaireRefaire.canUndo());
		assertFalse(this.gestionDefaireRefaire.canRedo());

		// Then we undo one time
		assertTrue(this.gestionDefaireRefaire.defaire(1, this.moteur));

		// We check that the moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// We check that we can't undo but can redo
		assertFalse(this.gestionDefaireRefaire.canUndo());
		assertTrue(this.gestionDefaireRefaire.canRedo());

		// Then we do three more actions
		this.moteur.saisir("Bonjour");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Ceci");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Est");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Un");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Test");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// Then we undo two times
		assertTrue(this.gestionDefaireRefaire.defaire(2, this.moteur));

		// We check that the moteur is at the correct state
		assertEquals("BonjourCeciEst", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we undo a lot of times
		assertFalse(this.gestionDefaireRefaire.defaire(20, this.moteur));

		// Then we undo two times
		assertTrue(this.gestionDefaireRefaire.defaire(2, this.moteur));

		// We check that we can undo or redo
		assertTrue(this.gestionDefaireRefaire.canUndo());
		assertTrue(this.gestionDefaireRefaire.canRedo());

		// We check that the moteur is at the first state
		assertEquals("Bonjour", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we undo two more time
		assertFalse(this.gestionDefaireRefaire.defaire(2, this.moteur));
	}


	@Test
	public void testRefaire() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// And we save this state
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// We check that we can undo but not redo
		assertTrue(this.gestionDefaireRefaire.canUndo());
		assertFalse(this.gestionDefaireRefaire.canRedo());

		// Then we undo one time
		assertTrue(this.gestionDefaireRefaire.defaire(1, this.moteur));

		// We check that the moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// Then we do three more actions
		this.moteur.saisir("Bonjour");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Ceci");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Est");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Un");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);
		this.moteur.saisir("Test");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// Then we undo two times
		assertTrue(this.gestionDefaireRefaire.defaire(2, this.moteur));

		// We check that we can undo or redo
		assertTrue(this.gestionDefaireRefaire.canUndo());
		assertTrue(this.gestionDefaireRefaire.canRedo());

		// We check that the moteur is at the correct state
		assertEquals("BonjourCeciEst", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we redo a lot of times
		assertFalse(this.gestionDefaireRefaire.refaire(20, this.moteur));

		// Then we redo two times
		assertTrue(this.gestionDefaireRefaire.refaire(2, this.moteur));

		// We check that the moteur is at a correct state
		assertEquals("BonjourCeciEstUnTest", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we undo two more time
		assertTrue(this.gestionDefaireRefaire.defaire(2, this.moteur));

		// Then we do an action
		this.moteur.saisir("Test");
		this.gestionDefaireRefaire.sauverMoteur(this.moteur);

		// We check that the moteur is at the first state
		assertEquals("BonjourCeciEstTest", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And normally, we can't redo anymore
		assertFalse(this.gestionDefaireRefaire.refaire(1, this.moteur));
	}

}
