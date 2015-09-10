package net.fauxpark.phoebe.model;

import java.util.List;

/**
 * A model of a kanji.
 *
 * @author fauxpark
 */
public class Kanji {
	/**
	 * The kanji literal in UTF-16 encoding.
	 *
	 * Example: "愛"
	 */
	private String literal;

	/**
	 * The UCS codepoint of the kanji as a string.
	 *
	 * Example: "611b"
	 */
	private String codepoint;

	/**
	 * The KangXi radical number, from 1 to 214.
	 */
	private Integer radical;

	/**
	 * The grade of the kanji, from 1 to 10.
	 */
	private Integer grade;

	/**
	 * The stroke count of the kanji.
	 */
	private Integer strokeCount;

	/**
	 * The frequency ranking of the kanji, from 1 to 2501.
	 */
	private Integer frequency;

	/**
	 * The former JLPT level of the kanji, from 1 to 4.
	 */
	private Integer jlpt;

	/**
	 * The Heisig index of the kanji, from James Heisig's <i>Remembering The Kanji</i>.
	 *
	 * Example: 737
	 */
	private Integer heisig;

	/**
	 * The SKIP (System of Kanji Identification by Patterns) code of the kanji.
	 *
	 * Example: "2-4-9"
	 */
	private String skip;

	/**
	 * The four corner code of the kanji.
	 *
	 * Example: "2024.7"
	 */
	private String fourCorner;

	/**
	 * The list of <i>on'yomi</i> (Chinese-derived) readings of the kanji.
	 */
	private List<String> onyomi;

	/**
	 * The list of <i>kun'yomi</i> (Japanese-derived) readings of the kanji.
	 */
	private List<String> kunyomi;

	/**
	 * The list of <i>nanori</i> (name-only) readings of the kanji.
	 */
	private List<String> nanori;

	/**
	 * The list of meanings (in English only) of the kanji.
	 */
	private List<String> meanings;

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getCodepoint() {
		return codepoint;
	}

	public void setCodepoint(String codepoint) {
		this.codepoint = codepoint;
	}

	public Integer getRadical() {
		return radical;
	}

	public void setRadical(Integer radical) {
		this.radical = radical;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getStrokeCount() {
		return strokeCount;
	}

	public void setStrokeCount(Integer strokeCount) {
		this.strokeCount = strokeCount;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getJlpt() {
		return jlpt;
	}

	public void setJlpt(Integer jlpt) {
		this.jlpt = jlpt;
	}

	public Integer getHeisig() {
		return heisig;
	}

	public void setHeisig(Integer heisig) {
		this.heisig = heisig;
	}

	public String getSkip() {
		return skip;
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}

	public String getFourCorner() {
		return fourCorner;
	}

	public void setFourCorner(String fourCorner) {
		this.fourCorner = fourCorner;
	}

	public List<String> getOnyomi() {
		return onyomi;
	}

	public void setOnyomi(List<String> onyomi) {
		this.onyomi = onyomi;
	}

	public List<String> getKunyomi() {
		return kunyomi;
	}

	public void setKunyomi(List<String> kunyomi) {
		this.kunyomi = kunyomi;
	}

	public List<String> getNanori() {
		return nanori;
	}

	public void setNanori(List<String> nanori) {
		this.nanori = nanori;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
	}
}
