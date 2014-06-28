/**
 * 
 */
package com.sf.tracem.connection;

/**
 * @author Lupe
 * 
 */
public class OrderSchedule {

	private String aufnr;
	private String id_program;

	public final static String AUFNR = Operation.AUFNR;

	public final static String ID_PROGRAM = Schedule.ID_PROGRAM;

	public final static String TABLE_NAME = "ORDERS_SCHEDULE";

	public final static String[] COLUMN_NAMES = new String[] { AUFNR,
			ID_PROGRAM };

	public final static String CREATE_TABLE = "CREATE TABLE ORDERS_SCHEDULE("
			+ "AUFNR TEXT REFERENCES ORDERS(AUFNR) ON UPDATE CASCADE ON DELETE CASCADE"
			+ ", ID_PROGRAM INTEGER REFERENCES SCHEDULE(ID_PROGRAM) ON UPDATE CASCADE ON DELETE CASCADE"
			+ ", PRIMARY KEY (AUFNR,ID_PROGRAM)" + ")";

	/**
	 * @return the aUFNR
	 */
	public String getAUFNR() {
		return aufnr;
	}

	/**
	 * @param aUFNR
	 *            the aUFNR to set
	 */
	public void setAUFNR(String aUFNR) {
		aufnr = aUFNR;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return id_program;
	}

	/**
	 * @param id
	 *            the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String id) {
		id_program = id;
	}

}
