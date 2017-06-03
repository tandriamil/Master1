package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;


/**
 * Class test for Coller command
 */
public class TestColler {

	// Attributes
	private Coller coller;
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);

		// And put them in the command
		this.coller = new Coller(this.moteur, this.ihm);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.coller.execute();

		// And check that the correct command was thrown
		verify(this.moteur).coller();
	}

}
