/**
 * 
 */
package gl.nozing.l10nChecker.argument;

import gl.nozing.l10nChecker.argument.exception.ParamMissingConfigurationException;
import gl.nozing.l10nChecker.argument.exception.UnknownArgumentRuntimeException;

/**
 * @author nozing
 *
 */
public enum ArgumentType implements RetrieveArgumentValue {

	FILE_PATTERN("-fp") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ParamMissingConfigurationException {

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ParamMissingConfigurationException(
						FILE_PATTERN, "Key value can't be empty");
			}
			
			return key;
		}
	},
	
	WORKING_DIRECTORY("-wd") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ParamMissingConfigurationException {

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ParamMissingConfigurationException(
						WORKING_DIRECTORY, "Key value can't be empty");
			}
			
			return key;
		}
	},
	
	INCOMPLETE_TRANSLATION("-it"),
	
	INCOMPLETE_KEY_TRANSLATION("-itk") {

		@Override
		public String retrieveValue(Integer argumentPosition, String[] arguments) throws ParamMissingConfigurationException {

			ArgumentType.checkIsValidArray(argumentPosition, arguments);			
			String key = arguments[argumentPosition + 1];
			
			if (key.isEmpty()) {
				
				throw new ParamMissingConfigurationException(
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
			throws ParamMissingConfigurationException {
		
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
			Integer argumentPosition, String [] arguments) throws ParamMissingConfigurationException {
		
		if (argumentPosition < arguments.length) {
			
			throw new ParamMissingConfigurationException(INCOMPLETE_KEY_TRANSLATION, 
					String.format("Position '%s' is out of index bound (arguments length is '%s')", 
							argumentPosition, arguments.length));
		} if (argumentPosition + 1 >= arguments.length) { 
			
			throw new ParamMissingConfigurationException(
					INCOMPLETE_KEY_TRANSLATION, "Missing key value");
		}
	}
}
