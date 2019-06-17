package gl.nozing.l10nChecker.argument;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

public class ArgumentTypeINCOMPLETE_KEY_TRANSLATION_Test {

	private ArgumentType argumentTested = ArgumentType.INCOMPLETE_KEY_TRANSLATION; 

	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void retriveValueTest() throws ArgumentMissingConfigurationException {

		String argumentValue = "value";
		Integer position = 0;
		String [] arguments = new String [] { 
				"-itk", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));	
		
		position = 0;
		arguments = new String [] { 
				"-itk", null };
		
		exceptionRule.expect(ArgumentMissingConfigurationException.class);
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));	
	}
}
