package gl.nozing.l10nChecker.argument;

import org.junit.Assert;
import org.junit.Test;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

public class ArgumentTypeINCOMPLETE_TRANSLATION_Test {

	private ArgumentType argumentTested = ArgumentType.INCOMPLETE_TRANSLATION; 
		
	@Test
	public void retriveValueTest() throws ArgumentMissingConfigurationException {

		String argumentValue = "";
		Integer position = 0;
		String [] arguments = new String [] { 
				"-it", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
		
		arguments = new String [] { "-it" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
}
