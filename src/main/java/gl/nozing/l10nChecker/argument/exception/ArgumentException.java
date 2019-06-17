package gl.nozing.l10nChecker.argument.exception;

/**
 * Exception thrown when there is some problem trying to process some argument
 * 
 * @author nozing
 *
 */
public class ArgumentException extends Exception {

	private static final long serialVersionUID = -8313281909880337689L;

	private String message;
	
	/**
	 * @param message Message explaining the problem
	 */
	public ArgumentException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		
		return message;
	}
}
