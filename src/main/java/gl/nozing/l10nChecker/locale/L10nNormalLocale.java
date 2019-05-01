
/**
 * 
 */
package gl.nozing.l10nChecker.locale;

import java.util.Locale;

/**
 * <p>Class to encapsulate a locale</p>
 * 
 * @author nozing
 *
 */
public class L10nNormalLocale implements L10nLocale {

	private Locale locale;
		
	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 * 
	 * @param locale <code>{@link Locale}</code> with the locale to encapsulate
	 */
	public L10nNormalLocale(Locale locale) {
		super();
		this.locale = locale;
	}

	/**
	 * @return Returns the encapsulated <code>{@link Locale}</code>
	 */
	public Locale getLocale() {
		
		return locale;
	}

	@Override
	public String getName() {

		return this.locale.toString();
	}

	@Override
	public String toString() {
		
		return this.getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		L10nNormalLocale other = (L10nNormalLocale) obj;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}
}
