/**
 * 
 */
package gl.nozing.l10nChecker.localizationManager.obj;

import java.util.LinkedList;
import java.util.List;

import gl.nozing.l10nChecker.locale.L10nLocale;

/**
 * <p>
 * Class to store the locales missed for a given word
 * </p>
 * 
 * @author nozing
 *
 */
public class MissedWordLocales {

	private final String word;
	private final List<L10nLocale> missingLocales;
	
	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 * 
	 * @param word         <code>{@link String}</code> with the word we want to
	 *                     store
	 * @param missedLocale <code>{@link L10nLocale}</code> with the locale that is
	 *                     missed for this word
	 */
	public MissedWordLocales(String word, L10nLocale missedLocale) {
		super();
		this.word = word;
		
		this.missingLocales = new LinkedList<L10nLocale>();
		this.missingLocales.add(missedLocale);
	}
	
	/**
	 * @return Returns an <code>{@link String</code> with the word stored
	 */
	public final String getWord() {
		return word;
	}
	
	/**
	 * <p>
	 * Adds a locale to the missed list of locales of this word
	 * </p>
	 * 
	 * @param locale <code>{@link L10nLocale}</code> with the locale to add
	 */
	public void addMissedLocale(L10nLocale locale) {
		
		this.missingLocales.add(locale);
	}
	
	/**
	 * @return Returns a list of <code>{@link L10nLocale}</code>
	 */
	public final List<L10nLocale> getMissedLocales() {
		
		return this.missingLocales;
	}
}
