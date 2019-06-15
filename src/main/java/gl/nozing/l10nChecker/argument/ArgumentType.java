/**
 * 
 */
package gl.nozing.l10nChecker.argument;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;
import gl.nozing.l10nChecker.argument.exception.UnknownArgumentRuntimeException;

/**
 * Enum with the arguments to configure the execution of the application
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

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						FILE_PATTERN, "Key value can't be empty");
			}
			
			return key;
		}
	},
	
	/**
	 * Defines the root directory where the application will start searching 
	 * internationalization resource files
	 */
	WORKING_DIRECTORY("-wd") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ArgumentMissingConfigurationException {

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						WORKING_DIRECTORY, "Key value can't be empty");
			}
			
			return key;
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

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ArgumentMissingConfigurationException(
						INCOMPLETE_KEY_TRANSLATION, "Key value can't be empty");
			}
			
			return key;
		}
	};
	
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
	
	public static ArgumentType byName(String name) {
		
		for (ArgumentType argType : ArgumentType.values()) {
		
			if (argType.getName().equals(name)) {
				
				return argType;
			}
		}
		
		throw new UnknownArgumentRuntimeException(name);
	}
	
	private static void checkIsValidArray(
			Integer argumentPosition, String [] arguments) throws ArgumentMissingConfigurationException {
		
		if (argumentPosition < arguments.length) {
			
			throw new ArgumentMissingConfigurationException(INCOMPLETE_KEY_TRANSLATION, 
					String.format("Position '%s' is out of index bound (arguments length is '%s')", 
							argumentPosition, arguments.length));
		} if (argumentPosition + 1 >= arguments.length) { 
			
			throw new ArgumentMissingConfigurationException(
					INCOMPLETE_KEY_TRANSLATION, "Missing key value");
		}
	}
}
