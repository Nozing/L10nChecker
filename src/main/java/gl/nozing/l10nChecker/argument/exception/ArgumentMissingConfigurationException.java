package gl.nozing.l10nChecker.argument.exception;

import gl.nozing.l10nChecker.argument.ArgumentType;

/**
 * Exception thrown when there is some problem trying to process some argument
 * 
 * @author nozing
 *
 */
public class ArgumentMissingConfigurationException extends Exception {

	private static final long serialVersionUID = -8313281909880337689L;

	private ArgumentType argument;
	private String message;
	
	/**
	 * @param argument Argument that throws the exception
	 * @param message Message explaining the problem
	 */
	public ArgumentMissingConfigurationException(ArgumentType argument, String message) {
		super();
		this.argument = argument;
		this.message = message;
	}
	
	public ArgumentType getArgument() {
		return argument;
	}
	
	@Override
	public String getMessage() {
		
		return message;
	}
}
