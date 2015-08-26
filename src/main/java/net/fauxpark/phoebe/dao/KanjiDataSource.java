package net.fauxpark.phoebe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import net.fauxpark.phoebe.model.Kanji;
import net.fauxpark.phoebe.model.Radical;
import net.fauxpark.phoebe.openhelper.KanjiDbOpenHelper;

/**
 * A kanji oriented data source.
 *
 * @author fauxpark
 */
public class KanjiDataSource extends BaseDataSource {
	/**
	 * Create a new kanji data source.
	 *
	 * @param fileName The file name of the database to pass to the open helper.
	 */
	public KanjiDataSource(String fileName) {
		dbOpenHelper = KanjiDbOpenHelper.getOpenHelper(fileName);
		connection = dbOpenHelper.getConnection();
	}

	/**
	 * Perform the initial table creation.
	 */
	public void createTable() {
		String schema = "CREATE TABLE IF NOT EXISTS kanji (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, literal TEXT UNIQUE NOT NULL, " +
			"codepoint TEXT NOT NULL, radical INTEGER NOT NULL, grade INTEGER, " +
			"stroke_count INTEGER NOT NULL, frequency INTEGER, jlpt INTEGER, heisig INTEGER, " +
			"skip TEXT, four_corner TEXT, onyomi TEXT, kunyomi TEXT, nanori TEXT, meanings TEXT, " +
			"components TEXT)";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(schema);
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a single kanji to the database.
	 *
	 * @param kanji The kanji to add.
	 */
	public void addKanji(Kanji kanji) {
		String insert = "INSERT INTO kanji (" +
			"literal, codepoint, radical, grade, stroke_count, frequency, jlpt, " +
			"heisig, skip, four_corner, onyomi, kunyomi, nanori, meanings" +
			") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pStatement = connection.prepareStatement(insert);

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
			pStatement.setString(11, kanji.getOnyomi().toString());
			pStatement.setString(12, kanji.getKunyomi().toString());
			pStatement.setString(13, kanji.getNanori().toString());
			pStatement.setString(14, kanji.getMeanings().toString());

			pStatement.executeUpdate();
		} catch(SQLException e) {
			if(e.getErrorCode() == 19) {
				System.out.println("Kanji " + kanji.getLiteral() + " already exists in DB, skipping");
			} else {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add kanji records from a list of {@link Kanji}.
	 *
	 * @param kanjis The list of kanji to insert into the database.
	 */
	public void addKanji(List<Kanji> kanjis) {
		for(Kanji kanji : kanjis) {
			addKanji(kanji);
		}
	}

	/**
	 * Add kanji radicals to an existing kanji record.
	 *
	 * @param radical The string of radicals to add.
	 */
	public void addRadical(Radical radical) {
		String query = "UPDATE kanji SET components = ? WHERE literal = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(query);
			pStatement.setString(1, radical.getComponents());
			pStatement.setString(2, radical.getLiteral());
			pStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Merge kanji radicals from a list of {@link Radical} into the kanji database.
	 *
	 * @param radicals The list of radicals to insert into the database.
	 */
	public void addRadicals(List<Radical> radicals) {
		for(Radical radical : radicals) {
			addRadical(radical);
		}
	}

	/**
	 * Test method. Retrieve the literal of the first kanji in the database.
	 *
	 * @return 亜
	 */
	public String getFirstKanji() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT literal FROM kanji WHERE id = 1 LIMIT 1");
			statement.close();

			return resultSet.getString(0);
		} catch(SQLException e) {
			e.printStackTrace();

			return null;
		}
	}
}
