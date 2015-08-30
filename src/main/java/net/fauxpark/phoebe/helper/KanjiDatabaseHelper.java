package net.fauxpark.phoebe.helper;

/**
 * A Kanji database helper.
 *
 * @author fauxpark
 */
public class KanjiDatabaseHelper extends DatabaseHelper {
	/**
	 * The singleton-ish instance of this class.
	 */
	private static KanjiDatabaseHelper kanjiDbHelper = null;

	/**
	 * KanjiDatabaseHelper constructor.
	 *
	 * @param fileName The filename of the database to open (or create if it is nonexistent).
	 */
	public KanjiDatabaseHelper(String fileName) {
		super(fileName);
	}

	/**
	 * Retrieve the singleton.
	 *
	 * @param fileName The file name to pass to the constructor.
	 * @return A KanjiDatabaseHelper instance.
	 */
	public static KanjiDatabaseHelper getInstance(String fileName) {
		if(kanjiDbHelper == null) {
			kanjiDbHelper = new KanjiDatabaseHelper(fileName);
		}

		return kanjiDbHelper;
	}
}
