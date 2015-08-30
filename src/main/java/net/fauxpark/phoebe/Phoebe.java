package net.fauxpark.phoebe;

import net.fauxpark.phoebe.dao.KanjiDataSource;
import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.RadicalParser;

public class Phoebe {
	public static void main(String[] args) {
		try {
			Config.load();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		KanjiParser kanjiParser = new KanjiParser(Config.getKanjiDicLocation());
		KanjiDataSource kanjiDao = new KanjiDataSource(Config.getKanjiDbLocation());
		kanjiDao.createTable();

		RadicalParser radicalParser = new RadicalParser(Config.getRadicalDicLocation());

		long start = System.currentTimeMillis();
		System.out.println("Adding kanji...");
		kanjiDao.addKanji(kanjiParser.parse(Config.getKanjiParseLimit()));
		System.out.println("Adding radicals...");
		kanjiDao.addRadicals(radicalParser.parse(Config.getKanjiParseLimit()));
		long end = System.currentTimeMillis();
		System.out.println("Done, took " + ((end - start) / 1000.0) + " seconds.");

		kanjiDao.close();
	}
}
