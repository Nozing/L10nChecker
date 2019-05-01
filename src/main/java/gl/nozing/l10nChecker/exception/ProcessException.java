/**
 * 
 */
package gl.nozing.l10nChecker.exception;

/**
 * <p>
 * Exception thrown when there is some problem while something is being
 * processed
 * </p>
 * 
 * @author nozing
 *
 */
public class ProcessException extends Exception {

	private static final long serialVersionUID = 5003943830315720819L;

	private Throwable encapsulatedException;

	/**
	 * <p>
	 * Default constructor with a message explaining what has been the problem on
	 * the process
	 * </p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the exception
	 */
	private ProcessException(String message) {
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
	private ProcessException(String message, Throwable exception) {

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
	 * <p>
	 * Static method to create an instance of the exception
	 * </p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the exception
	 * @return Returns an instance of <code>{@link ProcessException}</code>
	 */
	public static ProcessException createException(String message) {

		return new ProcessException(message);
	}

	/**
	 * <p>Static method to create an instance of the exception</p>
	 * 
	 * @param message <code>{@link String}</code> with the message of the
	 *                  exception
	 * @param exception <code>{@link Throwable}</code> with the exception that has
	 *                  been thrown during the process
	 * @return Returns an instance of <code>{@link ProcessException}</code>
	 */
	public static ProcessException createException(String message, Throwable exception) {

		if (exception instanceof ProcessException) {

			return (ProcessException) exception;
		} else {

			return new ProcessException(message, exception);
		}
	}
}
