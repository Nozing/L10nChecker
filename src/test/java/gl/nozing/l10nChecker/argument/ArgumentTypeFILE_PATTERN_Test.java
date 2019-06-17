package gl.nozing.l10nChecker.argument;

import org.junit.Assert;
import org.junit.Test;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

public class ArgumentTypeFILE_PATTERN_Test {

	private ArgumentType argumentTested = ArgumentType.FILE_PATTERN; 
		
	@Test
	public void retriveValueTest() throws ArgumentMissingConfigurationException {

		String argumentValue = "value";
		Integer position = 0;
		String [] arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
}
