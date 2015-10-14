# phoebe
Phoebe is a tool for generating SQLite databases from Japanese dictionary files, primarily for use in Android applications. The aim of phoebe is to create databases for these apps that can be easily updated by simply downloading the new dictionary files and running the progam.

## Part Of Speech
To save space, some information about words such as whether it is a noun, verb, etc. (the "parts of speech") are specially coded. However, it is easy to decode this information for use in Android and even perform searches on it. See [this document](https://github.com/fauxpark/phoebe/blob/master/PARTOFSPEECH.md) for more information.

## Sources
Phoebe generates its databases from two freely available dictionary files:

 - [KANJIDIC2](http://www.edrdg.org/kanjidic/kanjd2index.html)
 - [JMDict](http://www.edrdg.org/jmdict/edict_doc.html)

A few other data sources are also merged into the kanji database:

 - Kanji components derived from an XML version of [KRADFILE and KRADFILE2](http://www.csse.monash.edu.au/~jwb/kradinf.html)
 - Indexes for the [White Rabbit Japan](http://whiterabbitjapan.com/) Kanji flashcard series and poster
 - A listing of the [214 kanji radicals](https://en.wikipedia.org/wiki/Table_of_Japanese_kanji_radicals) and their meanings and variants in a separate `radicals` table

These sources can be found in the [phoebe-dictionaries](https://github.com/fauxpark/phoebe-dictionaries) repository.

Stroke order data from [KanjiVG](https://github.com/KanjiVG/kanjivg) may also be added in future, depending on how well Android can be made to render SVG paths from strings.
Japanese names from the [JMNedict](http://www.edrdg.org/enamdict/enamdict_doc.html) project is also planned, but not set in stone.

## Building
Phoebe is built using Maven, makes use of the [xerial/sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) library, and runs on Java 8.
