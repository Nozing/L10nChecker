/**
 * 
 */
package gl.nozing.l10nChecker.argument.exception;

import gl.nozing.l10nChecker.argument.ArgumentType;

/**
 * @author nozing
 *
 */
public class UnknownArgumentRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7432572189940823468L;

	private String unknownValue;
	
	public UnknownArgumentRuntimeException(String unknownValue) {

		this.unknownValue = unknownValue;
	}
	
	public String getUnknownValue() {
		return unknownValue;
	}
	
	@Override
	public String getMessage() {
		
		return String.format("Can't resolve '%s' to an %s", 
				unknownValue, ArgumentType.class.getName());
	}
}
