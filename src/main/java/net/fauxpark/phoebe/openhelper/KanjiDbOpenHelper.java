package net.fauxpark.phoebe.openhelper;

/**
 * A Kanji database open helper.
 *
 * @author fauxpark
 */
public class KanjiDbOpenHelper extends BaseDbOpenHelper {
	/**
	 * A static instance of this class.
	 */
	private static KanjiDbOpenHelper kanjiDbOpenHelper = null;

	/**
	 * Create a new KanjiDbOpenHelper.
	 *
	 * @param fileName The filename of the database to open or create.
	 */
	public KanjiDbOpenHelper(String fileName) {
		super(fileName);
	}

	/**
	 * Retrieve the static instance of this class, creating it if necessary.
	 *
	 * @param fileName The file name to pass to the constructor.
	 * @return A KanjiDbOpenHelper instance.
	 */
	public static KanjiDbOpenHelper getOpenHelper(String fileName) {
		if(kanjiDbOpenHelper == null) {
			kanjiDbOpenHelper = new KanjiDbOpenHelper(fileName);
		}

		return kanjiDbOpenHelper;
	}
}
