package net.fauxpark.phoebe.openhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A base database open helper.
 *
 * @author fauxpark
 */
public abstract class BaseDbOpenHelper {
	/**
	 * The database connection.
	 */
	protected Connection connection = null;

	/**
	 * BaseDbOpenHelper constructor.
	 *
	 * @param fileName The file name of the database to open (or create if it is nonexistent).
	 */
	public BaseDbOpenHelper(String fileName) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
			System.out.println("Opened DB");
		} catch(SQLException e) {
			System.err.println("Couldn't open the DB. Details below.");
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			System.err.println("Couldn't find the SQLite JDBC driver. Make sure it's in your classpath!");
		}
	}

	/**
	 * Get the database connection.
	 *
	 * @return A {@link Connection} object.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Close the database connection.
	 */
	public void close() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Closed DB");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
