/**
 * 
 */
package gl.nozing.l10nChecker.resourceFile.exception;

/**
 * <p>
 * Exception throw when we are not able to parse the name of a file
 * </p>
 * 
 * @author nozing
 *
 */
public class UnparsableFileNameException extends Exception {

	private static final long serialVersionUID = -441948009038554756L;

	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 * 
	 * @param fileName <code>{@link String}</code> with the name of the file that we
	 *                 are not able to parse
	 */
	public UnparsableFileNameException(String fileName) {

		super(String.format("File name '%s' unparseable", fileName));
		
	}
}
