/**
 * 
 */
package gl.nozing.l10nChecker.exception;

/**
 * <p>
 * Exception thrown when there is some problem while configuring something
 * </p>
 * 
 * @author nozing
 *
 */
public class ConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7008379077645466227L;
	
	private Throwable encapsulatedException;
	
	/**
	 * <p>
	 * Default constructor with a message explaining what has been the configuration
	 * problem
	 * </p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the exception
	 */
	private ConfigurationException(String message) {
		super(message);
	}
	
	/**
	 * <p>
	 * Default constructor with a message explaining what has been the problem on
	 * the process and the exception captured
	 * </p>
	 * 
	 * @param message   <code>{@link String}</code> with the message of the
	 *                  exception
	 * @param exception <code>{@link Throwable}</code> with the exception that has
	 *                  been thrown during the process
	 */
	private ConfigurationException(String message, Throwable exception) {
		
		super(message);
		this.encapsulatedException = exception;
	}
	
	/**
	 * @return The encapsulated exception that throws this exception. It can be
	 *         <code>NULL</code>
	 */
	public Throwable getEncapsulatedException() {
		return encapsulatedException;
	}
	
	/**
	 * <p>Static method to create an instance of the exception</p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the
	 *                  exception
	 * @return Returns an instance of <code>{@link ConfigurationException}</code>
	 */
	public static ConfigurationException createException(String message) {
		
		return new ConfigurationException(message);
	}
	
	/**
	 * <p>Static method to create an instance of the exception</p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the
	 *                  exception
	 * @param exception <code>{@link Throwable}</code> with the exception that has
	 *                  been thrown during the process
	 * @return Returns an instance of <code>{@link ConfigurationException}</code>
	 */
	public static ConfigurationException createException(String message, Throwable exception) {
		
		if (exception instanceof ConfigurationException) {
			
			return (ConfigurationException) exception;
		} else {
			
			return new ConfigurationException(message, exception);
		}
	}
}
