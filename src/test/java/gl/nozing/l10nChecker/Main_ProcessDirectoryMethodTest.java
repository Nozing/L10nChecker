package gl.nozing.l10nChecker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gl.nozing.l10nChecker.exception.ConfigurationException;
import gl.nozing.l10nChecker.resourceFile.ResourceFile;

public class Main_ProcessDirectoryMethodTest {

	private static final String NOT_INCLUDED_PROPERTIES_FILE_NAME = "not_included.properties";

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(Main_ProcessDirectoryMethodTest.class);
	private static File rootDirectory = null;
	@ClassRule
	public static TemporaryFolder testFolder = new TemporaryFolder();

	private Main main = null;
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
	public void configureTest() {
		
		main = new Main();
	}
	
	/**
	 * <p>Tries to process a file as a directory</p>
	 * @throws Exception This exception is never thrown
	 */
	@Test
	public void isFileTest() throws Exception {

		exceptionRule.expect(ConfigurationException.class);
		main.processDirectory(this.createEmptyFile(""));
	}
	
	/**
	 * <p>Tests an empty directory</p>
	 * @throws Exception
	 */
	@Test
	public void emptyDirectoryTest() throws Exception {
		
		List<ResourceFile> result = main.processDirectory(
				this.createEmptyDirectory());

		Assert.assertNotNull(result);
		Assert.assertTrue(result.isEmpty());
	}
	
	/**
	 * <p>Tests a correct directory with different kinds of files</p>
	 */
	@Test 
	public void processDirectory() {
		
		List<ResourceFile> resources = null;
		try {
			
			resources = main.processDirectory(rootDirectory);
		} catch (ConfigurationException e) {
			
			Assert.fail(e.getMessage());
		}
		
		for (ResourceFile rs : resources) {
			
			if (rs.getFilePath().equals(NOT_INCLUDED_PROPERTIES_FILE_NAME)) {
				
				Assert.fail(String.format("Invalid file '%s' has been included", NOT_INCLUDED_PROPERTIES_FILE_NAME));
			}
		}
	}
	
	@BeforeClass
	public static void setUpDirectories() throws IOException {
		
		testFolder.newFile(Main.DEFAULT_FILE_NAME_PATTERN + ".properties");
		testFolder.newFile(Main.DEFAULT_FILE_NAME_PATTERN + "_es.properties");
		testFolder.newFile(Main.DEFAULT_FILE_NAME_PATTERN + "_es_ES.properties");
		testFolder.newFile(NOT_INCLUDED_PROPERTIES_FILE_NAME);
		
		rootDirectory = testFolder.getRoot();
	}

	/**
	 * @param fileName
	 * @return
	 */
	private File createEmptyFile(String fileName) {

		File f = null;
		try {
			if (fileName == null || fileName.isEmpty()) {
				f = testFolder.newFile();
			} else {
				f = testFolder.newFile(fileName);
			}
		} catch (IOException e) {

			Assert.fail(e.getMessage());
		}
		return f;
	}

	/**
	 * @return
	 */
	private File createEmptyDirectory() {
		
		return this.createEmptyDirectory("");
	}
	
	/**
	 * @return
	 */
	private File createEmptyDirectory(String directoryName) {

		File directory = null;
		try {
			
			if (directoryName == null || directoryName.isEmpty()) {
				directory = testFolder.newFolder();
			} else {
				directory = testFolder.newFile(directoryName);
			}
		} catch (IOException e) {

			Assert.fail(e.getMessage());
		}
		return directory;
	}
}
