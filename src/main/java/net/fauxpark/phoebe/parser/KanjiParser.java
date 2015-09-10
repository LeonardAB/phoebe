package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Kanji;

/**
 * A kanji oriented parser.
 *
 * @author fauxpark
 */
public class KanjiParser extends Parser<Kanji> {
	/**
	 * A list of Nodes that the parser will iterate through.
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
	 * @return A list of kanji.
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
			kanji.setLiteral(getByTagName("literal"));
			kanji.setCodepoint(getByTagAttributeValue("cp_value", "cp_type", "ucs").toLowerCase());
			kanji.setRadical(getByTagAttributeValueInt("rad_value", "rad_type", "classical"));
			kanji.setGrade(getByTagNameInt("grade"));
			kanji.setStrokeCount(getByTagNameInt("stroke_count"));
			kanji.setFrequency(getByTagNameInt("frequency"));
			kanji.setJlpt(getByTagNameInt("jlpt"));
			kanji.setHeisig(getByTagAttributeValueInt("dic_ref", "dr_type", "heisig"));
			kanji.setSkip(getByTagAttributeValue("q_code", "qc_type", "skip"));
			kanji.setFourCorner(getByTagAttributeValue("q_code", "qc_type", "four_corner"));
			kanji.setOnyomi(getOnyomi());
			kanji.setKunyomi(getKunyomi());
			kanji.setNanori(getNanori());
			kanji.setMeanings(getMeanings());
			kanjis.add(kanji);
		}

		return kanjis;
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
	 * Get the current kanji's meanings.
	 *
	 * @return A list of meanings in English.
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
