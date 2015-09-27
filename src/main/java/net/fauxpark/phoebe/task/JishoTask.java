package net.fauxpark.phoebe.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.Config;
import net.fauxpark.phoebe.parser.WordParser;
import net.fauxpark.phoebe.provider.JishoProvider;

/**
 * A task that parses Japanese dictionary files and inserts them into a database.
 *
 * @author fauxpark
 */
public class JishoTask {
	/**
	 * The parser for the JMDict file.
	 */
	private static WordParser wordParser;

	/**
	 * The provider for the jisho database.
	 */
	private static JishoProvider jishoProvider;

	private static final Logger log = LogManager.getLogger(JishoTask.class);

	/**
	 * Runs the task.
	 */
	public static void run() {
		long start = System.currentTimeMillis();

		// Set up parser
		wordParser = new WordParser(Config.getString("dic.words.location"));

		// Set up provider
		jishoProvider = new JishoProvider(Config.getString("db.jisho.location"));

		// Create tables
		jishoProvider.createTables();

		log.info("Adding words");

		jishoProvider.addWords(wordParser.parse(null));

		jishoProvider.close();

		long end = System.currentTimeMillis();

		log.info("Done, took " + ((end - start) / 1000.0) + " seconds.");
	}
}
