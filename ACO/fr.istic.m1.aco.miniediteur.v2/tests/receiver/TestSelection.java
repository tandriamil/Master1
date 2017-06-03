package receiver;

// Imports
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Class test for SelectionInterface
 */
public class TestSelection {

	// Variables
	private SelectionInterface selection;
	private int x;


	// Initialization
	@Before
	public void initialize() {
		selection = new Selection();
	}


	// Tests cases
	@Test
	public void testGetDebut() {

		// The value to test with
		x = 5;

		// Put the value as the debut of selection
		selection.setDebut(x);

		// And then test it
		assertEquals("Début non égale après appel à getDebut()", x, selection.getDebut());
	}


	@Test
	public void testSetDebut() {

		// The value to test with
		x = 35;

		// Put the value as the debut of selection
		selection.setDebut(x);

		// And then test it
		assertEquals("Début non égale après appel à setDebut()", x, selection.getDebut());
	}


	@Test
	public void testGetLongueur() {

		// The value to test with
		x = 15;

		// Put the value as the longueur of selection
		selection.setLongueur(x);

		// And then test it
		assertEquals("Longueur non égale après appel à getLongueur()", x, selection.getLongueur());
	}


	@Test
	public void testSetLongueur() {

		// The value to test with
		x = 25;

		// Put the value as the longueur of selection
		selection.setLongueur(x);

		// And then test it
		assertEquals("Longueur non égale après appel à getLongueur()", x, selection.getLongueur());
	}

}
