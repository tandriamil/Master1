package command;

// Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

import receiver.*;
import invoker.*;


/**
 * Class test for Copier command
 */
public class TestCopier {

	// Attributes
	private Copier copier;
	private MoteurEditeurInterface moteur;
	private IhmInterface ihm;


	// Initialization
	@Before
	public void initialize() {

		// Mock the two needed classes
		this.moteur = mock(MoteurEditeur.class);
		this.ihm = mock(MyIhm.class);

		// And put them in the command
		this.copier = new Copier(this.moteur, this.ihm);
	}


	// Tests cases
	@Test
	public void testExecute() {

		// Launch the function
		this.copier.execute();

		// And check that the correct command was thrown
		verify(this.moteur).copier();
	}

}