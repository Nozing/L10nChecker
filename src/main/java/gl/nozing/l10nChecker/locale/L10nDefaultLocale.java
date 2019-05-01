/**
 * 
 */
package gl.nozing.l10nChecker.locale;

/**
 * <p>
 * Class to represent the default locale
 * </p>
 * 
 * @author nozing
 *
 */
public class L10nDefaultLocale implements L10nLocale {

	@Override
	public String getName() {
		
		return "Default";
	}

	@Override
	public Boolean isDefault() {
		
		return true;
	}
	
	@Override
	public String toString() {
		
		return this.getName();
	}
}
