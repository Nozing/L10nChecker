package gl.nozing.l10nChecker.resourceFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.exception.ConfigurationException;
import gl.nozing.l10nChecker.locale.L10nDefaultLocale;
import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.resourceFile.exception.UnparsableFileNameException;

/**
 * @author nozing
 *
 */
public class ResourceFile {

	private static Logger log = LoggerFactory.getLogger(ResourceFile.class);

	private File resourceFile;

	private L10nLocale locale;
	private Properties properties;

	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 * 
	 * @param resourceFile <code>{@link File}</code> with the file we want to load
	 *                     as a resource
	 * @throws ConfigurationException Exception throw when we are not able to
	 *                                configure the resource
	 */
	public ResourceFile(File resourceFile) throws ConfigurationException {

		this.resourceFile = resourceFile;

		this.configure();
	}

	/**
	 * @return Returns an <code>{@link String}</code> with the absolute file path of
	 *         the resource processed
	 */
	public String getFilePath() {

		return this.resourceFile.getAbsolutePath();
	}

	/**
	 * @return Returns the <code>{@link L10nLocale}</code> of the resource
	 */
	public L10nLocale getLocale() {

		return this.locale;
	}

	/**
	 * @return Returns the <code>{@link Properties}</code> that contains the pairs
	 *         key/value
	 */
	public Properties getProperties() {

		return this.properties;
	}

	/**
	 * <p>
	 * Creates the locale related to the file name passed as a parameter.
	 * </p>
	 * <p>
	 * <code>fileName</code> has to contain a dot (<code>.</code>) and some
	 * underscore (<code>_</code>) character. If we are not able to parse it it will
	 * throw an <code>Exception</code>
	 * </p>
	 * <p>
	 * Some examples of valid file names:
	 * <ul>
	 * <li><code>ApplicationResources.properties</code>: it will return <code>
	 * {@link L10nLocale}</code></li>
	 * <li><code>ApplicationResources_es.properties</code>: it will return a
	 * <code>es</code> {@link L10nLocale}</li>
	 * <li><code>ApplicationResoruces_es_ES.properties</code>: it will return a
	 * <code>es_ES</code> {@link L10nLocale}</li>
	 * </ul>
	 * </p>
	 * 
	 * @param fileName <code>{@link String}</code> with the file name
	 * @return Returns a {@link L10nLocale} related to the file or a
	 *         <code>null</code> if there is not locale
	 * @throws UnparsableFileNameException 	It is thrown when it can't parse the file
	 *                                      name
	 */
	public static L10nLocale extractLocaleFromFileName(String fileName) throws UnparsableFileNameException {

		if (fileName != null) {

			String[] tmp = fileName.split("\\.");
			String fileNameCleaned = null;
			if (tmp.length == 1) {
				fileNameCleaned = tmp[0];
			} else if (tmp.length == 2) {
				fileNameCleaned = tmp[0];
			} else {

				throw new UnparsableFileNameException(fileName);
			}

			Locale locale = null;
			tmp = fileNameCleaned.split("_");
			if (tmp.length == 2) {

				locale = new Locale(tmp[1]);
			} else if (tmp.length == 3) {

				locale = new Locale(tmp[1], tmp[2]);
			}

			if (locale == null) {

				return new L10nDefaultLocale();
			} else {

				return new L10nNormalLocale(locale);
			}
		}

		return null;
	}

	/**
	 * <p>
	 * Configures the processor. It identifies the locale of the file and loads it
	 * as a {@link Properties} object. It sets a flag as <code>
	 * configured</code> to avoid load more than one time the configuration
	 * </p>
	 * 
	 * @throws ConfigurationException Exception thrown when it can't be parsed the
	 *                                filename or when there is a problem loading
	 *                                the resource
	 */
	private void configure() throws ConfigurationException {

		try {

			this.locale = ResourceFile.extractLocaleFromFileName(this.getFilePath());

		} catch (UnparsableFileNameException ufne) {

			throw ConfigurationException.createException(ufne.getMessage());
		}

		this.properties = new Properties();
		try {

			this.properties.load(new FileInputStream(this.resourceFile));
		} catch (IOException exc) {

			String errorMessage = String.format("Error loading resource file '%s'",
					this.resourceFile.getAbsolutePath());
			log.error(errorMessage, exc);
			throw ConfigurationException.createException(errorMessage, exc);
		}
	}
}