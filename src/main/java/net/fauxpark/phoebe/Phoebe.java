package net.fauxpark.phoebe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.parser.ComponentsParser;
import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.RadicalsParser;
import net.fauxpark.phoebe.provider.KanjiProvider;

public class Phoebe {
	private static final Logger log = LogManager.getLogger(Phoebe.class);

	public static void main(String[] args) {
		try {
			Config.load();
		} catch(Exception e) {
			log.error("Could not load configuration.", e);
			System.exit(1);
		}

		// Setup parsers
		KanjiParser kanjiParser = new KanjiParser(Config.getKanjiDicLocation());
		ComponentsParser componentsParser = new ComponentsParser(Config.getComponentsDicLocation());
		RadicalsParser radicalsParser = new RadicalsParser(Config.getRadicalsDicLocation());

		// Setup DB providers
		KanjiProvider kanjiProvider = new KanjiProvider(Config.getKanjiDbLocation());
		kanjiProvider.createTables();

		long start = System.currentTimeMillis();

		log.info("Adding kanji");

		kanjiProvider.addKanji(kanjiParser.parse(Config.getKanjiParseLimit()));

		log.info("Adding components");

		kanjiProvider.addComponents(componentsParser.parse(Config.getKanjiParseLimit()));

		log.info("Adding radicals");

		kanjiProvider.addRadicals(radicalsParser.parse(null));

		kanjiProvider.close();

		long end = System.currentTimeMillis();

		log.info("Done, took " + ((end - start) / 1000.0) + " seconds.");
	}
}
