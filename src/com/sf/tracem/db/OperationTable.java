package com.sf.tracem.db;

public class OperationTable {
	/**
	 * Operation table name
	 */
	public final static String TABLE_NAME = "OPERATION";
	/**
	 * Order number
	 * 
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = OrdersTable.AUFNR;
	/**
	 * Operation/Activity Number
	 */
	public final static String ACTIVITY = "ACTIVITY";
	/**
	 * Work center
	 */
	public final static String WORK_CNTR = "WORK_CNTR";
	/**
	 * Operation short text
	 */
	public final static String DESCRIPTION = "DESCRIPTION";
	/**
	 * Plant
	 */
	public final static String PLANT = "PLANT";
	/**
	 * Normal duration of the activity
	 */
	public final static String DURATION_NORMAL = "DURATION_NORMAL";
	/**
	 * Normal duration/unit
	 */
	public final static String DURATION_NORMAL_UNIT = "DURATION_NORMAL_UNIT";
	/**
	 * Indicator: No Remaining Work Expected
	 */
	public final static String COMPLETE = "COMPLETE";
	/**
	 * Create operation table statement
	 */
	public final static String CREATE_TABLE = "CREATE TABLE OPERATION ("
			+ "ACTIVITY		INTEGER PRIMARY KEY"
			+ ", AUFNR 		TEXT REFERENCES ORDERS (AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ ", WORK_CNTR 	INTEGER"
			+ ", DESCRIPTION 	TEXT"
			// +", CONF_NO 		TEXT"
			+ ", PLANT 		INTEGER"
			+ ", DURATION_NORMAL 			REAL"
			+ ", DURATION_NORMAL_UNIT 		TEXT"
			// +", DURATION_NORMAL_UNIT_ISO 	TEXT"
			+ ", COMPLETE 					INTEGER"
			+ ");"
			+ "CREATE TRIGGER update_order_status"
			+ "AFTER UPDATE OF COMPLETE  ON OPERATION"
			+ "BEGIN"
			+ "	IF NOT EXISTS (SELECT COMPLETE FROM OPERATION WHERE COMPLETE = 0)"
			+ "	BEGIN"
			+ "		UPDATE ORDERS SET COMPLETE = 1 WHERE AUFNR = NEW.AUFNR"
			+ "	END" + "END;";
}
