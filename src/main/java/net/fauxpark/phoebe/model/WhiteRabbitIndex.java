package net.fauxpark.phoebe.model;

/**
 * A model of a White Rabbit Japan kanji poster/flashcards index.
 *
 * @author fauxpark
 */
public class WhiteRabbitIndex {
	/**
	 * The kanji literal in UTF-16 encoding.
	 *
	 * Example: "𠮟"
	 */
	private String literal;

	/**
	 * The index of the kanji from 1 to 1945, or N1 to N196 if it is part of the New Jōyō kanji introduced in 2010.
	 */
	private String index;

	/**
	 * The version of the kanji from JIS X 0208 in UTF-16 encoding, where it differs from the official character.
	 *
	 * Example: "叱"
	 */
	private String variant;

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}
}
