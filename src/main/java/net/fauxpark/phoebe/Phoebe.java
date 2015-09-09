package net.fauxpark.phoebe;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.parser.ComponentsParser;
import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.RadicalsParser;
import net.fauxpark.phoebe.parser.WhiteRabbitParser;
import net.fauxpark.phoebe.provider.KanjiProvider;

public class Phoebe {
	private static final Logger log = LogManager.getLogger(Phoebe.class);

	public static void main(String[] args) {
		try {
			Config.load();
		} catch(IOException e) {
			log.error("Could not load default configuration.", e);

			System.exit(1);
		}

		// Setup parsers
		KanjiParser kanjiParser = new KanjiParser(Config.getString("dic.kanji.location"));
		ComponentsParser componentsParser = new ComponentsParser(Config.getString("dic.components.location"));
		RadicalsParser radicalsParser = new RadicalsParser(Config.getString("dic.radicals.location"));
		WhiteRabbitParser whiteRabbitParser = new WhiteRabbitParser(Config.getString("dic.whiterabbit.location"));

		// Setup DB providers
		KanjiProvider kanjiProvider = new KanjiProvider(Config.getString("db.kanji.location"));
		kanjiProvider.createTables();

		long start = System.currentTimeMillis();

		log.info("Adding kanji");

		Integer parseLimit = Config.getInt("dic.kanji.parselimit");

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
