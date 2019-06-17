package gl.nozing.l10nChecker.argument;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gl.nozing.l10nChecker.argument.exception.ArgumentException;

public class ArgumentTypeTest {

	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void checkIsValidArray_ArrayIsNullTest() throws ArgumentException {
		
		Integer position = 0;
		String [] arguments = null;
		
		exceptionRule.expect(ArgumentException.class);
		ArgumentType.checkIsValidArray(position, arguments);		
	}
	
	@Test
	public void checkIsValidArray_ArrayIsEmptyTest() throws ArgumentException {
		
		Integer position = 0;
		String [] arguments = new String [0];
		
		exceptionRule.expect(ArgumentException.class);
		ArgumentType.checkIsValidArray(position, arguments);		
	}
	
	@Test
	public void checkIsValidArray_NoValueTest() throws ArgumentException {
		
		Integer position = 0;
		String [] arguments = new String [1];
		
		exceptionRule.expect(ArgumentException.class);
		ArgumentType.checkIsValidArray(position, arguments);	
	}
	
	@Test
	public void checkIsValidArray_IncorrectPositionTest() throws ArgumentException {
		
		Integer position = -1;
		String [] arguments = new String [] { "value" };
		
		exceptionRule.expect(ArgumentException.class);
		ArgumentType.checkIsValidArray(position, arguments);	
	}
	
	@Test
	public void checkIsValidArray_IncorrectPosition2Test() throws ArgumentException {
		
		Integer position = 2;
		String [] arguments = new String [] { "value" };
		
		exceptionRule.expect(ArgumentException.class);
		ArgumentType.checkIsValidArray(position, arguments);	
	}
		
	@Test
	public void checkIsValidArrayTest() throws ArgumentException {
		
		Integer position = 0;
		String [] arguments = new String [] { "key", "value" };
		
		ArgumentType.checkIsValidArray(position, arguments);	
	}
}
