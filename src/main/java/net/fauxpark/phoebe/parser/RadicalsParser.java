package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Radical;

/**
 * A Radical oriented parser.
 *
 * @author fauxpark
 */
public class RadicalsParser extends Parser<Radical> {
	/**
	 * A list of Nodes that the parser will iterate through.
	 */
	private NodeList characters;

	private static final Logger log = LogManager.getLogger(RadicalsParser.class);

	/**
	 * RadicalsParser constructor.
	 *
	 * @param fileName The location of the radicals.xml file.
	 */
	public RadicalsParser(String fileName) {
		super(fileName, "radic");

		characters = document.getElementsByTagName("radical");
	}

	/**
	 * Where the magic happens. Parses the radical dictionary.
	 *
	 * @param limit Ignored.
	 * @return A list of kanji radicals.
	 */
	@Override
	public List<Radical> parse(Integer limit) {
		List<Radical> radicals = new ArrayList<>();

		log.info("Parsing " + characters.getLength() + " entries.");

		for(int i = 0; i < characters.getLength(); i++) {
			setElement(characters.item(i));
			Radical radical = new Radical();
			radical.setLiteral(getByTagName("literal"));
			radical.setName(getByTagName("name"));
			radical.setReading(getByTagName("reading"));
			radical.setStrokeCount(getByTagNameInt("stroke_count"));
			radical.setVariants(getByTagName("variants"));
			radicals.add(radical);
		}

		return radicals;
	}
}
