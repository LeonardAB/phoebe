# phoebe
Phoebe generates SQLite databases from Japanese dictionary files, primarily for use in Android applications.

It was initially created to automate generation of databases for a planned Japanese dictionary app, but it can be adapted to fit your own needs as well.

Phoebe is built using Maven, makes use of the [xerial/sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) library, and runs on Java 8.

## Sources
Phoebe generates its databases from several freely available dictionary files:

 - [KANJIDIC2](http://www.edrdg.org/kanjidic/kanjd2index.html)
 - [JMDict](http://www.edrdg.org/jmdict/edict_doc.html) (coming soon)
 - [JMNedict](http://www.edrdg.org/enamdict/enamdict_doc.html) (hopefully)

A few other data sources are also merged into the kanji database:

 - Kanji components derived from an XML version of [KRADFILE and KRADFILE2](http://www.csse.monash.edu.au/~jwb/kradinf.html)
 - A listing of the [214 kanji radicals](https://en.wikipedia.org/wiki/Table_of_Japanese_kanji_radicals) and their meanings and variants in a separate `radicals` table
 - Indexes for the [White Rabbit Japan](http://whiterabbitjapan.com/) Kanji flashcard series and poster

Stroke order data from [KanjiVG](https://github.com/KanjiVG/kanjivg) may also be added in future, depending on how well Android can be made to render SVG paths from strings.
