package net.fauxpark.phoebe.provider;

import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Set;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.helper.JishoDatabaseHelper;
import net.fauxpark.phoebe.model.PartOfSpeech;
import net.fauxpark.phoebe.model.Word;

/**
 * A JMDict oriented provider.
 *
 * @author fauxpark
 */
public class JishoProvider extends DatabaseProvider {
	private static final Logger log = LogManager.getLogger(JishoProvider.class);

	/**
	 * JishoProvider constructor.
	 *
	 * @param fileName The file name of the database to pass to the open helper.
	 */
	public JishoProvider(String fileName) {
		super(JishoDatabaseHelper.getInstance(fileName));
	}

	/**
	 * Perform the initial table creation.
	 */
	public void createTables() {
		String schemaJisho = "CREATE TABLE IF NOT EXISTS jisho (" +
		"id INTEGER PRIMARY KEY, kanji TEXT, readings TEXT NOT NULL, " +
		"meanings TEXT NOT NULL, pos BLOB)";

		try {
			log.info("Creating Jisho database table");

			Statement statement = getConnection().createStatement();
			statement.executeUpdate(schemaJisho);
			statement.close();
		} catch(SQLException e) {
			log.error("SQL Exception occurred.", e);
		}
	}

	/**
	 * Add dictionary entries into the database.
	 *
	 * @param words The list of words to insert.
	 */
	public void addWords(List<Word> words) {
		String insert = "INSERT INTO jisho (id, kanji, readings, meanings, pos) VALUES (?, ?, ?, ?, ?)";

		try {
			log.info("Inserting word records");

			getConnection().setAutoCommit(false);
			PreparedStatement pStatement = getConnection().prepareStatement(insert);

			for(Word word : words) {
				log.debug("Inserting word: " + word.getKanji());

				pStatement.setInt(1, word.getId());

				if(word.getKanji().size() > 0) {
					pStatement.setString(2, iterableToString(word.getKanji()));
				} else {
					pStatement.setNull(2, Types.VARCHAR);
				}

				pStatement.setString(3, iterableToString(word.getReadings()));
				pStatement.setString(4, iterableToString(word.getMeanings()));
				pStatement.setBytes(5, posToByteArray(word.getPos()));
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
	 * Test method. Retrieve the kanji for "please" by searching for its reading.
	 *
	 * @return [お願いします][御願いします]
	 */
	public String getWordByReading() {
		String query = "SELECT kanji FROM jisho WHERE readings = \"[おねがいします]\" LIMIT 1";
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
	 * Another test method. Retrieve the kanji for the first I-adjective in the database.
	 *
	 * @return [悪どい]
	 */
	public String getWordByPOS() {
		String query = "SELECT kanji FROM jisho WHERE instr(pos, x'" + PartOfSpeech.ADJECTIVE_I + "') LIMIT 1";
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
	 * Convert the set of POS element opcodes to a byte array
	 * to be inserted into the database.
	 *
	 * @param set The set of POS elements.
	 * @return An array of POS opcodes.
	 */
	private byte[] posToByteArray(Set<Integer> set) {
		byte[] retVal = new byte[set.size()];
		int i = 0;

		for(Integer item : set) {
			retVal[i] = item.byteValue();
			i++;
		}

		return retVal;
	}
}
