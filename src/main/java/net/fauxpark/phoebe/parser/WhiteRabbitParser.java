package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.WhiteRabbitIndex;

/**
 * A White Rabbit Japan kanji poster/flashcards oriented parser.
 *
 * @author fauxpark
 */
public class WhiteRabbitParser extends Parser<WhiteRabbitIndex> {
	/**
	 * A list of {@link Node}s that the parser will iterate through in {@link WhiteRabbitParser#parse()}
	 */
	private NodeList characters;

	private static final Logger log = LogManager.getLogger(WhiteRabbitParser.class);

	/**
	 * WhiteRabbitParser constructor.
	 *
	 * @param fileName The location of the whiterabbit.xml file.
	 */
	public WhiteRabbitParser(String fileName) {
		super(fileName, "whiterabbit");

		characters = document.getElementsByTagName("kanji");
	}

	/**
	 * Where the magic happens. Parses the White Rabbit index list.
	 *
	 * @param limit Ignored.
	 * @return A list of {@link WhiteRabbitIndex} objects.
	 */
	@Override
	public List<WhiteRabbitIndex> parse(Integer limit) {
		List<WhiteRabbitIndex> indexes = new ArrayList<>();

		log.info("Parsing " + characters.getLength() + " White Rabbit indexes");

		for(int i = 0; i < characters.getLength(); i++) {
			setElement(characters.item(i));
			WhiteRabbitIndex index = new WhiteRabbitIndex();
			index.setLiteral(getLiteral());
			index.setIndex(getIndex());
			index.setVariant(getVariant());
			indexes.add(index);
		}

		return indexes;
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
	 * Get the current kanji's index.
	 *
	 * @return A numeric string from 1 to 1945, or N1 to N196 for the New Jōyō kanji.
	 */
	private String getIndex() {
		return getByTagName("index");
	}

	/**
	 * Get the current kanji's JIS X 0208 variant.
	 *
	 * @return A kanji literal in UTF-8 encoding.
	 */
	private String getVariant() {
		return getByTagName("variant");
	}
}
