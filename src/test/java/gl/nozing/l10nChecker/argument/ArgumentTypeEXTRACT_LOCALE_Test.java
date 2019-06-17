package gl.nozing.l10nChecker.argument;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

public class ArgumentTypeEXTRACT_LOCALE_Test {

	private ArgumentType argumentTested = ArgumentType.EXTRACT_LOCALE; 

	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void retriveValueTest() throws ArgumentMissingConfigurationException {

		Integer position = 0;
		String argumentValue = "es";
		String [] arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
		
		argumentValue = "es_ES";
		arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
	
	@Test
	public void retriveValueInvalid_1_Test() throws ArgumentMissingConfigurationException {

		Integer position = 0;
		String argumentValue = "es-ES";
		String [] arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		exceptionRule.expect(ArgumentMissingConfigurationException.class);
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
	
	@Test
	public void retriveValueInvalid_2_Test() throws ArgumentMissingConfigurationException {

		Integer position = 0;
		String argumentValue = "es_ES_12";
		String [] arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		exceptionRule.expect(ArgumentMissingConfigurationException.class);
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}

	@Test
	public void retriveValueInvalid_3_Test() throws ArgumentMissingConfigurationException {

		Integer position = 0;
		String argumentValue = "es_ES_as";
		String [] arguments = new String [] { 
				"key", argumentValue, "other1", "other2" };
		
		exceptionRule.expect(ArgumentMissingConfigurationException.class);
		Assert.assertEquals(argumentValue, 
				argumentTested.retrieveValue(position, arguments));
	}
}
