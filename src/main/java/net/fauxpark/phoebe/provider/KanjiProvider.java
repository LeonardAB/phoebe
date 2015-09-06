package net.fauxpark.phoebe.provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.helper.KanjiDatabaseHelper;
import net.fauxpark.phoebe.model.Kanji;
import net.fauxpark.phoebe.model.Radical;
import net.fauxpark.phoebe.model.WhiteRabbitIndex;
import net.fauxpark.phoebe.model.Components;

/**
 * A kanji oriented provider.
 *
 * @author fauxpark
 */
public class KanjiProvider extends DatabaseProvider {
	private static final Logger log = LogManager.getLogger(KanjiProvider.class);
	/**
	 * KanjiProvider constructor.
	 *
	 * @param fileName The file name of the database to pass to the open helper.
	 */
	public KanjiProvider(String fileName) {
		super(KanjiDatabaseHelper.getInstance(fileName));
	}

	/**
	 * Perform the initial table creation.
	 */
	public void createTables() {
		String schemaKanji = "CREATE TABLE IF NOT EXISTS kanji (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, literal TEXT UNIQUE NOT NULL, " +
			"codepoint TEXT NOT NULL, radical INTEGER NOT NULL, grade INTEGER, stroke_count INTEGER NOT NULL, " +
			"frequency INTEGER, jlpt INTEGER, heisig INTEGER, white_rabbit TEXT, skip TEXT, four_corner TEXT, " +
			"onyomi TEXT, kunyomi TEXT, nanori TEXT, meanings TEXT, components TEXT)";
		String schemaRadicals = "CREATE TABLE IF NOT EXISTS radicals (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, literal TEXT NOT NULL, " +
			"name TEXT NOT NULL, stroke_count INTEGER NOT NULL, variants TEXT)";

		try {
			log.info("Creating kanji database tables");

			Statement statement = getConnection().createStatement();
			statement.executeUpdate(schemaKanji);
			statement.executeUpdate(schemaRadicals);
			statement.close();
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Add kanji records from a list of {@link Kanji}.
	 *
	 * @param kanjis The list of kanji to insert into the database.
	 */
	public void addKanji(List<Kanji> kanjis) {
		String insert = "INSERT INTO kanji (" +
			"literal, codepoint, radical, grade, stroke_count, frequency, jlpt, " +
			"heisig, skip, four_corner, onyomi, kunyomi, nanori, meanings" +
			") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			log.info("Inserting kanji records");

			getConnection().setAutoCommit(false);
			PreparedStatement pStatement = getConnection().prepareStatement(insert);

			for(Kanji kanji : kanjis) {
				log.debug("Inserting kanji: " + kanji.getLiteral());

				pStatement.setString(1, kanji.getLiteral());
				pStatement.setString(2, kanji.getCodepoint());
				pStatement.setInt(3, kanji.getRadical());

				if(kanji.getGrade() != null) {
					pStatement.setInt(4, kanji.getGrade());
				} else {
					pStatement.setNull(4, Types.INTEGER);
				}

				pStatement.setInt(5, kanji.getStrokeCount());

				if(kanji.getFrequency() != null) {
					pStatement.setInt(6, kanji.getFrequency());
				} else {
					pStatement.setNull(6, Types.INTEGER);
				}

				if(kanji.getJlpt() != null) {
					pStatement.setInt(7, kanji.getJlpt());
				} else {
					pStatement.setNull(7, Types.INTEGER);
				}

				if(kanji.getHeisig() != null) {
					pStatement.setInt(8, kanji.getHeisig());
				} else {
					pStatement.setNull(8, Types.INTEGER);
				}

				pStatement.setString(9, kanji.getSkip());
				pStatement.setString(10, kanji.getFourCorner());

				if(kanji.getOnyomi().size() > 0) {
					pStatement.setString(11, listToString(kanji.getOnyomi()));
				} else {
					pStatement.setNull(11, Types.VARCHAR);
				}

				if(kanji.getKunyomi().size() > 0) {
					pStatement.setString(12, listToString(kanji.getKunyomi()));
				} else {
					pStatement.setNull(12, Types.VARCHAR);
				}

				if(kanji.getNanori().size() > 0) {
					pStatement.setString(13, listToString(kanji.getNanori()));
				} else {
					pStatement.setNull(13, Types.VARCHAR);
				}

				if(kanji.getMeanings().size() > 0) {
					pStatement.setString(14, listToString(kanji.getMeanings()));
				} else {
					pStatement.setNull(14, Types.VARCHAR);
				}

				pStatement.addBatch();
			}

			pStatement.executeBatch();
			getConnection().commit();
			pStatement.close();
			getConnection().setAutoCommit(true);
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Merge kanji components from a list of {@link Components} into the kanji database.
	 *
	 * @param components The list of components to insert into the database.
	 */
	public void addComponents(List<Components> components) {
		String update = "UPDATE kanji SET components = ? WHERE literal = ?";

		try {
			log.info("Inserting kanji components");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(Components component : components) {
				log.debug("Inserting components for kanji: " + component.getLiteral());

				pStatement.setString(1, component.getComponents());
				pStatement.setString(2, component.getLiteral());
				pStatement.addBatch();
			}

			pStatement.executeBatch();
			getConnection().commit();
			pStatement.close();
			getConnection().setAutoCommit(true);
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Add kanji radicals from a list of {@link Radical}s.
	 *
	 * @param radicals The list of radicals to insert into the database.
	 */
	public void addRadicals(List<Radical> radicals) {
		String update = "INSERT INTO radicals (literal, name, stroke_count, variants) VALUES (?, ?, ?, ?)";

		try {
			log.info("Inserting kanji radicals");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(Radical radical : radicals) {
				log.debug("Inserting radical: " + radical.getLiteral());

				pStatement.setString(1, radical.getLiteral());
				pStatement.setString(2, radical.getName());
				pStatement.setInt(3, radical.getStrokeCount());

				if(radical.getVariants() != null) {
					pStatement.setString(4, radical.getVariants());
				} else {
					pStatement.setNull(4, Types.VARCHAR);
				}

				pStatement.addBatch();
			}

			pStatement.executeBatch();
			getConnection().commit();
			pStatement.close();
			getConnection().setAutoCommit(true);
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Merge White Rabbit indexes from a list of {@link WhiteRabbitIndex} into the kanji database.
	 *
	 * @param indexes The list of indexes to insert into the database.
	 */
	public void addWhiteRabbitIndexes(List<WhiteRabbitIndex> indexes) {
		String update = "UPDATE kanji SET white_rabbit = ? WHERE literal = ? OR literal = ?";

		try {
			log.info("Inserting White Rabbit indexes");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(WhiteRabbitIndex index : indexes) {
				log.debug("Inserting White Rabbit index for kanji: " + index.getLiteral());

				pStatement.setString(1, index.getIndex());
				pStatement.setString(2, index.getLiteral());
				pStatement.setString(3, index.getVariant());
				pStatement.addBatch();
			}

			pStatement.executeBatch();
			getConnection().commit();
			pStatement.close();
			getConnection().setAutoCommit(true);
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Test method. Retrieve the literal of the first kanji in the database.
	 *
	 * @return 亜
	 */
	public String getFirstKanji() {
		String query = "SELECT literal FROM kanji WHERE id = 1 LIMIT 1";
		String result = null;

		try {
			Statement statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			if(resultSet.next()) {
				result = resultSet.getString(1);
				resultSet.close();
				statement.close();

				return result;
			}
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}

		return null;
	}

	/**
	 * Format the contents of a List<String> into a usable string.
	 *
	 * @param list The list to convert.
	 * @return A string containing the elements of the list, enclosed in square brackets.
	 */
	private String listToString(List<String> list) {
		String retVal = "";

		for(String item : list) {
			retVal += "[" + item + "]";
		}

		return retVal;
	}
}
