package net.fauxpark.phoebe.parser;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A base parser for working with dictionary files.
 *
 * @author fauxpark
 *
 * @param <T> The type of model object the class will work with.
 */
public abstract class Parser<T> {
	/**
	 * The {@link Document} to be parsed.
	 */
	protected Document document;

	/**
	 * The current character being parsed.
	 */
	protected Element character;

	/**
	 * Parser constructor.
	 *
	 * @param fileName the input .xml document to parse into a {@link Document}.
	 */
	public Parser(String fileName) {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			document = dBuilder.parse(new File(fileName));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse the document and return a list of model objects.
	 *
	 * @param limit The number of elements to parse. Null should parse everything.
	 * @return A list of resulting model objects.
	 */
	public abstract List<T> parse(Integer limit);

	/**
	 * Set the current character to parse.
	 *
	 * @param character A {@link Node} object which will be cast to an {@link Element} and set as the current character.
	 */
	protected void setElement(Node character) {
		this.character = (Element) character;
	}

	/**
	 * Retrieve text content of the first element with the specified tag name.
	 *
	 * @param tag Tag name to search for.
	 * @return Text content of the tag, or null if nothing was found.
	 */
	protected String getByTagName(String tag) {
		NodeList nl = character.getElementsByTagName(tag);

		if(nl.getLength() > 0) {
			return nl.item(0).getTextContent();
		}

		return null;
	}

	/**
	 * Retrieve text content of the first element with matching tag name, attribute and attribute value.
	 *
	 * @param tag Tag name to search for.
	 * @param attr Name of the attribute.
	 * @param value Value of the attribute.
	 * @return Text content of the tag, or null if nothing was found.
	 */
	protected String getByTagAttributeValue(String tag, String attr, String value) {
		NodeList nl = character.getElementsByTagName(tag);
		Element el = null;

		for(int i = 0; i < nl.getLength(); i++) {
			el = (Element) nl.item(i);

			if(el.getAttribute(attr).equals(value)) {
				return nl.item(i).getTextContent();
			}
		}

		return null;
	}
}
