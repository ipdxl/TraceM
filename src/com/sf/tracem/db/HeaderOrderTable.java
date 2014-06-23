package com.sf.tracem.db;


public class HeaderOrderTable {
	/**
	 * Order Number Reference
	 * 
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = OrdersTable.AUFNR;

	/**
	 * Object ID of the Work Center
	 */
	public final static String MN_WKCTR_ID = "MN_WKCTR_ID";
	/**
	 * Equipment Number
	 */
	public final static String EQUIPMENT = "EQUIPMENT";
	/**
	 * Planner Group for Customer Service and Plant Maintenance
	 */
	public final static String PLANGROUP = "PLANGROUP";
	/**
	 * Notification No
	 */
	public final static String NOTIF_NO = "NOTIF_NO";
	/**
	 * Table name
	 */
	public final static String TABLE_NAME = "header_order";
	
	/**
	 * Creation statement
	 */
	public final static String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME
			+" ("
			+ AUFNR + " TEXT PRIMARY KEY REFERENCES ORDERS (AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ ", "+ MN_WKCTR_ID +" TEXT" 
			+ ", "+ EQUIPMENT +" TEXT" 
			+ ", "+ PLANGROUP +" TEXT"
			+ ", "+ NOTIF_NO +" TEXT" 
			+ ")";
}
