package gl.nozing.l10nChecker;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.exception.ConfigurationException;
import gl.nozing.l10nChecker.locale.L10nLocale;
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
	private File initialFolder;

	private List<ResourceFile> resourceFiles;

	private Map<String, String> arguments = null;

	/**
	 * <p>
	 * Default constructor of the class
	 * </p>
	 */
	public Main() {

		this.resourceFiles = new LinkedList<ResourceFile>();

		// Default values for command line arguments
		this.arguments = new HashMap<String, String>();
		// fp (file pattern) : name of the properties file we will try to find
		this.arguments.put("fp", "ApplicationResources");
		// wd (working directory) : root directory of the scan
		this.arguments.put("wd", System.getProperty("user.dir"));
	}

	/**
	 * <p>
	 * Adds an argument to the application
	 * </p>
	 * 
	 * @param arg   <code>{@link String}</code> with the arg to add
	 * @param value <code>{@link String}</code> with the value of the arg to add
	 */
	public void addArgument(String arg, String value) {

		this.arguments.put(arg, value);
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

		Main main = new Main();

		for (int i = 0; i < args.length; i++) {

			switch (args[i]) {
			case "-wd":
				i++;
				main.addArgument("wd", args[i]);
				break;
			case "-fp":
				i++;
				main.addArgument("fp", args[i]);
				break;
			case "-it":
				main.addArgument("it", "");
				break;
			case "-itk":
				i++;
				main.addArgument("itk", args[i]);
				break;
			default:
				break;
			}
		}

		main.process();
	}

	/**
	 * <p>
	 * Method that, after Main has been configured, process the request of the user
	 * </p>
	 * 
	 * @throws Exception
	 */
	private void process() throws Exception {

		log.info(String.format("Using working directory '%s''", this.arguments.get("wd")));
		this.initialFolder = new File(this.arguments.get("wd"));

		if (!this.initialFolder.isDirectory()) {

			throw new Exception(
					String.format("Initial folder '%s' is not a directory", this.initialFolder.getAbsolutePath()));
		} else {

			this.processDirectory(this.initialFolder);
		}

		long t0 = System.currentTimeMillis();
		log.trace("Initializing LocalizationManager");
		LocalizationManager lm = new LocalizationManager();
		for (ResourceFile resource : this.resourceFiles) {

			log.debug("Adding '{}' resource to LocalizationManager", resource.getFilePath());
			lm.addLanguage(new LanguageDO(resource.getLocale(), resource.getProperties()));
		}
		log.trace("Initialization done in {} ms", (System.currentTimeMillis() - t0));

		// Find incomplete translations or incomplete translations for a key
		if (this.arguments.containsKey("it") || this.arguments.containsKey("itk")) {

			MissedWordLocalesManager mwlm = lm.findIncompleteKeys();

			String key = this.arguments.get("itk");
			if (key != null) {

				MissedWordLocales missedLocales = mwlm.getMissedLocalesOfWord(key);
				if (missedLocales != null) {
					System.out.println(missedLocales.getWord());
					for (L10nLocale missedLocale : missedLocales.getMissedLocales()) {
						System.out.println("\t" + missedLocale.getName());
					}
				} else {

					System.out.println(String.format("No missed locales for '%s' key", key));
				}

			} else {

				List<MissedWordLocales> missedLocales = mwlm.getMissedLocales();
				for (MissedWordLocales incompleteWord : missedLocales) {

					System.out.println(incompleteWord.getWord());
					for (L10nLocale missedLocale : incompleteWord.getMissedLocales()) {
						System.out.println("\t" + missedLocale.getName());
					}
				}
			}
		}
	}

	/**
	 * @param directory
	 */
	private void processDirectory(File directory) {

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {

				this.processDirectory(file);
			} else {

				log.trace("File '{}' found", file.getAbsolutePath());
				if (file.isFile() && !file.isHidden() && file.getName().contains(this.arguments.get("fp"))
						&& file.getName().contains(".properties")) {

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
	}
}