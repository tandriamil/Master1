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
		moteur.setBuffer(buffer);

		// Assert that the buffer is correct
		assertEquals("Buffer not correctly set when calling getBuffer()", buffer , moteur.getBuffer());
	}


	@Test
	public void testSetBuffer() {

		// Put the buffer
		moteur.setBuffer(buffer);

		// Assert that the buffer is correct
		assertEquals("Buffer not correctly received when calling setBuffer()", buffer , moteur.getBuffer());
	}


	@Test
	public void testGetPressePapier() {

		// Put the Presse-papier
		moteur.setPressePapier(p);

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correctly received when calling getPressePapier()", p , moteur.getPressePapier());
	}


	@Test
	public void testSetPressePapier() {

		// Put the Presse-papier
		moteur.setPressePapier(p);

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correctly set when calling setPressePapier()", p , moteur.getPressePapier());
	}


	@Test
	public void testSelectionner() {

		// The values to put into the selection
		int x = 2, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range
		moteur.selectionner(x, y);
		
		// Assert that the selection values of the buffer are correct
		assertEquals("Beginning of selection not set when calling selectionner()", x, moteur.getBuffer().getSelection().getDebut());
		assertEquals("Length of selection not set when calling selectionner()", y, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerDebutDepasse() {

		// The values to put into the selection
		int x = 13, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range
		moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerLongueurDepasse() {

		// The values to put into the selection
		int x = 5, y = 10;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range
		moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerDebutNegatif() {

		// The values to put into the selection
		int x = -5, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range
		moteur.selectionner(x, y);
	}


	@Test(expected=InputMismatchException.class)
	public void testSelectionnerLongueurNegatif() {

		// The values to put into the selection
		int x = 5, y = -5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range
		moteur.selectionner(x, y);
	}


	@Test
	public void testCouper() {

		// The values to put into the selection
		int x = 6, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then cut
		moteur.selectionner(x, y);
		moteur.couper();
		
		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after cut operation", new StringBuilder("Hello ").toString(), moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after cut operation", "world", moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after cut operation", x, moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after cut operation", 0, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCouperVide() {

		// The values to put into the selection
		int x = 6, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then cut
		moteur.selectionner(x, y);
		moteur.couper();
		
		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after cut operation", new StringBuilder("Hello world").toString(), moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after cut operation", "", moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after cut operation", x, moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after cut operation", 0, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCopier() {

		// The values to put into the selection
		int x = 0, y = 5;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then copy
		moteur.selectionner(x, y);
		moteur.copier();
		
		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after copy operation", new StringBuilder("Hello world").toString(), moteur.getBuffer().getContenu().toString());
		
		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after copy operation", "Hello" , moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after copy operation", x, moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after copy operation", y, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCopierVide() {

		// The values to put into the selection
		int x = 3, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then copy
		moteur.selectionner(x, y);
		moteur.copier();
		
		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after copy operation", new StringBuilder("Hello world").toString(), moteur.getBuffer().getContenu().toString());
		
		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after copy operation", "" , moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after copy operation", x, moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after copy operation", y, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testColler() {

		// The values to put into the selection
		int x = 5, y = 0;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Put the pastebin
		p.setContenu(" !!!");
		moteur.setPressePapier(p);

		// Select a range and then paste
		moteur.selectionner(x, y);
		moteur.coller();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after paste without selection operation", new StringBuilder("Hello !!! world").toString(), moteur.getBuffer().getContenu().toString());

		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after paste without selection operation", " !!!" , moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after paste operation", (x + p.getContenu().length()), moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after paste operation", 0, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testCollerAvecSelection() {

		// The values to put into the selection
		int x = 5, y = 4;

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Put the pastebin
		p.setContenu(" !!!");
		moteur.setPressePapier(p);

		// Select a range and then paste
		moteur.selectionner(x, y);
		moteur.coller();

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after paste operation", new StringBuilder("Hello !!!ld").toString(), moteur.getBuffer().getContenu().toString());
		
		// Assert that the Presse-papier is correct
		assertEquals("Pastebin not correct after paste operation", " !!!" , moteur.getPressePapier().getContenu());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after paste operation", (x + y), moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after paste operation", 0, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testSaisir() {

		// The values to put into the selection
		int x = 5, y = 0;
		String toInsert = " !!!";

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then write text
		moteur.selectionner(x, y);
		moteur.saisir(toInsert);
	   
		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after writting", new StringBuilder("Hello !!! world").toString(), moteur.getBuffer().getContenu().toString());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after insert operation", (x + toInsert.length()), moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after insert operation", 0, moteur.getBuffer().getSelection().getLongueur());
	}


	@Test
	public void testSaisirAvecSelection() {

		// The values to put into the selection
		int x = 5, y = 6;
		String toInsert = " you";

		// Put a content to the buffer
		buffer.setContenu(new StringBuilder("Hello world"));
		moteur.setBuffer(buffer);

		// Select a range and then write text
		moteur.selectionner(x, y);
		moteur.saisir(toInsert);

		// Assert that the Buffer is correct
		assertEquals("Buffer not correct after writting", new StringBuilder("Hello you").toString(), moteur.getBuffer().getContenu().toString());

		// Assert that the selection is correct
		assertEquals("Selection index not correct after insert operation", (x + toInsert.length()), moteur.getBuffer().getSelection().getDebut());
		assertEquals("Selection length not correct after insert operation", 0, moteur.getBuffer().getSelection().getLongueur());
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

}