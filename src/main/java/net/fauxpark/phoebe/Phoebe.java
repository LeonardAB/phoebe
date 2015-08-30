package net.fauxpark.phoebe;

import net.fauxpark.phoebe.parser.KanjiParser;
import net.fauxpark.phoebe.parser.ComponentsParser;
import net.fauxpark.phoebe.provider.KanjiProvider;

public class Phoebe {
	public static void main(String[] args) {
		try {
			Config.load();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		KanjiParser kanjiParser = new KanjiParser(Config.getKanjiDicLocation());
		KanjiProvider kanjiProvider = new KanjiProvider(Config.getKanjiDbLocation());
		kanjiProvider.createTable();

		ComponentsParser radicalParser = new ComponentsParser(Config.getRadicalDicLocation());

		long start = System.currentTimeMillis();
		System.out.println("Adding kanji...");
		kanjiProvider.addKanji(kanjiParser.parse(Config.getKanjiParseLimit()));
		System.out.println("Adding radicals...");
		kanjiProvider.addRadicals(radicalParser.parse(Config.getKanjiParseLimit()));
		long end = System.currentTimeMillis();
		System.out.println("Done, took " + ((end - start) / 1000.0) + " seconds.");

		kanjiProvider.close();
	}
}
