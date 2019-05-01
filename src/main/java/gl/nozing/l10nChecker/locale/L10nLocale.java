/**
 * 
 */
package gl.nozing.l10nChecker.locale;

/**
 * Interface to work in a more standard way with the locales
 * 
 * @author nozing
 *
 */
public interface L10nLocale {

	/**
	 * @return Returns the name of the locale
	 */
	String getName();
	
	/**
	 * @return Returns <code>TRUE</code> if it is the default locale of the system
	 *         or <code>FALSE</code> (by default) in other cases
	 */
	default Boolean isDefault() {
		return false;
	}
}
