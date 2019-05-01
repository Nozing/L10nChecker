/**
 * 
 */
package gl.nozing.l10nChecker.localizationManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;
import gl.nozing.l10nChecker.localizationManager.obj.MissedWordLocales;

/**
 * <p>
 * Manager that given a set of locales and their dictionarys it is able to find
 * which words are not missed for some locales
 * </p>
 * 
 * @author nozing
 *
 */
public class MissedWordLocalesManager {

	private final static Logger log = LoggerFactory.getLogger(MissedWordLocalesManager.class);
	
	private final Set<L10nLocale> availableLocales;
	private final Map<String, Set<L10nLocale>> translationsOfWords;
	
	/**
	 * Default constructor of the class
	 */
	public MissedWordLocalesManager() {
	
		this.availableLocales = new HashSet<L10nLocale>();
		this.translationsOfWords = new HashMap<String, Set<L10nLocale>>();
	}

	/**
	 * <p>
	 * Returns the missed locales for a given word
	 * </p>
	 * 
	 * @param word <code>{@link word}</code> with the word
	 * @return Returns an instance of <code>{@link MissedWordLocales}</code> that
	 *         contains the missed locales of the word
	 */
	public MissedWordLocales getMissedLocalesOfWord(String word) {

		Set<L10nLocale> locales = this.translationsOfWords.get(word);
		if (locales == null) {			
			
			log.debug("No locales for word '{}'", word);
			MissedWordLocales mwl = null;
			for (L10nLocale availableLocale : this.availableLocales) {
				
				log.trace("Adding '{}' locale has missed", availableLocale);
				if (mwl == null) {
					
					mwl = new MissedWordLocales(word, availableLocale);
				} else {
					
					mwl.addMissedLocale(availableLocale);
				}
			}
			
			return mwl; 
		} else {
			
			log.debug("{} locales for word '{}'", locales.size(), word);
			MissedWordLocales mwl = null;
			for (L10nLocale availableLocale : this.availableLocales) {
				
				if (!locales.contains(availableLocale)) {
					
					log.trace("Adding '{}' locale has missed", availableLocale);
					if (mwl == null) {
						
						mwl = new MissedWordLocales(word, availableLocale);
					} else {
						
						mwl.addMissedLocale(availableLocale);
					}
				}
			}
			
			return mwl;
		}		
	}

	/**
	 * @return Returns a set of <code>{@link L10nLocale}</code> with the available
	 *         locales of the manager
	 */
	public final Set<L10nLocale> getAvailableLocales() {

		return this.availableLocales;
	}

	/**
	 * <p>
	 * Adds a language to the manager. If the given language is <code>NULL</code> it
	 * will be ignored
	 * </p>
	 * 
	 * @param language <code>{@link LanguageDO}</code> with the language to be added
	 */
	public void addLanguage(LanguageDO language) {
		
		if (language != null) {
			
			log.debug("Adding '{}' locale", language.getLocale());
			this.availableLocales.add(language.getLocale());
			
			for (String word : language.getKeys()) {
				
				Set<L10nLocale> translations = this.translationsOfWords.get(word);
				if (translations == null) {
					
					log.trace("Creating translations...");
					translations = new HashSet<L10nLocale>();
				}
				
				translations.add(language.getLocale());
				
				log.trace("Adding locale '{}' to word '{}'", language.getLocale(), word);
				this.translationsOfWords.put(word, translations);
			}
		}
	}

	/**
	 * <p>
	 * Returns the locales for a given word
	 * </p>
	 * 
	 * @param word <code>{@link String}</code> with the word that we want to know
	 *             the locales
	 * @return Returns a set of <code>{@link L10nLocale}</code> that contains the
	 *         given word
	 */
	public final Set<L10nLocale> getLocalesFromWord(String word) {

		Set<L10nLocale> locales = this.translationsOfWords.get(word);
		if (locales == null) {
			
			return new HashSet<L10nLocale>();
		} else {
		
			return locales;
		}
	}

	/**
	 * @return Returns a list of <code>{@link MissedWordLocales}</code> with the
	 *         relations of words which doesn't have a locale
	 */
	public final List<MissedWordLocales> getMissedLocales() {
		
		List<MissedWordLocales> result = new LinkedList<MissedWordLocales>();
		for (Entry<String, Set<L10nLocale>> entry : this.translationsOfWords.entrySet()) {
			
			MissedWordLocales missedLocales = null;
			Set<L10nLocale> localesKey = entry.getValue();
			for (L10nLocale locale : this.availableLocales) {
				
				if (!localesKey.contains(locale)) {
					
					if (missedLocales == null) {
						
						missedLocales = new MissedWordLocales(entry.getKey(), locale);
					} else {
					
						missedLocales.addMissedLocale(locale);
					}
				}
			}
			
			if (missedLocales != null) {
				
				result.add(missedLocales);
			}
		}
		
		return result;
	}
}
