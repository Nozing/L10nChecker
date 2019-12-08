/**
 * 
 */
package gl.nozing.l10nChecker.argument;

import java.util.regex.Pattern;

import gl.nozing.l10nChecker.argument.exception.ArgumentException;
import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;
import gl.nozing.l10nChecker.argument.exception.UnknownArgumentRuntimeException;

/**
 * Enumeration with the arguments to configure the execution of the application
 * 
 * @author nozing
 *
 */
public enum ArgumentType implements RetrieveArgumentValue {

	/**
	 * Defines the file pattern it will be used to search the internationalization 
	 * resources
	 */
	FILE_PATTERN("-fp") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ArgumentMissingConfigurationException {

			try {
				ArgumentType.checkIsValidArray(argumentPosition, arguments);
			} catch (ArgumentException e) {
				
				throw new ArgumentMissingConfigurationException(
						FILE_PATTERN, e.getMessage());
			}
			
			String value = arguments[argumentPosition + 1];
			
			if (value == null
					|| value.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						FILE_PATTERN, "Key value can't be empty or nullS");
			}
			
			return value;
		}
	},
	
	/**
	 * Defines the root directory where the application will start searching 
	 * internationalization resource files
	 */
	WORKING_DIRECTORY("-wd") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ArgumentMissingConfigurationException {

			try {
				ArgumentType.checkIsValidArray(argumentPosition, arguments);
			} catch (ArgumentException e) {
				
				throw new ArgumentMissingConfigurationException(
						WORKING_DIRECTORY, e.getMessage());
			}
			
			String value = arguments[argumentPosition + 1];
			
			if (value == null
					|| value.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						WORKING_DIRECTORY, "Key value can't be empty or nullS");
			}
			
			return value;
		}
	},
	
	/**
	 * Indicates to the application that it has to search for all the incomplete 
	 * translations for all the translation keys
	 */
	INCOMPLETE_TRANSLATION("-it"),
	
	/**
	 * Indicates to the application that it has to search for all the missing translations
	 * of a given key
	 */
	INCOMPLETE_KEY_TRANSLATION("-itk") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ArgumentMissingConfigurationException {

			try {
				ArgumentType.checkIsValidArray(argumentPosition, arguments);
			} catch (ArgumentException e) {
				
				throw new ArgumentMissingConfigurationException(
						INCOMPLETE_KEY_TRANSLATION, e.getMessage());
			}
			
			String value = arguments[argumentPosition + 1];
			
			if (value == null
					|| value.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						INCOMPLETE_KEY_TRANSLATION, "Key value can't be empty or nullS");
			}
			
			return value;
		}
	},
	
	EXTRACT_LOCALE("-el") {
		
		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments)
				throws ArgumentMissingConfigurationException {
			
			try {
				ArgumentType.checkIsValidArray(argumentPosition, arguments);
			} catch (ArgumentException e) {
				
				throw new ArgumentMissingConfigurationException(
						EXTRACT_LOCALE, e.getMessage());
			}			
			
			String value = arguments[argumentPosition + 1];
			
			if (value == null
					|| value.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						EXTRACT_LOCALE, "Key value can't be empty or null");
			}
			
			Pattern pattern = Pattern.compile("^[a-z]{2}(_[A-Z]{2}){0,1}$");
			if (!pattern.matcher(value).lookingAt()) {
				
				throw new ArgumentMissingConfigurationException(
						EXTRACT_LOCALE, String.format("Input '%s' is not a valid locale definition", value));
			}
			
			return value;
		}
	}
	;
	
	private String name;
	
	private ArgumentType(String argumentName) {
		
		this.name = argumentName;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String retrieveValue(Integer argumentPosition, String[] arguments)
			throws ArgumentMissingConfigurationException {
		
		return "";
	}
	
	/**
	 * Returns an <code>ArgumentType</code> by its name. If there is not 
	 * coincidence, the method throws an <code>
	 * UnknownArgumentRuntimeException</code>.
	 * 
	 * @param name <code>String</code> with the name of the argument
	 * @return Returns an <code>ArgumentType</code> or a runtime exception if 
	 * the <code>name</code> doesn't match with an argument type.
	 * @throws UnknownArgumentRuntimeException Runtime exception thrown if 
	 * there isn't a math between <code>name</code> and <code>
	 * ArgumentType</code>
	 */
	public static ArgumentType byName(String name) {
		
		for (ArgumentType argType : ArgumentType.values()) {
		
			if (argType.getName().equals(name)) {
				
				return argType;
			}
		}
		
		throw new UnknownArgumentRuntimeException(name);
	}
	
	/**
	 * Checks if inside an array an argument has a value
	 * @param argumentPosition Integer with the position of the argument name in the array
	 * @param arguments Array of arguments to check
	 * @throws ArgumentException Exception thrown if the array is not valid
	 */
	public static Boolean checkIsValidArray(
			Integer argumentPosition, String [] arguments) throws ArgumentException {
		
		if (arguments == null 
				|| arguments.length == 0) {
			
			throw new ArgumentException("Arguments array can't be null or empty");
		}
		
		if (argumentPosition < 0) {
			
			throw new ArgumentException( 
					String.format("Position '%s' is out of index bound (arguments length is '%s')", 
							argumentPosition, arguments.length));
		} if (argumentPosition + 1 >= arguments.length) { 
			
			throw new ArgumentException("Missing key value");
		}
		
		return true;
	}
}
