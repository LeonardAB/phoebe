package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.PartOfSpeech;
import net.fauxpark.phoebe.model.Word;

/**
 * A word oriented parser.
 *
 * @author fauxpark
 */
public class WordParser extends Parser<Word> {
	/**
	 * A list of Nodes that the parser will iterate through.
	 */
	private NodeList entries;

	private static final Logger log = LogManager.getLogger(WordParser.class);

	public WordParser(String fileName) {
		super(fileName, "JMdict");

		entries = document.getElementsByTagName("entry");
	}

	/**
	 * Where the magic happens. Parses the dictionary.
	 *
	 * @param limit Ignored.
	 * @return A list of words.
	 */
	@Override
	public List<Word> parse(Integer limit) {
		List<Word> words = new ArrayList<>();

		log.info("Parsing " + entries.getLength() + " entries.");

		for(int i = 0; i < entries.getLength(); i++) {
			setElement(entries.item(i));
			Word word = new Word();

			word.setId(getByTagNameInt("ent_seq"));
			word.setKanji(getKanji());
			word.setReadings(getReadings());
			word.setMeanings(getMeanings());
			word.setPos(getPos());
			words.add(word);
		}

		return words;
	}

	private List<String> getKanji() {
		NodeList nl = character.getElementsByTagName("keb");
		List<String> retVal = new ArrayList<String>();

		for(int i = 0; i < nl.getLength(); i++) {
			retVal.add(nl.item(i).getTextContent());
		}

		return retVal;
	}

	private List<String> getReadings() {
		NodeList nl = character.getElementsByTagName("reb");
		List<String> retVal = new ArrayList<String>();

		for(int i = 0; i < nl.getLength(); i++) {
			retVal.add(nl.item(i).getTextContent());
		}

		return retVal;
	}

	private List<String> getMeanings() {
		NodeList nl = character.getElementsByTagName("gloss");
		List<String> retVal = new ArrayList<String>();

		for(int i = 0; i < nl.getLength(); i++) {
			retVal.add(nl.item(i).getTextContent());
		}

		return retVal;
	}

	private Set<Integer> getPos() {
		NodeList posTags = character.getElementsByTagName("pos");
		NodeList fieldTags = character.getElementsByTagName("field");
		NodeList miscTags = character.getElementsByTagName("misc");
		Set<Integer> retVal = new LinkedHashSet<Integer>();

		for(int i = 0; i < posTags.getLength(); i++) {
			retVal.add(PartOfSpeech.getPosCode(posTags.item(i).getTextContent()));
		}

		for(int i = 0; i < fieldTags.getLength(); i++) {
			retVal.add(PartOfSpeech.getPosCode(fieldTags.item(i).getTextContent()));
		}

		for(int i = 0; i < miscTags.getLength(); i++) {
			retVal.add(PartOfSpeech.getPosCode(miscTags.item(i).getTextContent()));
		}

		return retVal;
	}
}
