package net.fauxpark.phoebe.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A base database helper.
 *
 * @author fauxpark
 */
public abstract class DatabaseHelper {
	/**
	 * The database connection.
	 */
	private Connection connection = null;

	private static Logger log = LogManager.getLogger(DatabaseHelper.class);

	/**
	 * DatabaseHelper constructor.
	 *
	 * @param fileName The file name of the database to open (or create if it is nonexistent).
	 */
	public DatabaseHelper(String fileName) {
		try {
			log.info("Opening database: " + fileName);

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
		} catch(SQLException e) {
			log.error("Could not open the database.", e);
		} catch(ClassNotFoundException e) {
			log.error("Could not locate the SQLite JDBC driver in the classpath.");
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
				log.info("Closing database");

				connection.close();
			} catch(SQLException e) {
				log.error("Could not close the database.", e);
			}
		}
	}
}
