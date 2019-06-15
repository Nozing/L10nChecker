package gl.nozing.l10nChecker.argument;

import gl.nozing.l10nChecker.argument.exception.ParamMissingConfigurationException;

public interface RetrieveArgumentValue {

	String retrieveValue(Integer argumentPosition, String [] arguments) throws ParamMissingConfigurationException;
}
