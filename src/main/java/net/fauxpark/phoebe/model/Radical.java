package net.fauxpark.phoebe.model;

/**
 * A model of a KangXi radical.
 *
 * @author fauxpark
 */
public class Radical {
	/**
	 * The radical literal in UTF-16 encoding.
	 *
	 * Example: "刀"
	 */
	private String literal;

	/**
	 * A comma-separated list of names for the radical, in English.
	 *
	 * Example: "knife,sword"
	 */
	private String name;

	/**
	 * The Japanese name of the radical in hiragana.
	 *
	 * Example: "かたな"
	 */
	private String reading;

	/**
	 * The stroke count of the radical, from 1 to 17.
	 */
	private Integer strokeCount;

	/**
	 * A list of variants on the radical in UTF-16 encoding. There are no spaces between each character.
	 *
	 * Example: "刂⺈"
	 */
	private String variants;

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public Integer getStrokeCount() {
		return strokeCount;
	}

	public void setStrokeCount(Integer strokeCount) {
		this.strokeCount = strokeCount;
	}

	public String getVariants() {
		return variants;
	}

	public void setVariants(String variants) {
		this.variants = variants;
	}
}
