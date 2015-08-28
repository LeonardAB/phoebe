package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Radical;

/**
 * A radical oriented parser.
 *
 * @author fauxpark
 */
public class RadicalParser extends BaseParser<Radical> {
	/**
	 * A list of {@link Node}s that the parser will iterate through in {@link RadicalParser#parse()}.
	 */
	private NodeList characters;

	/**
	 * Creates a new {@link RadicalParser}.
	 *
	 * @param fileName The location of the kradx.xml file.
	 * @throws UnsupportedOperationException if the root element is not &lt;kradx&gt;.
	 */
	public RadicalParser(String fileName) throws UnsupportedOperationException {
		super(fileName);

		if(!document.getDocumentElement().getTagName().equals("kradx")) {
			throw new UnsupportedOperationException("This is not a KRADX file!");
		}

		System.out.println("KRADX Version " + document.getElementsByTagName("file_version").item(0).getTextContent());
		System.out.println("Database Version " + document.getElementsByTagName("database_version").item(0).getTextContent());
		System.out.println("DB Creation Date " + document.getElementsByTagName("date_of_creation").item(0).getTextContent());
		System.out.println();

		characters = document.getElementsByTagName("radical");
	}

	/**
	 * Where the magic happens. Parses the radical dictionary.
	 *
	 * @param limit The number of radicals to parse. Null parses everything.
	 * @return A list of {@link Radical} objects.
	 */
	public List<Radical> parse(Integer limit) {
		List<Radical> radicals = new ArrayList<>();

		if(limit == null || limit > characters.getLength()) {
			limit = characters.getLength();
		}

		System.out.println("Parsing " + limit + " radicals.");

		for(int i = 0; i < limit; i++) {
			setElement(characters.item(i));
			Radical radical = new Radical();
			radical.setLiteral(getLiteral());
			radical.setComponents(getComponents());
			radicals.add(radical);
		}

		return radicals;
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
	 * Get the current kanji's components.
	 *
	 * @return The components of the kanji in UTF-8 encoding.
	 */
	private String getComponents() {
		return getByTagName("components");
	}
}
