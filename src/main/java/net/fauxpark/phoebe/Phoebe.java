package net.fauxpark.phoebe;

import net.fauxpark.phoebe.dao.KanjiDataSource;
import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.RadicalParser;

public class Phoebe {
	private static Config config;

	public static void main(String[] args) {
		try {
			config = new Config();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		KanjiParser kanjiParser = new KanjiParser(config.getKanjidicLocation());
		KanjiDataSource kanjiDao = new KanjiDataSource(config.getKanjiDbLocation());
		kanjiDao.createTable();

		RadicalParser radicalParser = new RadicalParser(config.getRadicalDicLocation());

		long start = System.currentTimeMillis();
		System.out.println("Adding kanji...");
		kanjiDao.addKanji(kanjiParser.parse(config.getKanjiParseLimit()));
		System.out.println("Adding radicals...");
		kanjiDao.addRadicals(radicalParser.parse(config.getKanjiParseLimit()));
		long end = System.currentTimeMillis();
		System.out.println("Done, took " + ((end - start) / 1000.0) + " seconds.");

		kanjiDao.close();
	}
}
