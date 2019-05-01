package gl.nozing.l10nChecker.localizationManager.obj;

import java.util.Locale;
import java.util.Properties;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;

public class LanguageDOTest {

    private L10nNormalLocale locale_es;
    private Properties dictionary_es;

    @Before
    public void setUp() {

        locale_es = new L10nNormalLocale(new Locale("es"));
        dictionary_es = new Properties();
        dictionary_es.put("key1", "key1_es");
        dictionary_es.put("key2", "key2_es");
        dictionary_es.put("key3", "key3_es");
        dictionary_es.put("key4", "key4_es");
    }
    
    @Test
    public void addDictionaryTest() {

        LanguageDO lang = new LanguageDO(this.locale_es);
        
        Assert.assertNotNull(lang.getLocale());
        
        lang.addDictionary(this.dictionary_es);

        for (Entry<Object, Object> entry : this.dictionary_es.entrySet()) {

            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            String expectedValue = lang.getValue(key);
            Assert.assertNotNull(expectedValue);
            Assert.assertEquals(expectedValue, value);
        }
    }
    
    @Test
    public void addToDictionaryTest() {

        LanguageDO lang = new LanguageDO(this.locale_es);
        
        Assert.assertNotNull(lang.getLocale());
        
        lang.addDictionary(this.dictionary_es);
        String newKwey = "newKey";
        String newValue = "newKey_es";
        lang.addToDictionay(newKwey, newValue);

        for (Entry<Object, Object> entry : this.dictionary_es.entrySet()) {

            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            String expectedValue = lang.getValue(key);
            Assert.assertNotNull(expectedValue);
            Assert.assertEquals(expectedValue, value);
        }

        Assert.assertEquals(newValue, lang.getValue(newKwey));
    }
}