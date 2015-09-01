package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Radical;

/**
 * A Radical oriented parser.
 *
 * @author fauxpark
 */
public class RadicalsParser extends Parser<Radical> {
	/**
	 * A list of {@link Node}s that the parser will iterate through in {@link RadicalsParser#parse()}.
	 */
	private NodeList characters;

	private static final Logger log = LogManager.getLogger(RadicalsParser.class);

	/**
	 * RadicalsParser constructor.
	 *
	 * @param fileName The location of the radicals.xml file.
	 */
	public RadicalsParser(String fileName) {
		super(fileName, "radicals");

		characters = document.getElementsByTagName("radical");
	}

	/**
	 * Where the magic happens. Parses the radical dictionary.
	 *
	 * @param limit Ignored.
	 * @return A list of {@link Radical} objects.
	 */
	@Override
	public List<Radical> parse(Integer limit) {
		List<Radical> radicals = new ArrayList<>();

		log.info("Parsing " + characters.getLength() + " entries.");

		for(int i = 0; i < characters.getLength(); i++) {
			setElement(characters.item(i));
			Radical radical = new Radical();
			radical.setLiteral(getLiteral());
			radical.setName(getName());
			radical.setStrokeCount(getStrokeCount());
			radical.setVariants(getVariants());
			radicals.add(radical);
		}

		return radicals;
	}

	/**
	 * Get the current radical's literal.
	 *
	 * @return A radical literal in UTF-8 encoding.
	 */
	private String getLiteral() {
		return getByTagName("literal");
	}

	/**
	 * Get the current radical's name.
	 *
	 * @return A comma-separated list of the radical's names.
	 */
	private String getName() {
		return getByTagName("name");
	}

	/**
	 * Get the current radical's stroke count.
	 *
	 * @return The number of strokes it takes to draw the radical, or null.
	 */
	private Integer getStrokeCount() {
		try {
			return Integer.parseInt(getByTagName("stroke_count"));
		} catch(NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Get the current radical's variants.
	 *
	 * @return A string containing the variants on the radical, or null.
	 */
	private String getVariants() {
		return getByTagName("variants");
	}
}