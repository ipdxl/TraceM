package com.sf.tracem.db;

public final class OrdersTable {

	/**
	 * Orders table name
	 */
	public final static String TABLE_NAME = "ORDERS";
	/**
	 * Order Number
	 */
	public static final String AUFNR = "AUFNR";

	/**
	 * Order Type
	 */
	public static final String AUFART = "AUFART";

	/**
	 * Basic start date
	 */
	public static final String CO_GSTRP = "CO_GSTRP";

	/**
	 * Description
	 */
	public static final String AUFTEXT = "AUFTEXT";

	/**
	 * Partner name
	 * 
	 * @see PartnerTable#PARTNER
	 */
	public static final String PARTNER = "PARTNER";

	/**
	 * Partner address
	 */
	public static final String ADDRESS = "ADDRESS";

	/**
	 * Order status
	 */
	public static final String ORDER_STATUS = "ORDER_STATUS";

	/**
	 * Expiration days
	 */
	public static final String EXP_DAYS = "EXP_DAYS";

	/**
	 * Expiration status
	 */
	public static final String EXP_STATUS = "EXP_STATUS";

	/**
	 * Order status
	 */
	public static final String ZHOURS = "ZHOURS";

	/**
	 * Asigned program status
	 */
	public static final String ASSIGNED_STATUS = "ASSIGNED_STATUS";

//	/**
//	 * Id program which this order corresponds
//	 * 
//	 * @see ProgramTable#ID_Program
//	 */
//	public final static String ID_PROGRAM = ScheduleTable.ID_PROGRAM;

	/**
	 * Creation order table statement
	 */
	public final static String CREATE_TABLE = "CREATE TABLE ORDERS ("
			+ "AUFNR 		TEXT PRIMARY KEY"
//			+ ", ID_PROGRAM	TEXT REFERENCES SCHEDULE(ID_PROGRAM) "
//			+ " ON UPDATE CASCADE ON DELETE NO ACTION" 
			+ ", AUFART		TEXT"
			+ ", CO_GSTRP	TEXT" + ", AUFTEXT 	TEXT"
			+ ", PARTNER 	TEXT NO NULL REFERENCES PARTNER (PARTNER)"
			+ ", ADDRESS	TEXT" + ", ORDERS_STATUS 	TEXT"
			+ ", EXP_DAYS 		INTEGER" + ", EXP_STATUS 	TEXT" + ", ZHOURS 		TEXT"
			+ ", ASSIGNED_STATUS INTEGER" + ", ORDER_STATUS INTEGER" + ")";

	/**
	 * Column names array ordered alphabetically
	 */
	public final static String[] COLUMN_NAMES = new String[] { 
		ADDRESS
		, ASSIGNED_STATUS
		, AUFART
		, AUFNR
		, AUFTEXT
		, CO_GSTRP
		, EXP_DAYS
		, EXP_STATUS
//		, ID_PROGRAM
		, ORDER_STATUS
		, PARTNER
		, ZHOURS };
}