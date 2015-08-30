package net.fauxpark.phoebe.model;

/**
 * A model of a kanji's components.
 *
 * @author fauxpark
 */
public class Components {
	/**
	 * The kanji literal in UTF-8 encoding.
	 */
	private String literal;

	/**
	 * The list of components in UTF-8 encoding.
	 */
	private String components;

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}
}
