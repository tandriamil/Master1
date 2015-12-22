package receiver;

// Imports
import static org.junit.Assert.*;
import org.junit.*;


/**
 * Class test for PressePapierInterface
 */
public class TestPressePapier {

	// Attributes
	private PressePapierInterface p;
	private String s;


	// Initialization
	@Before
	public void initialize() {
		p = new PressePapier();
	}


	// Tests cases
	@Test
	public void testGetContenu() {

		// Put some value to the string
		s = "Hello";
				
		// Put the content
		p.setContenu(s);
				
		// Then assert that we got the same value
		assertEquals("Content of Pastebin not correct when calling getContenu()", s, p.getContenu());
	}



	@Test
	public void testSetContenu() {

		// Put some value into the string
		s = "World";
		
		// Put the content
		p.setContenu(s);
		
		// Then assert that we got the same value
		assertEquals("Content of Pastebin not correct when calling setContenu()", s, p.getContenu());
	}

}