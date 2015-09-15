package net.fauxpark.phoebe.model;

/**
 * An enum for mapping Part-of-Speech elements to "opcodes".
 * <br/>
 * Of note here is the use of the full name of the entities,
 * instead of the entities themselves (eg. "&adj-i;"). This is because
 * the DOM parser does not allow element text to be retrieved without
 * resolving entity references first. Thus, the resolved text must be
 * used to compare instead.
 *
 * @author fauxpark
 */
public enum PartOfSpeech {
	NOUN(0x00, "noun (common) (futsuumeishi)"),
	NOUN_ADVERB(0x01, "adverbial noun (fukushitekimeishi)"),
	NOUN_PROPER(0x02, "proper noun"),
	NOUN_TEMPORAL(0x03, "noun (temporal) (jisoumeishi)"),
	NOUN_PREFIX(0x04, "noun, used as a prefix"),
	NOUN_SUFFIX(0x05, "noun, used as a suffix"),
	PRONOUN(0x07, "pronoun"),
	////////////////
	ADVERB(0x08, "adverb (fukushi)"),
	ADVERB_TO(0x09, "adverb taking the `to' particle"),
	////////////////
	ADJECTIVE_I(0x10, "adjective (keiyoushi)"),
	ADJECTIVE_IX(0x11, "adjective (keiyoushi) - yoi/ii class"),
	ADJECTIVE_F(0x12, "noun or verb acting prenominally"),
	ADJECTIVE_NA(0x13, "adjectival nouns or quasi-adjectives (keiyodoshi)"),
	ADJECTIVE_NO(0x14, "nouns which may take the genitive case particle `no'"),
	ADJECTIVE_KU(0x15, "`ku' adjective (archaic)"),
	ADJECTIVE_NARI(0x16, "archaic/formal form of na-adjective"),
	ADJECTIVE_KARI(0x17, "`kari' adjective (archaic)"),
	ADJECTIVE_TARU(0x18, "`taru' adjective"),
	ADJECTIVE_SHIKU(0x19, "`shiku' adjective (archaic)"),
	ADJECTIVE_PRENOUN(0x1A, "pre-noun adjectival (rentaishi)"),
	////////////////
	VERB_TRANSITIVE(0x1C, "transitive verb"),
	VERB_INTRANSITIVE(0x1D, "intransitive verb"),
	VERB_UNSPECIFIED(0x1E, "verb unspecified"),
	////////////////
	VERB_NIDAN_U(0x20, "Nidan verb with 'u' ending (archaic)"), //move me
	VERB_NIDAN_BU_UPPER(0x21, "Nidan verb (upper class) with `bu' ending (archaic)"),
	VERB_NIDAN_BU_LOWER(0x22, "Nidan verb (lower class) with `bu' ending (archaic)"),
	VERB_NIDAN_DZU_UPPER(0x23, "Nidan verb (upper class) with `dzu' ending (archaic)"),
	VERB_NIDAN_DZU_LOWER(0x24, "Nidan verb (lower class) with `dzu' ending (archaic)"),
	VERB_NIDAN_FU_UPPER(0x25, "Nidan verb (upper class) with `hu/fu' ending (archaic)"),
	VERB_NIDAN_FU_LOWER(0x26, "Nidan verb (lower class) with `hu/fu' ending (archaic)"),
	VERB_NIDAN_GU_UPPER(0x27, "Nidan verb (upper class) with `gu' ending (archaic)"),
	VERB_NIDAN_GU_LOWER(0x28, "Nidan verb (lower class) with `gu' ending (archaic)"),
	VERB_NIDAN_KU_UPPER(0x29, "Nidan verb (upper class) with `ku' ending (archaic)"),
	VERB_NIDAN_KU_LOWER(0x2A, "Nidan verb (lower class) with `ku' ending (archaic)"),
	VERB_NIDAN_MU_UPPER(0x2B, "Nidan verb (upper class) with `mu' ending (archaic)"),
	VERB_NIDAN_MU_LOWER(0x2C, "Nidan verb (lower class) with `mu' ending (archaic)"),
	VERB_NIDAN_NU_LOWER(0x2D, "Nidan verb (lower class) with `nu' ending (archaic)"),
	VERB_NIDAN_RU_UPPER(0x2E, "Nidan verb (upper class) with `ru' ending (archaic)"),
	VERB_NIDAN_RU_LOWER(0x2F, "Nidan verb (lower class) with `ru' ending (archaic)"),
	VERB_NIDAN_TSU_UPPER(0x30, "Nidan verb (upper class) with `tsu' ending (archaic)"),
	VERB_NIDAN_TSU_LOWER(0x31, "Nidan verb (lower class) with `tsu' ending (archaic)"),
	VERB_NIDAN_YU_UPPER(0x32, "Nidan verb (upper class) with `yu' ending (archaic)"),
	VERB_NIDAN_YU_LOWER(0x33, "Nidan verb (lower class) with `yu' ending (archaic)"),
	VERB_NIDAN_U_LOWER(0x34, "Nidan verb (lower class) with `u' ending and `we' conjugation (archaic)"),
	VERB_NIDAN_SU_LOWER(0x35, "Nidan verb (lower class) with `su' ending (archaic)"),
	VERB_NIDAN_ZU_LOWER(0x36, "Nidan verb (lower class) with `zu' ending (archaic)"),
	////////////////
	VERB_SURU(0x48, "noun or participle which takes the aux. verb suru"),
	VERB_SURU_IRREGULAR(0x49, "suru verb - irregular"),
	VERB_SURU_SPECIAL(0x4A, "suru verb - special class"),
	VERB_KURU(0x4B, "Kuru verb - special class"),
	////////////////
	VERB_NU(0x4C, "irregular nu verb"),
	VERB_RU(0x4D, "irregular ru verb, plain form ends with -ri"),
	VERB_SU(0x4E, "su verb - precursor to the modern suru"),
	////////////////
	VERB_GODAN_ARU(0x50, "Godan verb - -aru special class"),
	VERB_GODAN_BU(0x51, "Godan verb with `bu' ending"),
	VERB_GODAN_GU(0x52, "Godan verb with `gu' ending"),
	VERB_GODAN_IKU(0x53, "Godan verb - Iku/Yuku special class"),
	VERB_GODAN_KU(0x54, "Godan verb with `ku' ending"),
	VERB_GODAN_MU(0x55, "Godan verb with `mu' ending"),
	VERB_GODAN_NU(0x56, "Godan verb with `nu' ending"),
	VERB_GODAN_RU(0x57, "Godan verb with `ru' ending"),
	VERB_GODAN_RU_IRREGULAR(0x58, "Godan verb with `ru' ending (irregular verb)"),
	VERB_GODAN_SU(0x59, "Godan verb with `su' ending"),
	VERB_GODAN_TSU(0x5A, "Godan verb with `tsu' ending"),
	VERB_GODAN_U(0x5B, "Godan verb with `u' ending"),
	VERB_GODAN_U_SPECIAL(0x5C, "Godan verb with `u' ending (special class)"),
	VERB_GODAN_URU(0x5D, "Godan verb - Uru old class verb (old form of Eru)"),
	////////////////
	VERB_YODAN_BU(0x60, "Yodan verb with `bu' ending (archaic)"),
	VERB_YODAN_FU(0x61, "Yodan verb with `hu/fu' ending (archaic)"),
	VERB_YODAN_GU(0x62, "Yodan verb with `gu' ending (archaic)"),
	VERB_YODAN_KU(0x63, "Yodan verb with `ku' ending (archaic)"),
	VERB_YODAN_MU(0x64, "Yodan verb with `mu' ending (archaic)"),
	VERB_YODAN_NU(0x65, "Yodan verb with `nu' ending (archaic)"),
	VERB_YODAN_RU(0x66, "Yodan verb with `ru' ending (archaic)"),
	VERB_YODAN_SU(0x67, "Yodan verb with `su' ending (archaic)"),
	VERB_YODAN_TSU(0x68, "Yodan verb with `tsu' ending (archaic)"),
	////////////////
	VERB_ICHIDAN(0x70, "Ichidan verb"),
	VERB_ICHIDAN_SPECIAL(0x71, "Ichidan verb - kureru special class"),
	VERB_ICHIDAN_ZURU(0x72, "Ichidan verb - zuru verb (alternative form of -jiru verbs)"),
	////////////////
	AUXILIARY(0x74, "auxiliary"),
	AUXILIARY_ADJECTIVE(0x75, "auxiliary adjective"),
	AUXILIARY_VERB(0x76, "auxiliary verb"),
	////////////////
	NUMERIC(0x78, "numeric"),
	PARTICLE(0x79, "particle"),
	INTERJECTION(0x7A, "interjection (kandoushi)"),
	CONJUNCTION(0x7B, "conjunction"),
	COUNTER(0x7C, "counter"),
	COPULA(0x7D, "copula"),
	////////////////
	TERM_ANATOMY(0x80, "anatomical term"),
	TERM_ARCHITECTURE(0x81, "architecture term"),
	TERM_ASTRONOMY(0x82, "astronomy, etc. term"),
	TERM_BASEBALL(0x83, "baseball term"),
	TERM_BIOLOGY(0x84, "biology term"),
	TERM_BOTANY(0x85, "botany term"),
	TERM_BUDDHISM(0x86, "Buddhist term"),
	TERM_BUSINESS(0x87, "business term"),
	TERM_CHEMISTRY(0x88, "chemistry term"),
	TERM_COMPUTER(0x89, "computer terminology"),
	TERM_ECONOMICS(0x8A, "economics term"),
	TERM_ENGINEERING(0x8B, "engineering term"),
	TERM_FINANCE(0x8C, "finance term"),
	TERM_FOOD(0x8D, "food term"),
	TERM_GEOLOGY(0x8E, "geology, etc. term"),
	TERM_GEOMETRY(0x8F, "geometry term"),
	TERM_HUMOUR(0x90, "jocular, humorous term"),
	TERM_LAW(0x91, "law, etc. term"),
	TERM_LINGUISTICS(0x92, "linguistics terminology"),
	TERM_MAHJONG(0x93, "mahjong term"),
	TERM_MARTIAL_ARTS(0x94, "martial arts term"),
	TERM_MATHEMATICS(0x95, "mathematics"),
	TERM_MEDICINE(0x96, "medicine, etc. term"),
	TERM_MILITARY(0x97, "military"),
	TERM_MUSIC(0x98, "music term"),
	TERM_PHYSICS(0x99, "physics terminology"),
	TERM_POETRY(0x9A, "poetical term"),
	TERM_SHINTO(0x9B, "Shinto term"),
	TERM_SHOGI(0x9C, "shogi term"),
	TERM_SPORTS(0x9D, "sports term"),
	TERM_SUMO(0x9E, "sumo term"),
	TERM_XXX(0x9F, "rude or X-rated term (not displayed in educational software)"),
	TERM_ZOOLOGY(0xA0, "zoology term"),
	////////////////
	MALE(0xA8, "male term or language"),
	FEMALE(0xA9, "female term or language"),
	SLANG(0xAA, "slang"),
	SLANG_MALE(0xAB, "male slang"),
	SLANG_MANGA(0xAC, "manga slang"),
	CHILDREN(0xAD, "children's language"),
	////////////////
	DIALECT_HOKKAIDO(0xB0, "Hokkaido-ben"),
	DIALECT_KANSAI(0xB1, "Kansai-ben"),
	DIALECT_KANTO(0xB2, "Kantou-ben"),
	DIALECT_KYOTO(0xB3, "Kyoto-ben"),
	DIALECT_KYUSHU(0xB4, "Kyushu-ben"),
	DIALECT_NAGANO(0xB5, "Nagano-ben"),
	DIALECT_OSAKA(0xB6, "Osaka-ben"),
	DIALECT_RYUKU(0xB7, "Ryuukyuu-ben"),
	DIALECT_TOHOKU(0xB8, "Touhoku-ben"),
	DIALECT_TOSA(0xB9, "Tosa-ben"),
	DIALECT_TSUGARU(0xBA, "Tsugaru-ben"),
	////////////////
	EXPRESSION(0xC0, "expressions (phrases, clauses, etc.)"),
	PROVERB(0xC1, "proverb"),
	COLLOQUIALISM(0xC2, "colloquialism"),
	ABBREVIATION(0xC3, "abbreviation"),
	////////////////
	IRREGULAR_KANA(0xC4, "word containing irregular kana usage"),
	IRREGULAR_KANJI(0xC5, "word containing irregular kanji usage"),
	IRREGULAR_OKURIGANA(0xC6, "irregular okurigana usage"),
	IRREGULAR_VERB(0xC7, "irregular verb"),
	////////////////
	FAMILIAR(0xD0, "familiar language"),
	HUMBLE(0xD1, "humble (kenjougo) language"),
	POLITE(0xD2, "polite (teineigo) language"),
	HONORIFIC(0xD3, "honorific or respectful (sonkeigo) language"),
	SENSITIVE(0xD4, "sensitive"),
	////////////////
	YOJIJUKUGO(0xD8, "yojijukugo"),
	GIKUN(0xD9, "gikun (meaning as reading) or jukujikun (special kanji reading)"),
	ATEJI(0xDA, "ateji (phonetic) reading"),
	////////////////
	DEROGATORY(0xE0, "derogatory"),
	VULGAR(0xE1, "vulgar expression or word"),
	////////////////
	PREFIX(0xE8, "prefix"),
	SUFFIX(0xE9, "suffix"),
	////////////////
	USUALLY_KANA(0xF0, "word usually written using kana alone"),
	USUALLY_KANJI(0xF1, "word usually written using kanji alone"),
	EXCLUSIVELY_KANA(0xF2, "exclusively kana"),
	EXCLUSIVELY_KANJI(0xF3, "exclusively kanji"),
	OUTDATED_KANA(0xF4, "word containing outdated kana"),
	OUTDATED_KANJI(0xF5, "word containing outdated kanji"),
	OLD_KANA(0xF6, "old or irregular kana form"),
	ARCHAISM(0xF7, "archaism"),
	////////////////
	ONOMATOPOEIA(0xF8, "onomatopoeic or mimetic word"),
	IDIOMATIC(0xF9, "idiomatic expression"),
	OBSCURE(0xFC, "obscure term"),
	RARE(0xFD, "rare"),
	OBSOLETE(0xFE, "obsolete term"),
	UNCLASSIFIED(0xFF, "unclassified");

	/**
	 * The POS opcode.
	 */
	private int code;

	/**
	 * The full name of the POS element.
	 */
	private String name;

	/**
	 * PartOfSpeech constructor.
	 *
	 * @param code
	 * @param name
	 */
	private PartOfSpeech(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * Get the POS opcode matching the given name.
	 *
	 * @param name The name of the POS element to get.
	 * @return A POS opcode.
	 */
	public static int getPosCode(String name) {
		for(PartOfSpeech pos : values()) {
			if(pos.name.equals(name)) {
				return pos.code;
			}
		}

		return UNCLASSIFIED.code;
	}
}
