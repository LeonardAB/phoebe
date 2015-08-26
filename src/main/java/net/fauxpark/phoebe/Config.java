package net.fauxpark.phoebe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * A simple configuration class.
 *
 * @author fauxpark
 */
public class Config {
	/**
	 * The location of the configuration file.
	 */
	private static final String PROPERTIES_LOCATION = "phoebe.properties";

	/**
	 * Default location of the KANJIDIC2 file.
	 */
	private static final String KANJIDIC_LOCATION = "kanjidic2.xml";

	/**
	 * Default location of the resulting kanji database.
	 */
	private static final String KANJIDB_LOCATION = "kanji.db";

	/**
	 * Default location of the KRADX file.
	 */
	private static final String KRADX_LOCATION = "kradx.xml";

	private Properties properties = new Properties();

	/**
	 * Load configuration.
	 *
	 * @throws IOException if an error occurred when loading the properties file.
	 */
	public Config() throws IOException {
		File file = new File(PROPERTIES_LOCATION);

		try {
			FileInputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);

			System.out.println("Loaded configuration from " + file.getAbsolutePath());
		} catch(FileNotFoundException e) {
			System.err.println("Could not find properties file at \"" + file.getAbsolutePath() + "\"!");
			System.err.println("Attempting to use default values instead.");
		}
	}

	/**
	 * Get the number of kanji to parse, defaulting to null if absent.
	 *
	 * @return The number of kanji to parse.
	 */
	public Integer getKanjiParseLimit() {
		try{
			return Integer.parseInt(properties.getProperty("kanji.parselimit"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the location of the KANJIDIC2 file, defaulting to {@value #KANJIDIC_LOCATION} if absent.
	 *
	 * @return Where to find the KANJIDIC2 file.
	 */
	public String getKanjidicLocation() {
		return properties.getProperty("kanji.dict.location", KANJIDIC_LOCATION);
	}

	/**
	 * Get the location of the resulting kanji database, defaulting to {@value #KANJIDB_LOCATION} if absent.
	 *
	 * @return Where to place the resulting kanji database.
	 */
	public String getKanjiDbLocation() {
		return properties.getProperty("kanji.db.location", KANJIDB_LOCATION);
	}

	/**
	 * Get the location of the KRADX file, defaulting to {@value #KRADX_LOCATION} if absent.
	 *
	 * @return Where to find the KRADX file.
	 */
	public String getRadicalDicLocation() {
		return properties.getProperty("radicals.dict.location", KRADX_LOCATION);
	}
}
