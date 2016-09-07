package Minesweeper;
/**
 * @file SaveFileFilter.java
 * @author Kit Manners
 * @date 16/02/2016
 * @brief A filter to be used by a file selector which limits the files shown
 * to ones with a specific extension
 */

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @class SaveFileFilter
 * @brief A filter to be used by a file selector which limits the files shown
 * to ones with a specific extension
 */
public class SaveFileFilter extends FileFilter {

	/** The file extension to be used for save files */
	private static final String EXTENSION = ".kbw";
	private static final String DESCRIPTION =
			"Kablewie save files (*" + EXTENSION + ")";

	
	/**
	 * Returns true if the file has the correct format
	 */
	@Override
	public boolean accept(File file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');

		// check there is an extension
		if (i == -1) {
			return false;
		}

		String ext = name.substring(i);
		return EXTENSION.equals(ext);
	}

	/**
	 * returns the description to be shown in the window
	 */
	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	/**
	 * Converts a file to a valid save file
	 * @param old the file to convert
	 * @return the valid save file (could be the same object passed in)
	 */
	public File toSaveFile(File old) {
		if (accept(old)) {
			return old;
		}

		// when a user enters a name without an extension,
		// we need to add the extension ourselves
		return new File(old.getPath() + EXTENSION);
	}
}
