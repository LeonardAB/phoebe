package net.fauxpark.phoebe.model;

import java.util.List;
import java.util.Set;

/**
 * A model of a JMDict entry.
 *
 * @author fauxpark
 */
public class Word {
	/**
	 * The element ID from JMDict.
	 */
	private Integer id;

	/**
	 * The word, using kanji, and any alternate forms.
	 */
	private List<String> kanji;

	/**
	 * The kana readings of the word.
	 */
	private List<String> readings;

	/**
	 * The English meanings of the word.
	 */
	private List<String> meanings;

	/**
	 * The Part-of-Speech elements of the word.
	 * This should be a LinkedHashSet to avoid duplicate entries.
	 */
	private Set<Integer> pos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getKanji() {
		return kanji;
	}

	public void setKanji(List<String> kanji) {
		this.kanji = kanji;
	}

	public List<String> getReadings() {
		return readings;
	}

	public void setReadings(List<String> readings) {
		this.readings = readings;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
	}

	public Set<Integer> getPos() {
		return pos;
	}

	public void setPos(Set<Integer> pos) {
		this.pos = pos;
	}
}
