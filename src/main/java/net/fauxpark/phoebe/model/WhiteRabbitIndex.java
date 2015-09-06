package net.fauxpark.phoebe.model;

/**
 * A model of a White Rabbit Japan kanji poster/flashcards index.
 *
 * @author fauxpark
 */
public class WhiteRabbitIndex {
	/**
	 * The kanji literal in UTF-8 encoding.
	 */
	private String literal;

	/**
	 * The index of the kanji from 1 to 1945, or N1 to N196 if it is part of the New Jōyō kanji (2010).
	 */
	private String index;

	/**
	 * The JIS X 0208 version of the kanji in UTF-8 encoding.
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
