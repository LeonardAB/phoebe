package net.fauxpark.phoebe.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

import net.fauxpark.phoebe.model.Components;

/**
 * A kanji component oriented parser.
 *
 * @author fauxpark
 */
public class ComponentsParser extends Parser<Components> {
	/**
	 * A list of Nodes that the parser will iterate through.
	 */
	private NodeList characters;

	private static final Logger log = LogManager.getLogger(ComponentsParser.class);

	/**
	 * ComponentsParser constructor.
	 *
	 * @param fileName The location of the kradx.xml file.
	 */
	public ComponentsParser(String fileName) {
		super(fileName, "kradx");

		String fileVersion = document.getElementsByTagName("file_version").item(0).getTextContent();
		String dbVersion = document.getElementsByTagName("database_version").item(0).getTextContent();
		String creationDate = document.getElementsByTagName("date_of_creation").item(0).getTextContent();

		log.info("KRADX Version {} ({}), created {}", fileVersion, dbVersion, creationDate);

		characters = document.getElementsByTagName("kanji");
	}

	/**
	 * Where the magic happens. Parses the components dictionary.
	 *
	 * @param limit The number of components to parse. Null parses everything.
	 * @return A list of kanji and their components.
	 */
	@Override
	public List<Components> parse(Integer limit) {
		List<Components> components = new ArrayList<>();

		if(limit == null || limit > characters.getLength()) {
			log.debug("Invalid parse limit: {}. Parsing all entries.", limit);

			limit = characters.getLength();
		}

		log.info("Parsing {} entries.", limit);

		for(int i = 0; i < limit; i++) {
			setElement(characters.item(i));
			Components component = new Components();
			component.setLiteral(getByTagName("literal"));
			component.setComponents(getByTagName("components"));
			components.add(component);
		}

		return components;
	}
}
