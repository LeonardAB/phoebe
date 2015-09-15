package net.fauxpark.phoebe.helper;

/**
 * A Jisho database helper.
 *
 * @author fauxpark
 */
public class JishoDatabaseHelper extends DatabaseHelper {
	/**
	 * The internal database helper instance.
	 */
	private static JishoDatabaseHelper jishoDbHelper = null;

	/**
	 * JishoDatabaseHelper constructor.
	 *
	 * @param fileName The filename of the database to open (or create if it is nonexistent).
	 */
	public JishoDatabaseHelper(String fileName) {
		super(fileName);
	}

	/**
	 * Retrieve the database helper, creating a new one if it does not exist.
	 *
	 * @param fileName The file name to pass to the constructor.
	 * @return The JishoDatabaseHelper instance.
	 */
	public static JishoDatabaseHelper getInstance(String fileName) {
		if(jishoDbHelper == null) {
			jishoDbHelper = new JishoDatabaseHelper(fileName);
		}

		return jishoDbHelper;
	}
}
