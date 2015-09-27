package net.fauxpark.phoebe.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.Config;
import net.fauxpark.phoebe.parser.ComponentsParser;
import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.RadicalsParser;
import net.fauxpark.phoebe.parser.WhiteRabbitParser;
import net.fauxpark.phoebe.provider.KanjiProvider;

/**
 * A task that parses kanji dictionary files and inserts them into a database.
 *
 * @author fauxpark
 */
public class KanjiTask {
	/**
	 * The parser for the KANJIDIC2 file.
	 */
	private static KanjiParser kanjiParser;

	/**
	 * The parser for the KRADX file.
	 */
	private static ComponentsParser componentsParser;

	/**
	 * The parser for the White Rabbit indexes file.
	 */
	private static WhiteRabbitParser whiteRabbitParser;

	/**
	 * The parser for the kanji radicals file.
	 */
	private static RadicalsParser radicalsParser;

	/**
	 * The provider for the kanji database.
	 */
	private static KanjiProvider kanjiProvider;

	private static final Logger log = LogManager.getLogger(KanjiTask.class);

	/**
	 * Runs the task.
	 */
	public static void run() {
		long start = System.currentTimeMillis();

		// Set up parsers
		kanjiParser = new KanjiParser(Config.getString("dic.kanji.location"));
		componentsParser = new ComponentsParser(Config.getString("dic.components.location"));
		radicalsParser = new RadicalsParser(Config.getString("dic.radicals.location"));
		whiteRabbitParser = new WhiteRabbitParser(Config.getString("dic.whiterabbit.location"));

		// Set up provider
		kanjiProvider = new KanjiProvider(Config.getString("db.kanji.location"));

		kanjiProvider.createTables();

		Integer parseLimit = Config.getInt("dic.kanji.parselimit");

		log.info("Adding kanji");

		kanjiProvider.addKanji(kanjiParser.parse(parseLimit));

		log.info("Adding components");

		kanjiProvider.addComponents(componentsParser.parse(parseLimit));

		log.info("Adding radicals");

		kanjiProvider.addRadicals(radicalsParser.parse(null));

		log.info("Adding White Rabbit indexes");

		kanjiProvider.addWhiteRabbitIndexes(whiteRabbitParser.parse(null));

		kanjiProvider.close();

		long end = System.currentTimeMillis();

		log.info("Done, took " + ((end - start) / 1000.0) + " seconds.");
	}
}
