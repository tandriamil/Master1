package receiver;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.InputMismatchException;
import caretaker.*;


/**
 * Class test for MoteurEditeurInterface
 */
public class TestMoteurEditeur {

	// Attributes
	private MoteurEditeurInterface moteur;
	private BufferInterface buffer;
	private PressePapierInterface p;
	private EnregistreurInterface enregistreur;


	// Initialization
	@Before
	public void initialize() {
		this.moteur = new MoteurEditeur();
		this.buffer = new Buffer();
		this.p = new PressePapier();
		this.enregistreur = new Enregistreur();
	}


	// Tests cases
	@Test
	public void testGetBuffer() {

		// Put the buffer
		this.moteur.setBuffer(buffer);

		// Assert that the buffer is correct
		assertEquals("Buffer not correctly set when calling getBuffer()", buffer , this.moteur.getBuffer());
	}


	@Test
	public void testSetBuffer() {

		// Put the buffer
		this.moteur.setBuffer(buffer);

		// Assert that the buffer is correct
		assertEquals("Buffer not correctly received when calling setBuffer()", buffer , this.moteur.getBuffer());
	}


	@Test
	public void testGetPressePapier() {

		// Put the Presse-papier
		this.moteur.setPressePapier(p);

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correctly received when calling getPressePapier()", p , this.moteur.getPressePapier());
	}


	@Test
	public void testSetPressePapier() {

		// Put the Presse-papier
		this.moteur.setPressePapier(p);

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correctly set when calling setPressePapier()", p , this.moteur.getPressePapier());
	}


	@Test
	public void testCanUndo() {

		// Normally false at the beginning
		assertFalse(this.moteur.canUndo());
	}


	@Test
	public void testCanRedo() {

		// Normally false at the beginning
		assertFalse(this.moteur.canRedo());
	}


	@Test
	public void testSelectionner() {

		// The values to put into the selection
		int x = 2, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range
		this.moteur.selectionner(x, y);

		// Assert that the selection values of the buffer are correct
		assertEquals("Beginning of selection not set when calling selectionner()", x, this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Length of selection not set when calling selectionner()", y, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerDebutDepasse() {

		// The values to put into the selection
		int x = 13, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range
		this.moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerLongueurDepasse() {

		// The values to put into the selection
		int x = 5, y = 10;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range
		this.moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerDebutNegatif() {

		// The values to put into the selection
		int x = -5, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range
		this.moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerLongueurNegatif() {

		// The values to put into the selection
		int x = 5, y = -5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range
		this.moteur.selectionner(x, y);
	}


	@Test
	public void testCouper() {

		// The values to put into the selection
		int x = 6, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then cut
		this.moteur.selectionner(x, y);
		this.moteur.couper();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after cut operation", new StringBuilder("Hello ").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after cut operation", "world", this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after cut operation", x, this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after cut operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCouperVide() {

		// The values to put into the selection
		int x = 6, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then cut
		this.moteur.selectionner(x, y);
		this.moteur.couper();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after cut operation", new StringBuilder("Hello world").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after cut operation", "", this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after cut operation", x, this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after cut operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCopier() {

		// The values to put into the selection
		int x = 0, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then copy
		this.moteur.selectionner(x, y);
		this.moteur.copier();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after copy operation", new StringBuilder("Hello world").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after copy operation", "Hello" , this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after copy operation", x, this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after copy operation", y, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCopierVide() {

		// The values to put into the selection
		int x = 3, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then copy
		this.moteur.selectionner(x, y);
		this.moteur.copier();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after copy operation", new StringBuilder("Hello world").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after copy operation", "" , this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after copy operation", x, this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after copy operation", y, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testColler() {

		// The values to put into the selection
		int x = 5, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Put the pastebin
		p.setContenu(" !!!");
		this.moteur.setPressePapier(p);

		// Select a range and then paste
		this.moteur.selectionner(x, y);
		this.moteur.coller();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after paste without selection operation", new StringBuilder("Hello !!! world").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after paste without selection operation", " !!!" , this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after paste operation", (x + p.getContenu().length()), this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after paste operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCollerAvecSelection() {

		// The values to put into the selection
		int x = 5, y = 4;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Put the pastebin
		p.setContenu(" !!!");
		this.moteur.setPressePapier(p);

		// Select a range and then paste
		this.moteur.selectionner(x, y);
		this.moteur.coller();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after paste operation", new StringBuilder("Hello !!!ld").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after paste operation", " !!!" , this.moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after paste operation", (x + y), this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after paste operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testSaisir() {

		// The values to put into the selection
		int x = 5, y = 0;
		String toInsert = " !!!";

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then write text
		this.moteur.selectionner(x, y);
		this.moteur.saisir(toInsert);

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after writting", new StringBuilder("Hello !!! world").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after insert operation", (x + toInsert.length()), this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after insert operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testSaisirAvecSelection() {

		// The values to put into the selection
		int x = 5, y = 6;
		String toInsert = " you";

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		this.moteur.setBuffer(buffer);

		// Select a range and then write text
		this.moteur.selectionner(x, y);
		this.moteur.saisir(toInsert);

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after writting", new StringBuilder("Hello you").toString(), this.moteur.getBuffer().getContenu().toString());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after insert operation", (x + toInsert.length()), this.moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after insert operation", 0, this.moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testEnregistrer() {

		// The caretaker to use here
		this.enregistreur = new Enregistreur();

		// Check the state before execution
		assertFalse("Saver state incorrect before executing Enregistrer command", this.enregistreur.isSaving());

		// Make it saving
		this.moteur.enregistrer(this.enregistreur);

		// Check that we're saving now
		assertTrue("Saver state incorrect after executing Enregistrer command", this.enregistreur.isSaving());
	}


	@Test
	public void testEnregistrerOnOff() {

		// The caretaker to use here
		this.enregistreur = new Enregistreur();

		// Check the state before execution
		assertFalse("Saver state incorrect before executing Enregistrer command", this.enregistreur.isSaving());

		// Make it saving
		this.moteur.enregistrer(this.enregistreur);

		// Check that we're saving now
		assertTrue("Saver state incorrect after executing Enregistrer command", this.enregistreur.isSaving());

		// Make it stop saving
		this.moteur.enregistrer(this.enregistreur);

		// Check that we're saving now
		assertFalse("Saver state incorrect after executing Enregistrer command to switch it off", this.enregistreur.isSaving());
	}


	@Test
	public void testRejouer() {

		// The caretaker to use here
		this.enregistreur = mock(Enregistreur.class);

		// Execute the function
		this.moteur.rejouer(this.enregistreur);

		// Check that we called the correct function
		verify(this.enregistreur).rejouer();
	}


	@Test
	public void testDefaire() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// We check that we can undo but not redo
		assertTrue(this.moteur.canUndo());
		assertFalse(this.moteur.canRedo());

		// Then we undo one time
		this.moteur.defaire(1);

		// We check that the this.moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// We check that we can't undo but can redo
		assertFalse(this.moteur.canUndo());
		assertTrue(this.moteur.canRedo());

		// Then we do three more actions
		this.moteur.saisir("Bonjour");
		this.moteur.saisir("Ceci");
		this.moteur.saisir("Est");
		this.moteur.saisir("Un");
		this.moteur.saisir("Test");

		// Then we undo two times
		this.moteur.defaire(2);

		// We check that we can undo or redo
		assertTrue(this.moteur.canUndo());
		assertTrue(this.moteur.canRedo());

		// We check that the this.moteur is at the correct state
		assertEquals("BonjourCeciEst", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// Then we undo three times
		this.moteur.defaire(2);

		// We check that the this.moteur is at the first state
		assertEquals("Bonjour", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());
	}


	@Test(expected=InputMismatchException.class)
	public void testDefaireOverBound() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// Then we undo one time
		this.moteur.defaire(1);

		// We check that the this.moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// Then we do three more actions
		this.moteur.saisir("Bonjour");
		this.moteur.saisir("Ceci");
		this.moteur.saisir("Est");
		this.moteur.saisir("Un");
		this.moteur.saisir("Test");

		// Then we undo a lot of times so exception
		this.moteur.defaire(20);
	}


	@Test
	public void testRefaire() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// Then we undo one time
		this.moteur.defaire(1);

		// We check that the this.moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// Then we do three more actions
		this.moteur.saisir("Bonjour");
		this.moteur.saisir("Ceci");
		this.moteur.saisir("Est");
		this.moteur.saisir("Un");
		this.moteur.saisir("Test");

		// Then we undo two times
		this.moteur.defaire(2);

		// We check that the this.moteur is at the correct state
		assertEquals("BonjourCeciEst", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// We check that we can undo or redo
		assertTrue(this.moteur.canUndo());
		assertTrue(this.moteur.canRedo());

		// Then we redo two times
		this.moteur.refaire(2);

		// We check that the this.moteur is at a correct state
		assertEquals("BonjourCeciEstUnTest", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we undo two more time
		this.moteur.defaire(2);

		// Then we do an action
		this.moteur.saisir("Test");

		// We check that the this.moteur is at the first state
		assertEquals("BonjourCeciEstTest", this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());
	}


	@Test(expected=InputMismatchException.class)
	public void testRefaireOverBound() {

		// We save the state of the engine
		BufferInterface buf = this.moteur.getBuffer().clone();
		PressePapierInterface pp = this.moteur.getPressePapier();

		// We do an action on the engine
		this.moteur.saisir("Bonjour");

		// Then we undo one time
		this.moteur.defaire(1);

		// We check that the this.moteur is at the first state
		assertEquals(buf.getContenu().toString(), this.moteur.getBuffer().getContenu().toString());
		assertEquals(pp.getContenu(), this.moteur.getPressePapier().getContenu());

		// And if we redo a lot of times
		this.moteur.refaire(20);
	}

}
