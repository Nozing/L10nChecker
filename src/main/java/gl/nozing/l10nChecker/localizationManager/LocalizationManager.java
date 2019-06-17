package gl.nozing.l10nChecker.localizationManager;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;

/**
 * <p>
 * Stores the languages indexed by its locale and the default locale
 * </p>
 */
public class LocalizationManager {
    
    private Map<L10nLocale, LanguageDO> languages = null;
    private LanguageDO defaultLanguage = null;

    /**
     * <p>Default constructor of the class</p>
     */
    public LocalizationManager() {
    	
        this.languages = new LinkedHashMap<L10nLocale, LanguageDO>();
    }

    /**
	 * @return Returns a <code>{@link LanguageDO}</code> with the default language.
	 *         It can be <code>NULL</code>
	 */
    public LanguageDO getDefaultLanguage() {
    	
        return defaultLanguage;
    }
    
    /**
	 * <p>
	 * Sets the default language
	 * </p>
	 * 
	 * @param <code>{@link LanguageDO}</code> with the default language to set
	 */
    public void setDefaultLanguage(LanguageDO defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    /**
	 * <p>
	 * Adds a <code>{@link LanguageDO}</code> to the manager. If the language
	 * contains a default locale it will be automatically setted as the default
	 * language of the manager.
	 * </p>
	 * 
	 * @param language <code>{@link LanguageDO}</code> with the language to add
	 */
    public void addLanguage(LanguageDO language) {

    	if (language.getLocale().isDefault()) {
    		
    		this.setDefaultLanguage(language);
    	}
    	
    	this.languages.put(language.getLocale(), language);
	}
    
	/**
	 * <p>
	 * Adds a set of pairs key/value of a determinated locale to the languages of
	 * the manager
	 * </p>
	 * 
	 * @param newLocale  <code>{@link L10nLocale}</code> with the locale of the set
	 * @param properties <code>{@link Properties}</code> with the key/values to add
	 */
	public void addLanguage(L10nLocale newLocale, Properties properties) {

        LanguageDO language = this.languages.get(newLocale);
        if (language == null) {

            language = new LanguageDO(newLocale);
            language.addDictionary(properties);

            this.languages.put(newLocale, language);
        }
	}

	/**
	 * @return Returns the list of <code>{@link L10nLocale}</code> managed by the
	 *         class
	 */
	public List<L10nLocale> getLocales() {
        
		return new LinkedList<L10nLocale>(this.languages.keySet());
    }
    
    /**
	 * <p>
	 * Returns a list of <code>{@link L10nLocale}</code> which have the word
	 * </p>
	 * 
	 * @param word <code>{@link String}</code> with the word that we want to know
	 *             the locales
	 * @return Returns a list <code>{@link L10nLocale}</code> with the locales that
	 *         contains the word
	 */
    public List<L10nLocale> getLocalesOfWord(String word) {

        List<L10nLocale> localesWithKey = new LinkedList<L10nLocale>();
        for (Entry<L10nLocale, LanguageDO> entry : this.languages.entrySet()) {

            if (entry.getValue() != null) {

                localesWithKey.add(entry.getKey());
            }
        }

		return localesWithKey;
	}

	/**
	 * <p>
	 * Returns the <code>{@link LanguageDO}</code> that contains the locale
	 * </p>
	 * 
	 * @param locale <code>{@link L10nLocale}</code> with the locale we want to know
	 * @return Return the <code>{@link LanguageDO}</code> that contains this locale
	 *         or <code>NULL</code> if there isn't a language with this locale
	 */
	public LanguageDO getLanguage(L10nLocale locale) {
		
		return this.languages.get(locale);
	}

	/**
	 * <p>
	 * Method that creates a <code>{@link MissedWordLocalesManager}</code> with the
	 * information of this manager
	 * </p>
	 * 
	 * @return Returns a <code>{@link MissedWordLocalesManager}</code> ready to work
	 */
	public MissedWordLocalesManager configureMissedWorlLocalesManager() {
		
		MissedWordLocalesManager mwlm = new MissedWordLocalesManager();
		
		for (Entry<L10nLocale, LanguageDO> entry : this.languages.entrySet()) {
			
			mwlm.addLanguage(entry.getValue());
		}
		
		return mwlm;
	}

	/**
	 * @param locale
	 * @return
	 */
	public LanguageDO retrieveValuesForLocale(L10nLocale locale) {
		
		LanguageDO langToReturn = new LanguageDO(locale);
		
		Set<String> allKeys = new HashSet<String>();
		for (Entry<L10nLocale, LanguageDO> tmp : languages.entrySet()) {
			
			allKeys.addAll(tmp.getValue().getKeys());
		}
		

		LanguageDO localeLang = languages.get(locale);
		for (String key : allKeys) {
			
			String value = localeLang.getValue(key);
			langToReturn.addToDictionay(key, (value != null ? value : ""));
		}
		
		return langToReturn;
	}
}
