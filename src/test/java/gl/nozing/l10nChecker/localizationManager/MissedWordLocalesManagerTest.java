/**
 * 
 */
package gl.nozing.l10nChecker.localizationManager;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gl.nozing.l10nChecker.locale.L10nDefaultLocale;
import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;
import gl.nozing.l10nChecker.localizationManager.obj.MissedWordLocales;

/**
 * @author nozing
 *
 */
public class MissedWordLocalesManagerTest {

	private static final String MISSED_WORD_ES = "missedWord";

	private static final String KEY_GRETTING = "greeting";
	
	private L10nLocale locale_es_ES;
	private L10nLocale defaultLocale;
	private L10nLocale locale_en;
	
	private LanguageDO language_es_ES;
	private LanguageDO language_default;
	private LanguageDO language_en;

	@Before
	public void setUp() {
		
		defaultLocale = new L10nDefaultLocale();
		
		language_default = new LanguageDO(defaultLocale);
		language_default.addToDictionay(KEY_GRETTING, "hi");
		language_default.addToDictionay("goodbye", "bye");
		language_default.addToDictionay("order.label", "Order");
		language_default.addToDictionay(MISSED_WORD_ES, "Missed");
		
		locale_es_ES = new L10nNormalLocale(new Locale("es", "ES"));
		
		language_es_ES = new LanguageDO(locale_es_ES);
		language_es_ES.addToDictionay(KEY_GRETTING, "hola");
		language_es_ES.addToDictionay("goodbye", "adios");
		language_es_ES.addToDictionay("order.label", "Pedido");
		
		locale_en = new L10nNormalLocale(new Locale("en"));
		
		language_en = new LanguageDO(locale_en);
		language_en.addToDictionay(KEY_GRETTING, "hi");
		language_en.addToDictionay("goodbye", "bye");
		language_en.addToDictionay("order.label", "Order");
	}
	
	@Test
	public void initialStatusTest() {
		
		MissedWordLocalesManager mwlm = new MissedWordLocalesManager();
		
		// Test initial status of the manager 
		Set<L10nLocale> availableLocales = mwlm.getAvailableLocales();
		Assert.assertNotNull(availableLocales);
		Assert.assertTrue(availableLocales.isEmpty());
	}
	
	@Test
	public void addLanguageTest() {
		
		MissedWordLocalesManager mwlm = new MissedWordLocalesManager();
		
		Set<L10nLocale> availableLocales = mwlm.getAvailableLocales();
		Assert.assertNotNull(availableLocales);
		Assert.assertTrue(availableLocales.isEmpty());
				
		mwlm.addLanguage(this.language_es_ES);
		
		// Test manager status after adding a new language
		availableLocales = mwlm.getAvailableLocales();
		Assert.assertNotNull(availableLocales);
		Assert.assertFalse(availableLocales.isEmpty());
		Assert.assertTrue(availableLocales.size() == 1);
		Assert.assertTrue(availableLocales.contains(locale_es_ES));
		
		Set<L10nLocale> locales = mwlm.getLocalesFromWord(KEY_GRETTING);
		Assert.assertNotNull(locales);
		Assert.assertFalse(locales.isEmpty());
		Assert.assertTrue(locales.size() == 1);
		Assert.assertTrue(locales.contains(locale_es_ES));
	}
	
	@Test
	public void getLocalesFromWordTest() {
		
		MissedWordLocalesManager mwlm = new MissedWordLocalesManager();
		
		Set<L10nLocale> locales = mwlm.getLocalesFromWord(KEY_GRETTING);
		Assert.assertNotNull(locales);
		Assert.assertTrue(locales.isEmpty());
		
		mwlm.addLanguage(this.language_es_ES);
		
		locales = mwlm.getLocalesFromWord(KEY_GRETTING);
		Assert.assertNotNull(locales);
		Assert.assertFalse(locales.isEmpty());
		Assert.assertTrue(locales.size() == 1);
		Assert.assertTrue(locales.contains(locale_es_ES));
	}
	
	@Test
	public void getMissedLocalesOfWordTest() {
		
		MissedWordLocalesManager mwlm = new MissedWordLocalesManager();
		
		MissedWordLocales locales = mwlm.getMissedLocalesOfWord(MISSED_WORD_ES);
		Assert.assertNull(locales);
		
		mwlm.addLanguage(this.language_es_ES);
		mwlm.addLanguage(this.language_default);
		mwlm.addLanguage(this.language_en);
		
		locales = mwlm.getMissedLocalesOfWord(MISSED_WORD_ES);
		Assert.assertNotNull(locales);
		Assert.assertFalse(locales.getMissedLocales().isEmpty());
		Assert.assertTrue(locales.getMissedLocales().contains(locale_es_ES));
		Assert.assertTrue(locales.getMissedLocales().contains(locale_en));
	}
}
