package gl.nozing.l10nChecker.resourceFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gl.nozing.l10nChecker.exception.ConfigurationException;
import gl.nozing.l10nChecker.locale.L10nDefaultLocale;
import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.resourceFile.exception.UnparsableFileNameException;

public class ResourceFilesTest {

    private String pathFile_default = "gl/nozing/l10nChecker/ApplicationResources.properties";
    private String pathFile_es = "gl/nozing/l10nChecker/ApplicationResources_es.properties";
    private String pathFile_es_ES = "gl/nozing/l10nChecker/ApplicationResources_es_ES.properties";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test
    public void getResourcesFilesTest() {

        ResourceFile rfLocator = null;
		try {
			rfLocator = new ResourceFile(this.getFile(this.pathFile_default));
		} catch (ConfigurationException e) {
			
			Assert.fail(e.getMessage());
		}

        String filePath = rfLocator.getFilePath();
        Assert.assertNotNull(filePath);
    }

    @Test
    public void getLocaleFromResourceTest() throws Exception {

        ResourceFile rfLocator = new ResourceFile(this.getFile(this.pathFile_es));

        L10nNormalLocale expected_es_locale = 
        		new L10nNormalLocale(new Locale("es"));
        L10nLocale locale = rfLocator.getLocale();

        Assert.assertNotNull(locale);
        Assert.assertEquals(expected_es_locale, locale);

        rfLocator = new ResourceFile(this.getFile(this.pathFile_es_ES));

        L10nNormalLocale expected_es_ES_locale = 
        		new L10nNormalLocale(new Locale("es", "ES"));
        locale = rfLocator.getLocale();

        Assert.assertNotNull(locale);
        Assert.assertEquals(expected_es_ES_locale, locale);

        rfLocator = new ResourceFile(this.getFile(this.pathFile_default));

        locale = rfLocator.getLocale();

        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nDefaultLocale);
    }

    @Test
    public void getPropertiesFromResourceTest() throws IOException, Exception {

        ResourceFile rfLocator = new ResourceFile(
            this.getFile(this.pathFile_es));

        Properties properties = rfLocator.getProperties();
        Assert.assertNotNull(properties);
    }
    
    @Test
    public void extractLocaleFromFileNameTest() throws Exception {

    	Assert.assertNull(ResourceFile.extractLocaleFromFileName(null));
    	
    	L10nLocale locale = ResourceFile.extractLocaleFromFileName("filename");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nDefaultLocale);
        
        locale = ResourceFile.extractLocaleFromFileName("filename.properties");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nDefaultLocale);
        
        locale = ResourceFile.extractLocaleFromFileName("filename_es");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nNormalLocale);
        
        L10nNormalLocale normalLocale = (L10nNormalLocale) locale;
        Assert.assertEquals(new Locale("es"), normalLocale.getLocale());
        
        locale = ResourceFile.extractLocaleFromFileName("filename_es.properties");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nNormalLocale);
        
        normalLocale = (L10nNormalLocale) locale;
        Assert.assertEquals(new Locale("es"), normalLocale.getLocale());
        
        locale = ResourceFile.extractLocaleFromFileName("filename_es_ES");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nNormalLocale);
        
        normalLocale = (L10nNormalLocale) locale;
        Assert.assertEquals(new Locale("es", "ES"), normalLocale.getLocale());
        
        locale = ResourceFile.extractLocaleFromFileName("filename_es_ES.properties");
        Assert.assertNotNull(locale);
        Assert.assertTrue(locale instanceof L10nNormalLocale);
        
        normalLocale = (L10nNormalLocale) locale;
        Assert.assertEquals(new Locale("es", "ES"), normalLocale.getLocale());
        
        this.exceptionRule.expect(UnparsableFileNameException.class);
        ResourceFile.extractLocaleFromFileName("filename.es_ES.properties");
    }
    
    /**
     * <p>Load the resource to process</p>
     * 
     * @param pathFile <code>{@link String}</code> with the path ot the 
     * resource to load
     * @return Returns a <code>{@link File}</code> loaded from the path 
     * indicated
     */
    private File getFile(String pathFile) {
        
        File resourceFile = new File(
            this.getClass().getClassLoader().getResource(pathFile).getFile());

        return resourceFile;
    }
}