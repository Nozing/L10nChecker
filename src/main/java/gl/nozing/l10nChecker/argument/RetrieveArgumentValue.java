package gl.nozing.l10nChecker.argument;

import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;

/**
 * Defines the interface to the operation for retrieving the value of an
 * argument positioned in a given place and in a given arguments array
 * 
 * @author nozing
 *
 */
public interface RetrieveArgumentValue {

	/**
	 * Returns the value of an argument in a given position
	 * 
	 * @param argumentPosition Integer with the position of the argument key
	 * @param arguments Array with the argument key and values
	 * @return Returns an String with the value of the argument
	 * @throws ArgumentMissingConfigurationException Exception thrown when the value can't be retrieved for any reason
	 */
	String retrieveValue(Integer argumentPosition, String [] arguments) throws ArgumentMissingConfigurationException;
}
