package com.sf.tracem.db;

public final class MenuTable {
	/**
	 * Menu table name
	 */
	public final static String TABLE_NAME = "MENU";
	/**
	 * Id menu column
	 */
	public final static String ID_MENU = "ID_MENU";
	/**
	 * id father column
	 */
	public final static String ID_FATHER = "ID_FATHER";

	/**
	 * Creation menu table statement
	 * 
	 * @see #CREATE_TABLE
	 */
	public final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + ID_MENU + " INTEGER PRIMARY KEY" + ", " + ID_FATHER
			+ " INTEGER REFERENCES " + TABLE_NAME + "(" + ID_MENU
			+ ")" + ")";
}
