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
	 * The database open helper.
	 */
	private BaseDbOpenHelper dbOpenHelper;

	/**
	 * BaseDataSource constructor.
	 *
	 * @param dbOpenHelper A database open helper.
	 */
	public BaseDataSource(BaseDbOpenHelper dbOpenHelper) {
		this.dbOpenHelper = dbOpenHelper;
	}

	/**
	 * Get the database open helper's Connection.
	 *
	 * @return
	 */
	public Connection getConnection() {
		return dbOpenHelper.getConnection();
	}

	/**
	 * Close the database open helper.
	 */
	public void close() {
		dbOpenHelper.close();
	}
}
