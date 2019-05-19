package gl.nozing.l10nChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class Main_IsProcessableFileMethodTest {

	@Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test
	public void isProcessableFileTest() {
		
		Main main = new Main();
				
		Assert.assertFalse(main.isProcessableFile(createEmptyDirectory()));
		
		File f = createEmptyFile();
		Assert.assertFalse(main.isProcessableFile(f));
		
		f = createEmptyFile();
		try {
			Files.setAttribute(FileSystems.getDefault().getPath(f.getPath()), "dos:hidden", true);
		} catch (IOException e) {

			Assert.fail(e.getMessage());
		}
		Assert.assertFalse(main.isProcessableFile(f));

		f = createEmptyFile();
		f.setReadable(false);
		Assert.assertFalse(main.isProcessableFile(f));
		
		f = createEmptyFile("ApplicationResources");
		Assert.assertFalse(main.isProcessableFile(f));
		
		f = createEmptyFile("ApplicationResources.properties");
		Assert.assertTrue(main.isProcessableFile(f));
	}

	/**
	 * @return
	 */
	private File createEmptyFile() {
		
		return this.createEmptyFile("");
	}
	
	/**
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
		
		File directory = null;
		try {
			directory = testFolder.newFolder();
		} catch (IOException e) {

			Assert.fail(e.getMessage());
		}
		return directory;
	}
}
