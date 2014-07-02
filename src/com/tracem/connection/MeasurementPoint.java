/**
 * 
 */
package com.tracem.connection;

import java.io.Serializable;

import com.sf.tracem.connection.Equipment;

/**
 * @author USER-7
 * 
 */
@SuppressWarnings("serial")
public class MeasurementPoint implements Serializable {

	public final static String TABLE_NAME = "MEASUREMENT_POINT";
	public final static String EQUNR = Equipment.EQUNR;
	public final static String POINT = "POINT";
	public final static String READ = "READ";
	public final static String UNIT = "UNIT";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String NOTES = "NOTES";
	public final static String[] COLUMN_NAMES = new String[] { 
		EQUNR
		, POINT
		, READ
		, UNIT
		, DESCRIPTION
		, NOTES };

	public final static String CREATE_TABLE = "CREATE TABLE MEASUREMENT_POINT("
			+ "	EQUNR TEXT REFERENCES EQUIPMENT(EQUNR) ON UPDATE CASCADE ON DELETE CASCADE"
			+ ", POINT TEXT" 
			+ ", READ REAL" 
			+ ", UNIT TEXT"
			+ ", DESCRIPTION TEXT" 
			+ ", NOTES TEXT"
			+ ", PRIMARY KEY (EQUNR,POINT)" 
			+ ");";

	private String equnr;
	private String point;
	private String description;
	private String unit;
	private String notes;
	private double read;

	/**
	 * @return the equnr
	 */
	public String getEqunr() {
		return equnr;
	}

	/**
	 * @param equnr
	 *            the equnr to set
	 */
	public void setEqunr(String equnr) {
		this.equnr = equnr;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the read
	 */
	public double getRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(double read) {
		this.read = read;
	}

	/**
	 * @return the point
	 */
	public String getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(String point) {
		this.point = point;
	}

}