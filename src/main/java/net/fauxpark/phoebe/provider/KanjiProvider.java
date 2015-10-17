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
			"name TEXT NOT NULL, reading TEXT NOT NULL, stroke_count INTEGER NOT NULL, variants TEXT)";

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
	 * Add kanji records into the database.
	 *
	 * @param kanjis The list of kanji to insert.
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
				if(log.isDebugEnabled()) {
					log.debug("Inserting kanji: {}", kanji.getLiteral());
				}

				// We use setBytes here and in other places to force
				// writing surrogate pairs as a single UTF-8 character
				pStatement.setBytes(1, kanji.getLiteral().getBytes());
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
					pStatement.setString(11, iterableToString(kanji.getOnyomi()));
				} else {
					pStatement.setNull(11, Types.VARCHAR);
				}

				if(kanji.getKunyomi().size() > 0) {
					pStatement.setString(12, iterableToString(kanji.getKunyomi()));
				} else {
					pStatement.setNull(12, Types.VARCHAR);
				}

				if(kanji.getNanori().size() > 0) {
					pStatement.setString(13, iterableToString(kanji.getNanori()));
				} else {
					pStatement.setNull(13, Types.VARCHAR);
				}

				if(kanji.getMeanings().size() > 0) {
					pStatement.setString(14, iterableToString(kanji.getMeanings()));
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
	 * Merge kanji components into the kanji table.
	 *
	 * @param components The list of components to insert.
	 */
	public void addComponents(List<Components> components) {
		String update = "UPDATE kanji SET components = ? WHERE literal = ?";

		try {
			log.info("Inserting kanji components");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(Components component : components) {
				if(log.isDebugEnabled()) {
					log.debug("Inserting components for kanji: {}", component.getLiteral());
				}

				pStatement.setString(1, component.getComponents());
				pStatement.setBytes(2, component.getLiteral().getBytes());
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
	 * Add kanji radicals into the database.
	 *
	 * @param radicals The list of radicals to insert.
	 */
	public void addRadicals(List<Radical> radicals) {
		String update = "INSERT INTO radicals (literal, name, reading, stroke_count, variants) VALUES (?, ?, ?, ?, ?)";

		try {
			log.info("Inserting kanji radicals");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(Radical radical : radicals) {
				if(log.isDebugEnabled()) {
					log.debug("Inserting radical: {}", radical.getLiteral());
				}

				pStatement.setString(1, radical.getLiteral());
				pStatement.setString(2, radical.getName());
				pStatement.setString(3, radical.getReading());
				pStatement.setInt(4, radical.getStrokeCount());

				if(radical.getVariants() != null) {
					pStatement.setBytes(5, radical.getVariants().getBytes());
				} else {
					pStatement.setNull(5, Types.VARCHAR);
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
	 * Merge White Rabbit indexes into the kanji table.
	 *
	 * @param indexes The list of indexes to insert.
	 */
	public void addWhiteRabbitIndexes(List<WhiteRabbitIndex> indexes) {
		String update = "UPDATE kanji SET white_rabbit = ? WHERE literal = ? OR literal = ?";

		try {
			log.info("Inserting White Rabbit indexes");

			PreparedStatement pStatement = getConnection().prepareStatement(update);
			getConnection().setAutoCommit(false);

			for(WhiteRabbitIndex index : indexes) {
				if(log.isDebugEnabled()) {
					log.debug("Inserting White Rabbit index for kanji: {}", index.getLiteral());
				}

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
}
