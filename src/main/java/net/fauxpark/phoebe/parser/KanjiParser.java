package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Kanji;

/**
 * A kanji oriented parser.
 *
 * @author fauxpark
 */
public class KanjiParser extends Parser<Kanji> {
	/**
	 * A list of {@link Node}s that the parser will iterate through in {@link KanjiParser#parse()}.
	 */
	private NodeList characters;

	private static final Logger log = LogManager.getLogger(KanjiParser.class);

	/**
	 * KanjiParser constructor.
	 *
	 * @param fileName The location of the kanjidic2.xml file.
	 */
	public KanjiParser(String fileName) {
		super(fileName, "kanjidic2");

		String fileVersion = document.getElementsByTagName("file_version").item(0).getTextContent();
		String dbVersion = document.getElementsByTagName("database_version").item(0).getTextContent();
		String creationDate = document.getElementsByTagName("date_of_creation").item(0).getTextContent();

		log.info("KANJIDIC2 Version " + fileVersion + " (" + dbVersion + "), created " + creationDate);

		characters = document.getElementsByTagName("character");
	}

	/**
	 * Where the magic happens. Parses the kanji dictionary.
	 *
	 * @param limit The number of kanji to parse. Null parses everything.
	 * @return A list of {@link Kanji} objects.
	 */
	@Override
	public List<Kanji> parse(Integer limit) {
		List<Kanji> kanjis = new ArrayList<>();

		if(limit == null || limit > characters.getLength()) {
			log.debug("Invalid parse limit: " + limit + ". Parsing all entries.");

			limit = characters.getLength();
		}

		log.info("Parsing " + limit + " entries.");

		for(int i = 0; i < limit; i++) {
			setElement(characters.item(i));
			Kanji kanji = new Kanji();
			kanji.setLiteral(getLiteral());
			kanji.setCodepoint(getCodepoint());
			kanji.setRadical(getRadical());
			kanji.setGrade(getGrade());
			kanji.setStrokeCount(getStrokeCount());
			kanji.setFrequency(getFrequency());
			kanji.setJlpt(getJlpt());
			kanji.setHeisig(getHeisig());
			kanji.setSkip(getSkip());
			kanji.setFourCorner(getFourCorner());
			kanji.setOnyomi(getOnyomi());
			kanji.setKunyomi(getKunyomi());
			kanji.setNanori(getNanori());
			kanji.setMeanings(getMeanings());
			kanjis.add(kanji);
		}

		return kanjis;
	}

	/**
	 * Get the current kanji's literal.
	 *
	 * @return A kanji literal in UTF-8 encoding.
	 */
	private String getLiteral() {
		return getByTagName("literal");
	}

	/**
	 * Get the current kanji's UCS codepoint.
	 *
	 * @return A UCS codepoint as a string.
	 */
	private String getCodepoint() {
		return getByTagAttributeValue("cp_value", "cp_type", "ucs").toLowerCase();
	}

	/**
	 * Get the current kanji's radical number.
	 *
	 * @return A number between 1 and 214 representing the KangXi radical of the kanji.
	 */
	private Integer getRadical() {
		try {
			return Integer.parseInt(getByTagAttributeValue("rad_value", "rad_type", "classical"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's grade.
	 *
	 * @return A number between 1 and 10 denoting the kanji's grade level, or null.
	 */
	private Integer getGrade() {
		try {
			return Integer.parseInt(getByTagName("grade"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's stroke count.
	 *
	 * @return The number of strokes it takes to draw the kanji, or null.
	 */
	private Integer getStrokeCount() {
		try {
			return Integer.parseInt(getByTagName("stroke_count"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's frequency.
	 *
	 * @return A number from 1 to 2500 denoting the general frequency of the kanji in newspapers, or null.
	 */
	private Integer getFrequency() {
		try {
			return Integer.parseInt(getByTagName("freq"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's (former) JLPT level.
	 *
	 * @return A number from 1 to 4 denoting the former JLPT level of the kanji, or null.
	 */
	private Integer getJlpt() {
		try {
			return Integer.parseInt(getByTagName("jlpt"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's Heisig index.
	 *
	 * @return A Heisig index for the kanji, or null.
	 */
	private Integer getHeisig() {
		try {
			return Integer.parseInt(getByTagAttributeValue("dic_ref", "dr_type", "heisig"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current kanji's SKIP (System of Kanji Indexing by Patterns) code.
	 *
	 * @return A SKIP code for the kanji.
	 */
	private String getSkip() {
		return getByTagAttributeValue("q_code", "qc_type", "skip");
	}

	/**
	 * Get the current kanji's four corner code.
	 *
	 * @return A four corner code for the kanji.
	 */
	private String getFourCorner() {
		return getByTagAttributeValue("q_code", "qc_type", "four_corner");
	}

	/**
	 * Get the current kanji's <i>on'yomi</i> (Chinese-derived) readings.
	 *
	 * @return A list of readings in katakana.
	 */
	private List<String> getOnyomi() {
		NodeList nl = character.getElementsByTagName("reading");
		List<String> retVal = new ArrayList<String>();
		Element el = null;

		for(int i = 0; i < nl.getLength(); i++) {
			el = (Element) nl.item(i);

			if(el.getAttribute("r_type").equals("ja_on")) {
				retVal.add(nl.item(i).getTextContent());
			}
		}

		return retVal;
	}

	/**
	 * Get the current kanji's <i>kun'yomi</i> (Japanese-derived) readings.
	 *
	 * @return A list of readings in hiragana.
	 */
	private List<String> getKunyomi() {
		NodeList nl = character.getElementsByTagName("reading");
		List<String> retVal = new ArrayList<String>();
		Element el = null;

		for(int i = 0; i < nl.getLength(); i++) {
			el = (Element) nl.item(i);

			if(el.getAttribute("r_type").equals("ja_kun")) {
				retVal.add(nl.item(i).getTextContent());
			}
		}

		return retVal;
	}

	/**
	 * Get the current kanji's <i>nanori</i> (name-only) readings.
	 *
	 * @return A list of readings in hiragana.
	 */
	private List<String> getNanori() {
		NodeList nl = character.getElementsByTagName("nanori");
		List<String> retVal = new ArrayList<String>();

		for(int i = 0; i < nl.getLength(); i++) {
			retVal.add(nl.item(i).getTextContent());
		}

		return retVal;
	}

	/**
	 * Get the current kanji's English meanings.
	 *
	 * @return A list of meanings.
	 */
	private List<String> getMeanings() {
		NodeList nl = character.getElementsByTagName("meaning");
		List<String> retVal = new ArrayList<String>();

		for(int i = 0; i < nl.getLength(); i++) {
			// Meanings with no "m_lang" attribute are in English
			if(!nl.item(i).hasAttributes()) {
				retVal.add(nl.item(i).getTextContent());
			}
		}

		return retVal;
	}
}
