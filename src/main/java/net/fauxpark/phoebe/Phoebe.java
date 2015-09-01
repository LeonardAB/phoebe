package net.fauxpark.phoebe;

import net.fauxpark.phoebe.parser.KanjiParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.parser.ComponentsParser;
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

		KanjiParser kanjiParser = new KanjiParser(Config.getKanjiDicLocation());
		KanjiProvider kanjiProvider = new KanjiProvider(Config.getKanjiDbLocation());
		kanjiProvider.createTable();

		ComponentsParser radicalParser = new ComponentsParser(Config.getRadicalDicLocation());

		long start = System.currentTimeMillis();
		log.info("Adding kanji");
		kanjiProvider.addKanji(kanjiParser.parse(Config.getKanjiParseLimit()));
		log.info("Adding components");
		kanjiProvider.addRadicals(radicalParser.parse(Config.getKanjiParseLimit()));
		long end = System.currentTimeMillis();
		log.info("Done, took " + ((end - start) / 1000.0) + " seconds.");

		kanjiProvider.close();
	}
}
