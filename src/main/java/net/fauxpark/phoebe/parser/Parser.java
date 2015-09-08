package net.fauxpark.phoebe.parser;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger log = LogManager.getLogger(Parser.class);

	/**
	 * Parser constructor.
	 *
	 * @param fileName The input .xml document to parse into a {@link Document}.
	 * @param rootTag The root tag which the document must have.
	 */
	public Parser(String fileName, String rootTag) {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			document = dBuilder.parse(new File(fileName));

			log.info("Parsed file: " + fileName);

			if(!document.getDocumentElement().getTagName().equals(rootTag)) {
				throw new UnsupportedOperationException("Root tag \"" + rootTag + "\" not found.");
			}
		} catch(Exception e) {
			log.error("Could not parse the XML document.", e);
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
	 * Retrieve the contents of the first element with the specified tag name, as an Integer.
	 *
	 * @param tag Tag name to search for.
	 * @return Numerical representation of the tag's contents, or null if nothing was found.
	 */
	protected Integer getByTagNameInt(String tag) {
		try {
			return Integer.parseInt(getByTagName(tag));
		} catch(NumberFormatException e) {
			return null;
		}
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

	/**
	 * Retrieve the contents of the first element with matching tag name, attribute and attribute value, as an Integer.
	 *
	 * @param tag Tag name to search for.
	 * @param attr Name of the attribute.
	 * @param value Value of the attribute.
	 * @return Numerical representation of the tag's contents, or null if nothing was found.
	 */
	protected Integer getByTagAttributeValueInt(String tag, String attr, String value) {
		try {
			return Integer.parseInt(getByTagAttributeValue(tag, attr, value));
		} catch(NumberFormatException e) {
			return null;
		}
	}
}
