package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.WhiteRabbitIndex;

/**
 * A White Rabbit Japan kanji poster/flashcards oriented parser.
 *
 * @author fauxpark
 */
public class WhiteRabbitParser extends Parser<WhiteRabbitIndex> {
	/**
	 * A list of Nodes that the parser will iterate through.
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
	 * @return A list of kanji and their White Rabbit indexes.
	 */
	@Override
	public List<WhiteRabbitIndex> parse(Integer limit) {
		List<WhiteRabbitIndex> indexes = new ArrayList<>();

		log.info("Parsing {} White Rabbit indexes", characters.getLength());

		for(int i = 0; i < characters.getLength(); i++) {
			setElement(characters.item(i));
			WhiteRabbitIndex index = new WhiteRabbitIndex();
			index.setLiteral(getByTagName("literal"));
			index.setIndex(getByTagName("index"));
			index.setVariant(getByTagName("variant"));
			indexes.add(index);
		}

		return indexes;
	}
}
