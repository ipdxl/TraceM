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

	private String CREATE_DATE;
	private String ID_PROGRAM;
	private String STATUS;

	/**
	 * @return the dATE
	 */
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return ID_PROGRAM;
	}

	/**
	 * @return the sTATUS
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param createDate
	 *            the dATE to set
	 */
	public void setCREATE_DATE(String createDate) {
		CREATE_DATE = createDate;
	}

	/**
	 * @param iD_PROGRAM
	 *            the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String iD_PROGRAM) {
		ID_PROGRAM = iD_PROGRAM;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	@Override
	public String toString() {
		return ID_PROGRAM;
	}
}
