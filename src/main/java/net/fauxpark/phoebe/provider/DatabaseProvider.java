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

	/**
	 * Surround each entry in an Iterable with square brackets, and return it as a String.
	 *
	 * @param iterable The Iterable object to convert.
	 * @return A string containing the elements of the iterable, enclosed in square brackets.
	 */
	protected <T> String iterableToString(Iterable<T> iterable) {
		String retVal = "";

		for(T item : iterable) {
			retVal += "[" + item.toString() + "]";
		}

		return retVal;
	}
}
