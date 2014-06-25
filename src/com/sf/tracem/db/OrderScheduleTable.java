/**
 * 
 */
package com.sf.tracem.db;

/**
 * @author José Guadalupe Mandujano
 * 
 */
public class OrderScheduleTable {
	
	public final static String AUFNR = OrdersTable.AUFNR;

	public final static String ID_PROGRAM = ScheduleTable.ID_PROGRAM;	

	public final static String TABLE_NAME =  "ORDERS_SCHEDULE";
	
	public final static String[] COLUMN_NAMES = new String[] {AUFNR,ID_PROGRAM};
	
	public final static String CREATE_TABLE = 
			"CREATE TABLE ORDERS_SCHEDULE(" +
			"AUFNR TEXT REFERENCES ORDERS(AUFNR) ON UPDATE CASCADE ON DELETE CASCADE" +
			", ID_PROGRAM INTEGER REFERENCES SCHEDULE(ID_PROGRAM) ON UPDATE CASCADE ON DELETE CASCADE" +
			", PRIMARY KEY (AUFNR,ID_PROGRAM)" +
			")";
}
