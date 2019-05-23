package gl.nozing.l10nChecker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.exception.ConfigurationException;


public class MainTest {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(MainTest.class);
	
	private Main main = null;
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
	public void configureTest() {
		
		main = new Main();
	}
	
	@Test
	public void defaultInitialationTest() {
		
		Assert.assertEquals(
				System.getProperty("user.dir"), 
				main.getArgument(Main.ARG_WORKING_DIRECTORY));
		
		Assert.assertEquals(
				Main.DEFAULT_FILE_NAME_PATTERN, 
				main.getArgument(Main.ARG_FILE_PATTERN));
	}
	
	public void checkSomethingToProcess() {
		
		String [] args = new String [2];
		args[0] = Main.ARG_INCOMPLETE_KEY_TRANSLATION;
		
		exceptionRule.expect(ConfigurationException.class);
		Main.instantiateMain(args);
		
		args[1] = "key";
		
		main = Main.instantiateMain(args);
	}
}
