package gl.nozing.l10nChecker.argument.exception;

import gl.nozing.l10nChecker.argument.ArgumentType;

public class ParamMissingConfigurationException extends Exception {

	private static final long serialVersionUID = -8313281909880337689L;

	private ArgumentType argument;
	private String message;
	
	public ParamMissingConfigurationException(ArgumentType argument, String message) {
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
