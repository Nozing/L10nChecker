package gl.nozing.l10nChecker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.argument.ArgumentType;
import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

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
				main.getArgument(ArgumentType.WORKING_DIRECTORY));
		
		Assert.assertEquals(
				Main.DEFAULT_FILE_NAME_PATTERN, 
				main.getArgument(ArgumentType.FILE_PATTERN));
	}
	
	@Test
	public void checkSomethingToProcess() throws ArgumentMissingConfigurationException {
		
		String [] args = new String [2];
		args[0] = ArgumentType.INCOMPLETE_KEY_TRANSLATION.getName();
		
		exceptionRule.expect(ArgumentMissingConfigurationException.class);
		Main.instantiateMain(args);
		
		args[1] = "key";
		
		main = Main.instantiateMain(args);
	}
}
