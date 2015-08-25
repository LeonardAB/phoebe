package net.fauxpark.phoebe.dao;

import java.sql.Connection;

import net.fauxpark.phoebe.openhelper.BaseDbOpenHelper;

/**
 * A base data source.
 *
 * @author fauxpark
 */
public abstract class BaseDataSource {
	/**
	 * The database connection.
	 */
	protected Connection connection;

	/**
	 * The database open helper.
	 */
	protected BaseDbOpenHelper dbOpenHelper;

	/**
	 * Close the database open helper.
	 */
	public void close() {
		dbOpenHelper.close();
	}
}
