/**
 * 
 */
package com.sf.tracem.db;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class ScheduleTable {

	/**
	 * Program Id
	 */
	public static final String ID_PROGRAM = "ID_PROGRAM";
	/**
	 * Schedule status
	 */
	public static final String STATUS = "STATUS";
	/**
	 * Schedule status
	 */
	public static final String CREATE_DATE = "CREATE_DATE";
	/**
	 * Schedule table name
	 */
	public static final String TABLE_NAME = "SCHEDULE";
	
	/**
	 * Column names ordered alphabetically
	 */
	public static final String[] COLUMN_NAMES = new String[]{
		CREATE_DATE
		,ID_PROGRAM
		,STATUS
	};

	public static final String CREATE_TABLE = 
			"CREATE TABLE SCHEDULE("
			+ "ID_PROGRAM INTEGER PRIMARY KEY" 
			+ ", CREATE_DATE TEXT"
			+ ", STATUS TEXT" 
			+ ")";

}
