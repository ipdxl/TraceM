/**
 * 
 */
package com.sf.tracem.connection;

import java.io.Serializable;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
@SuppressWarnings("serial")
public class Schedule implements Serializable {

	private String create_date;
	private String id_program;
	private int status;

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
	public static final String[] COLUMN_NAMES = new String[] { CREATE_DATE,
			ID_PROGRAM, STATUS };

	public static final String CREATE_TABLE = "CREATE TABLE SCHEDULE("
			+ "ID_PROGRAM TEXT PRIMARY KEY" + ", CREATE_DATE TEXT"
			+ ", STATUS INTEGER" + ")";

	public static final String[] TRIGGERS = new String[] { "CREATE TRIGGER update_schedule_status"
			+ " AFTER UPDATE OF STATUS ON SCHEDULE"
			+ " WHEN OLD.STATUS = 2 AND NEW.STATUS = 3"
			+ " BEGIN"
			+ "	UPDATE SCHEDULE SET STATUS = 2" + "	WHERE STATUS = 1;" + "END" };

	/**
	 * @return the dATE
	 */
	public String getCREATE_DATE() {
		return create_date;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return id_program;
	}

	/**
	 * @return the sTATUS
	 */
	public int getSTATUS() {
		return status;
	}

	/**
	 * @param createDate
	 *            the dATE to set
	 */
	public void setCREATE_DATE(String createDate) {
		create_date = createDate;
	}

	/**
	 * @param iD_PROGRAM
	 *            the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String iD_PROGRAM) {
		id_program = iD_PROGRAM;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setSTATUS(int sTATUS) {
		status = sTATUS;
	}

	@Override
	public String toString() {
		return id_program;
	}

	public int getYear() {
		return Integer.parseInt(id_program.substring(0, 4));
	}

	public int getWeek() {
		return Integer.parseInt(id_program.substring(4));
	}
}
