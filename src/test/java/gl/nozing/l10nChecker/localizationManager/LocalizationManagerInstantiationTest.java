package gl.nozing.l10nChecker.localizationManager;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;
import gl.nozing.l10nChecker.localizationManager.obj.MissedWordLocales;

public class LocalizationManagerInstantiationTest {

	private static final String KEY1 = "key1";
	private static final String KEY3 = "key3";
	private static final String KEY5 = "key5";
	private static final String KEY6 = "key6";    

	private L10nLocale locale_unused;
	private Properties dictionary_default;
    private L10nLocale locale_es;
    private Properties dictionary_es;
    private L10nLocale locale_en;
    private Properties dictionary_en;

    @Before
    public void setUp() {

        locale_unused = new L10nNormalLocale(new Locale("he"));

        dictionary_default = new Properties();
        dictionary_default.put(KEY1, "key1_es");
        dictionary_default.put("key2", "key2_es");
        dictionary_default.put(KEY3, "key3_es");
        dictionary_default.put("key4", "key4_es");
        
        locale_es = new L10nNormalLocale(new Locale("es"));
        dictionary_es = new Properties();
        dictionary_es.put(KEY1, "key1_es");
        dictionary_es.put("key2", "key2_es");
        dictionary_es.put(KEY3, "key3_es");
        dictionary_es.put("key4", "key4_es");

        locale_en = new L10nNormalLocale(new Locale("en"));
        dictionary_en = new Properties();
        dictionary_en.put(KEY1, "key1_en");
        dictionary_en.put("key2", "key2_en");
        dictionary_en.put("key4", "key4_en");
        dictionary_en.put(KEY5, "key5_en");
        dictionary_en.put(KEY6, "key6_en");
    }

    @Test
    public void defaultDictionaryTest() {
    	
    	LocalizationManager lm = new LocalizationManager();
    	
    	Assert.assertNull(lm.getDefaultLanguage());
    	lm.setDefaultLanguage(new LanguageDO());
    	
    	Assert.assertNotNull(lm.getDefaultLanguage());
    }
    
    @Test
    public void getUnusedLanguageTest() {
        
        LocalizationManager lm = new LocalizationManager();
        
        LanguageDO localizationNull = lm.getLanguage(this.locale_unused);
        Assert.assertNull(localizationNull);
    }

    @Test
    public void instantiationTest() {

        LocalizationManager lm = new LocalizationManager();

        Assert.assertNotNull("'locales' can't be null", lm.getLocales());
        Assert.assertTrue("'locales' has to be empty", lm.getLocales().isEmpty());

        Assert.assertNotNull("'locales' can't be null", lm.getLocalesOfWord(KEY1));
        Assert.assertTrue("'locales' has to be empty", lm.getLocalesOfWord(KEY1).isEmpty());
    }

    @Test
    public void addOneLanguageTest() {
        
        LocalizationManager lm = new LocalizationManager();
        lm.addLanguage(this.locale_es, this.dictionary_es);

        List<L10nLocale> localesAvailable = lm.getLocales();
        Assert.assertNotNull(localesAvailable);
        Assert.assertFalse(localesAvailable.isEmpty());
        Assert.assertTrue(localesAvailable.size() == 1);
        Assert.assertEquals(locale_es, localesAvailable.get(0));
        
        List<L10nLocale> localesAvailableForKey = lm.getLocalesOfWord(KEY1);
        Assert.assertNotNull(localesAvailableForKey);
        Assert.assertFalse(localesAvailableForKey.isEmpty());
        Assert.assertTrue(localesAvailableForKey.size() == 1);
        Assert.assertEquals(locale_es, localesAvailableForKey.get(0));

        LanguageDO localizationNull = lm.getLanguage(locale_unused);
        Assert.assertNull(localizationNull);

        LanguageDO localization = lm.getLanguage(locale_es);
        Assert.assertNotNull(localization);
        Assert.assertNotNull(localization.getLocale());
        Assert.assertEquals(locale_es, localization.getLocale());
    }

    @Test
    public void addMoreThanOneLanguageTest() {
        
        LocalizationManager lm = new LocalizationManager();
        lm.addLanguage(this.locale_es, this.dictionary_es);
        lm.addLanguage(this.locale_en, this.dictionary_en);

        List<L10nLocale> localesAvailable = lm.getLocales();
        Assert.assertNotNull(localesAvailable);
        Assert.assertFalse(localesAvailable.isEmpty());
        Assert.assertTrue(localesAvailable.size() == 2);
        Assert.assertTrue(localesAvailable.contains(this.locale_es));
        Assert.assertTrue(localesAvailable.contains(this.locale_en));
        
        List<L10nLocale> localesAvailableForKey = lm.getLocalesOfWord(KEY1);
        Assert.assertNotNull(localesAvailableForKey);
        Assert.assertFalse(localesAvailableForKey.isEmpty());
        Assert.assertTrue(localesAvailableForKey.size() == 2);
        Assert.assertTrue(localesAvailableForKey.contains(this.locale_es));
        Assert.assertTrue(localesAvailableForKey.contains(this.locale_en));

        LanguageDO localization = lm.getLanguage(locale_es);
        Assert.assertNotNull(localization);
        Assert.assertNotNull(localization.getLocale());
        Assert.assertEquals(locale_es, localization.getLocale());
    }
    
    @Test
    public void findIncompleteTest() {
    	
    	// key - uncomplete locales
    	LocalizationManager lm = new LocalizationManager();
        lm.addLanguage(this.locale_es, this.dictionary_es);
        lm.addLanguage(this.locale_en, this.dictionary_en);
        
        MissedWordLocalesManager missedLocalesMgr = 
        		lm.findIncompleteKeys();
        
        Assert.assertNotNull(missedLocalesMgr);
        
        Assert.assertNull(missedLocalesMgr.getMissedLocalesOfWord(KEY1));
        
        MissedWordLocales locales = missedLocalesMgr.getMissedLocalesOfWord(KEY3);
        Assert.assertNotNull(locales);
        Assert.assertFalse(locales.getMissedLocales().isEmpty());
        Assert.assertTrue(locales.getMissedLocales().size() == 1);
        Assert.assertTrue(locales.getMissedLocales().contains(this.locale_en));
        
        locales = missedLocalesMgr.getMissedLocalesOfWord(KEY5);
        Assert.assertNotNull(locales);
        Assert.assertFalse(locales.getMissedLocales().isEmpty());
        Assert.assertTrue(locales.getMissedLocales().size() == 1);
        Assert.assertTrue(locales.getMissedLocales().contains(this.locale_es));
        
        locales = missedLocalesMgr.getMissedLocalesOfWord(KEY6);
        Assert.assertNotNull(locales);
        Assert.assertFalse(locales.getMissedLocales().isEmpty());
        Assert.assertTrue(locales.getMissedLocales().size() == 1);
        Assert.assertTrue(locales.getMissedLocales().contains(this.locale_es));
    }
}