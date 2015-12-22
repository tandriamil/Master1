package receiver;

// Imports
import static org.junit.Assert.*;
import org.junit.*;
import java.lang.StringBuilder;


/**
 * Class test for BufferInterface
 */
public class TestBuffer {

	// Attributes
	private BufferInterface buffer;
	private SelectionInterface selection;
	private StringBuilder sb;


	// Initialization
	@Before
	public void initialize() {
		buffer = new Buffer();
		selection = new Selection();
		sb = new StringBuilder();
	}


	// Tests cases
	@Test
	public void testGetContenu() {

		// Put the content
		buffer.setContenu(sb);

		// Then assert that we got the same value
		assertEquals("Content of Buffer not correct when calling getContenu()", sb, buffer.getContenu());
	}


	@Test
	public void testSetContenu() {

		// Put the content
		buffer.setContenu(sb);

		// Then assert that we got the same value
		assertEquals("Content of Buffer not correct when calling setContenu()", sb, buffer.getContenu());
	}


	@Test
	public void testGetSelection() {
		
		// Put the selection
		buffer.setSelection(selection);

		// Assert that the selection is correct
		assertEquals("Content of Selection not correct when calling  getSelection()", selection, buffer.getSelection());
	}


	@Test
	public void testSetSelection() {

		// Put the selection
		buffer.setSelection(selection);

		// Assert that the selection is correct
		assertEquals("Content of Selection not correct when calling setSelection()", selection, buffer.getSelection());
	}

}