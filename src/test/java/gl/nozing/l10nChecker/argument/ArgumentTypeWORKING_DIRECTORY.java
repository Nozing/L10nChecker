package gl.nozing.l10nChecker.argument;

import org.junit.Assert;
import org.junit.Test;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

public class ArgumentTypeWORKING_DIRECTORY {

	private ArgumentType argumentTested = ArgumentType.WORKING_DIRECTORY; 
		
	@Test
	public void retriveValueTest() throws ArgumentMissingConfigurationException {

		String argumentValue = "value";
		Integer position = 0;
		String [] arguments = new String [] { 
				"-wd", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
}
