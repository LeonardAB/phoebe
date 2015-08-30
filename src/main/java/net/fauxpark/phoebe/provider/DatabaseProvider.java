package net.fauxpark.phoebe.provider;

import java.sql.Connection;

import net.fauxpark.phoebe.helper.DatabaseHelper;

/**
 * A base database provider.
 *
 * @author fauxpark
 */
public abstract class DatabaseProvider {
	/**
	 * The database helper.
	 */
	private DatabaseHelper dbHelper;

	/**
	 * DatabaseProvider constructor.
	 *
	 * @param dbOpenHelper A database helper.
	 */
	public DatabaseProvider(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	/**
	 * Get the database helper's Connection.
	 *
	 * @return
	 */
	public Connection getConnection() {
		return dbHelper.getConnection();
	}

	/**
	 * Close the database helper.
	 */
	public void close() {
		dbHelper.close();
	}
}
