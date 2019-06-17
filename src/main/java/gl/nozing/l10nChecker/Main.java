package gl.nozing.l10nChecker;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.argument.ArgumentType;
import gl.nozing.l10nChecker.argument.exception.ArgumentMissingConfigurationException;
import gl.nozing.l10nChecker.argument.exception.UnknownArgumentRuntimeException;
import gl.nozing.l10nChecker.exception.ConfigurationException;
import gl.nozing.l10nChecker.locale.L10nLocale;
import gl.nozing.l10nChecker.locale.L10nNormalLocale;
import gl.nozing.l10nChecker.localizationManager.LocalizationManager;
import gl.nozing.l10nChecker.localizationManager.MissedWordLocalesManager;
import gl.nozing.l10nChecker.localizationManager.obj.LanguageDO;
import gl.nozing.l10nChecker.localizationManager.obj.MissedWordLocales;
import gl.nozing.l10nChecker.resourceFile.ResourceFile;

/**
 * <p>
 * Main class to execute the application
 * </p>
 * <p>
 * The application accepts the following parameters:
 * <ul>
 * <li><code>-fp filePattern</code>: contains the pattern of the name of the
 * files that will be searched to extract the locales. If it is not set
 * <code>ApplicationResources</code> will be used by default</li>
 * <li><code>-wd workingDirectory</code>: contains the path to the <em>working
 * directory</em> where the application will search the resources</li>
 * <li><code>-it</code>: tells the application to find <strong>all</strong> the
 * incomplete translations</li>
 * <li><code>-itk key</code>: tells the application to find <strong>all</strong>
 * the incomplete translations of a given key <code>key</code></li>
 * <li><code>-el locale</code>: ask for all the translations for a given locale
 * </li>
 * </ul>
 * For example:
 * <ul>
 * <li>Find all the incomplete translations inside a given directory: <br/><code>
 * java gl.nozing.l10nChecker.Main -wd /home/user/myProject -it</code>
 * </li>
 * <li>Find the incomplete translations of a given key inside a given directory in files with a given pattern: <br/><code>
 * java gl.nozing.l10nChecker.Main -wd /home/user/myProject -fp AppResources -itk order.form.label</code>
 * </li>
 * </ul>
 * </p>
 * 
 * @author nozing
 *
 */
public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static final String DEFAULT_FILE_NAME_PATTERN = "ApplicationResources";
	
	private File initialFolder;

	private List<ResourceFile> resourceFiles;

	private Map<ArgumentType, String> arguments = null;

	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 */
	public Main() {

		this.resourceFiles = new LinkedList<ResourceFile>();

		// Default values for command line arguments
		this.arguments = new HashMap<ArgumentType, String>();
		// fp (file pattern) : name of the properties file we will try to find
		this.arguments.put(ArgumentType.FILE_PATTERN, DEFAULT_FILE_NAME_PATTERN);
		// wd (working directory) : root directory of the scan
		this.arguments.put(ArgumentType.WORKING_DIRECTORY, System.getProperty("user.dir"));
	}

	/**
	 * <p>
	 * Adds an argument to the application
	 * </p>
	 * 
	 * @param arg   <code>{@link ArgumentType}</code> with the arg to add
	 * @param value <code>{@link String}</code> with the value of the arg to add
	 */
	public void addArgument(ArgumentType arg, String value) {

		arguments.put(arg, value);
	}

	/**
	 * <p>
	 * Returns the value of a given argument
	 * </p>
	 * 
	 * @param arg Argument to be retrieved
	 * @return Returns the value of the argument given or <code>NULL</code> if there
	 *         isn't a value
	 */
	public String getArgument(ArgumentType arg) {
		
		return arguments.get(arg);
	}
	
	/**
	 * @return Returns a list of <code>{@link ResourceFile}</code> of resources
	 */
	public final List<ResourceFile> getResourceFiles() {
		return resourceFiles;
	}
	
	/**
	 * <p>
	 * Main method to start the application
	 * </p>
	 * 
	 * @param args Array of <code>{@link String}</code> with the arguments to
	 *             configure the application
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Main main = instantiateMain(args);

		main.process();
	}

	/**
	 * <p>
	 * Instantiates <code>Main</code> class parsing the arguments given by the user
	 * </p>
	 * 
	 * @param args Array of <code>{@link String}</code> with the arguments to setup
	 *             the application
	 * @throws ArgumentMissingConfigurationException 
	 */
	public static Main instantiateMain(String[] args) throws ArgumentMissingConfigurationException {
		
		Main main = new Main();
		for (int i = 0; i < args.length; i++) {

			ArgumentType argType = null;
			
			try {
				argType = ArgumentType.byName(args[i]);
				
				main.addArgument(argType, argType.retrieveValue(i, args));
			} catch (UnknownArgumentRuntimeException uare) {
				
				/* We are traversing the arguments array so if we find something
				 * we don't recognize as an configuration, we just ignore it */
			}			
		}
		
		return main;
	}

	/**
	 * <p>
	 * Method that, after Main has been configured, process the request of the user
	 * </p>
	 * 
	 * @throws Exception
	 */
	private void process() throws Exception {

		log.info(String.format("Using working directory '%s''", this.arguments.get(ArgumentType.WORKING_DIRECTORY)));
		loadResources();

		LocalizationManager lm = configureLocalizationManager();

		// Find incomplete translations or incomplete translations for a key
		if (this.arguments.containsKey(ArgumentType.INCOMPLETE_TRANSLATION)) {
			
			findAllIncompleteTranslations(lm);
			
		} else if (this.arguments.containsKey(ArgumentType.INCOMPLETE_KEY_TRANSLATION)) {

			findIncompleteTranslationsOfKey(lm, this.arguments.get(ArgumentType.INCOMPLETE_KEY_TRANSLATION));
		} else if (arguments.containsKey(ArgumentType.EXTRACT_LOCALE)) {
			
			extractLocale(lm, this.arguments.get(ArgumentType.INCOMPLETE_KEY_TRANSLATION));
		}
	}

	/**
	 * @param lm <code>{@link LocalizationManager}</code> with the localization
	 *           information to be processed
	 * @param localeName <code>@link String</code> with the name of the locale
	 */
	public void extractLocale(LocalizationManager lm, String localeName) {
		
		L10nLocale locale = new L10nNormalLocale(new Locale(localeName));
	
		LanguageDO lang = lm.retrieveValuesForLocale(locale);
		
		if (lang.getKeys().isEmpty()) {
			
			System.out.println(String.format("There aren't resulto for locale '%'", localeName));
		}
		
		for (String key : lang.getKeys()) {
			
			System.out.println(String.format("%s = %s", key, lang.getValue(key)));
		}
	}

	/**
	 * <p>
	 * Method that finds the incomplete translations of a given key inside the
	 * resources we have found
	 * </p>
	 * 
	 * @param lm <code>{@link LocalizationManager}</code> with the localization
	 *           information to be processed
	 */
	public void findIncompleteTranslationsOfKey(LocalizationManager lm, String key) {
		
		MissedWordLocalesManager mwlm = lm.configureMissedWorlLocalesManager();
		
		MissedWordLocales missedLocales = mwlm.getMissedLocalesOfWord(key);
		if (missedLocales != null) {
			System.out.println(missedLocales.getWord());
			for (L10nLocale missedLocale : missedLocales.getMissedLocales()) {
				System.out.println("\t" + missedLocale.getName());
			}
		} else {

			System.out.println(String.format("No missed locales for '%s' key", key));
		}
	}

	/**
	 * <p>
	 * Method that finds the incomplete translations of all keys. It means that if
	 * we manage five different languages and one key has only four translations
	 * this method will show us those two languages missed 
	 * </p>
	 * 
	 * @param lm <code>{@link LocalizationManager}</code> with the localization
	 *           information to be processed
	 */
	public void findAllIncompleteTranslations(LocalizationManager lm) {
		MissedWordLocalesManager mwlm = lm.configureMissedWorlLocalesManager();
		List<MissedWordLocales> missedLocales = mwlm.getMissedLocales();
		for (MissedWordLocales incompleteWord : missedLocales) {

			System.out.println(incompleteWord.getWord());
			for (L10nLocale missedLocale : incompleteWord.getMissedLocales()) {
				System.out.println("\t" + missedLocale.getName());
			}
		}
	}

	/**
	 * @throws ConfigurationException
	 */
	private void loadResources() throws ConfigurationException {
		this.initialFolder = new File(this.arguments.get(ArgumentType.WORKING_DIRECTORY));

		if (!this.initialFolder.isDirectory()) {

			throw ConfigurationException.createException(
					String.format("Initial folder '%s' is not a directory", this.initialFolder.getAbsolutePath()));
		} else {

			this.processDirectory(this.initialFolder);
		}
	}

	/**
	 * <p>
	 * This method configures the localization manager. It process the resources and
	 * creates the languages with the pairs key/value (dictionary)
	 * </p>
	 * 
	 * @return Returns a <code>{@link LocalizationManager}</code> with the languages 
	 * and the dictionaries we have found
	 */
	private final LocalizationManager configureLocalizationManager() {
		
		long t0 = System.currentTimeMillis();
		log.trace("Initializing LocalizationManager");
		LocalizationManager lm = new LocalizationManager();
		for (ResourceFile resource : this.resourceFiles) {

			log.debug("Adding '{}' resource to LocalizationManager", resource.getFilePath());
			lm.addLanguage(new LanguageDO(resource.getLocale(), resource.getProperties()));
		}
		log.trace("Initialization done in {} ms", (System.currentTimeMillis() - t0));
		
		return lm;
	}

	/**
	 * <p>
	 * Process a given directory searching for files that are suitable to add as a
	 * resource. 
	 * </p>
	 * 
	 * @param directory <code>{@link File}</code> to be checked
	 * @throws ConfigurationException 
	 */
	public final List<ResourceFile> processDirectory(File directory) throws ConfigurationException {

		if (directory == null) {
			
			return this.resourceFiles;
		} else if (!directory.isDirectory()) {
			
			throw ConfigurationException.createException(
					String.format("Received tile '%s' instead a directory", directory.getName()));
		}
		
		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {

				this.processDirectory(file);
			} else {

				log.trace("File '{}' found", file.getAbsolutePath());
				if (isProcessableFile(file)) {

					log.info("File '{}' added", file.getName());

					try {

						this.resourceFiles.add(new ResourceFile(file));

					} catch (ConfigurationException ce) {

						log.error(String.format("Can't load file '%s': %s", file.getAbsolutePath(), ce.getMessage()),
								ce);
					}
				}
			}
		}
		
		return this.resourceFiles;
	}

	/**
	 * <p>
	 * Returns a <code>Boolean</code> indicating if a file is suitable to be
	 * processed. The criteria to decide this is:
	 * <ol>
	 * <li>The file is a normal file</li>
	 * <li>The file is not hidden</li>
	 * <li>The file is readable</li>
	 * <li>File's name contains the <em>file pattern</em> setted by the user</li>
	 * <li>File extension is <code>.properties</code></li>
	 * </ol>
	 * </p>
	 * 
	 * @param file <code>{@link File}</code> to be checked
	 * @return Returns <code>TRUE</code> if the file complies all the requirements
	 *         and <code>FALSE</code> in other case
	 */
	public boolean isProcessableFile(File file) {
		return file.isFile() && !file.isHidden() && file.canRead()
				&& file.getName().contains(this.arguments.get(ArgumentType.FILE_PATTERN))
				&& file.getName().contains(".properties");
	}
}