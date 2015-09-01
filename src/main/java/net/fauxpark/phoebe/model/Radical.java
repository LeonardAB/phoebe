package net.fauxpark.phoebe.model;

/**
 * A model of a KangXi radical.
 *
 * @author fauxpark
 */
public class Radical {
	/**
	 * The radical literal in UTF-8 encoding.
	 */
	private String literal;

	/**
	 * A comma-separated list of names for the radical, in English.
	 */
	private String name;

	/**
	 * The stroke count of the radical.
	 */
	private Integer strokeCount;

	/**
	 * A list of variants on the radical in UTF-8 encoding.
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
