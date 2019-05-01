package gl.nozing.l10nChecker.localizationManager.obj;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;

/**
 * <p>This class contains information about a language. A language is set of
 * pairs key-value of a determinated locale (it can be null for the default 
 * locale</p>
 * 
 * @author nozing
 *
 */
public class LanguageDO {

    private final L10nLocale locale;
    private final Map<String, String> dictionary;
    
    /**
     * Default constructor
     */
    public LanguageDO() {    	
    	this.locale = null;
    	this.dictionary = new HashMap<String, String>();
    }
    
    /**
     * Creates a language with a <code>{@link L10nNormalLocale}</code>
     * 
     * @param locale <code>{@link L10nNormalLocale}</code> to set
     */
    public LanguageDO(L10nLocale locale) {
    	this.locale = locale; 
    	this.dictionary = new HashMap<String, String>();
    }


    /**
     * Creates a language with a <code>{@link L10nNormalLocale}</code>
     * 
     * @param locale <code>{@link L10nNormalLocale}</code> to set
     * @param dictionary <code>{@link Properties}</code> with the dictionary
     */
    public LanguageDO(L10nLocale locale, Properties dictionary) {
    	this.locale = locale; 
    	this.dictionary = new HashMap<String, String>();
    	
    	this.addDictionary(dictionary);
    }
    
    /**
     * @return Returns the <code>{@link L10nLocale}</code>
     */
    public final L10nLocale getLocale() {
        return locale;
    }

    /**
     * 
     * @return Returns a list of the keys contained in te dictionary
     */
    public final List<String> getKeys() {

        return new LinkedList<String>(this.dictionary.keySet());
    }

    /**
     * Returns the value in the dictionary of a given <code>key</code>. It can
     * be <code>null</code> in case there were not a coincidence
     * 
     * @param key <code>{@link String}</code> the key
     * @return Returns an <code>{@link String}</code> with the value of the key
     * in the dictionary or <code>null</code>
     */
    public final String getValue(String key) {

        return this.dictionary.get(key);
    }

    /**
     * Converts a <code>{@link Properties}</code> in a dictionary
     * 
     * @param dictionary <code>{@link Properties}</code> to convert
     */
	public void addDictionary(Properties dictionary) {

        if (dictionary != null) {

            for (Object key : dictionary.keySet()) {

                this.dictionary.put(key.toString(), dictionary.getProperty(key.toString()));
            }
        }
    }
    
	/**
	 * Adds a word to the dictionary
	 * 
	 * @param word <code>{@link String}</code> with the word to be added
	 * @param value <code>{@link String}</code> with the value
	 */
    public void addToDictionay(String word, String value) {

        this.dictionary.put(word, value);
    }
}