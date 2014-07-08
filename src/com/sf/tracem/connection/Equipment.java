package com.sf.tracem.connection;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Equipment implements Serializable {

	private String aufnr;
	private String equnr;
	private String eqktxt;
	private int complete;
	private List<MeasurementPoint> measures;

	/**
	 * Equipment number
	 */
	public final static String TABLE_NAME = "EQUIPMENT";
	/**
	 * Equipment number
	 */
	public final static String EQUNR = "EQUNR";
	/**
	 * Order number
	 * 
	 * @see Order#AUFNR
	 */
	public final static String AUFNR = Order.AUFNR;
	/**
	 * Description of technical object
	 */
	public final static String EQTXT = "EQTXT";
	/**
 * 
 */
	public final static String COMPLETE = "COMPLETE";

	/**
	 * CREATE TABLE Equipment
	 */
	public final static String CREATE_TABLE = "CREATE TABLE EQUIPMENT("
			+ "EQUNR TEXT"
			+ ", AUFNR TEXT REFERENCES ORDERS(AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ ", EQTXT TEXT" + ", COMPLETE INTEGER NOT NULL DEFAULT 0"
			+ ", PRIMARY KEY (EQUNR,AUFNR)"
			+ ", CHECK(COMPLETE = 0 OR COMPLETE = 1)" + ");";

	public static final String[] COLUMN_NAMES = new String[] { AUFNR, COMPLETE,
			EQUNR, EQTXT };

	public String getEQUNR() {
		return equnr;
	}

	public void setEQUNR(String eQUNR) {
		equnr = eQUNR;
	}

	public String getEQKTX() {
		return eqktxt;
	}

	public void setEQKTX(String eQKTX) {
		eqktxt = eQKTX;
	}

	/**
	 * @return the aufnr
	 */
	public String getAufnr() {
		return aufnr;
	}

	/**
	 * @param aufnr
	 *            the aufnr to set
	 */
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	/**
	 * @return the complete
	 */
	public int getComplete() {
		return complete;
	}

	/**
	 * @param complete
	 *            the complete to set
	 */
	public void setComplete(int complete) {
		this.complete = complete;
	}

	/**
	 * @return the measures
	 */
	public List<MeasurementPoint> getMeasures() {
		return measures;
	}

	/**
	 * @param measures
	 *            the measures to set
	 */
	public void setMeasures(List<MeasurementPoint> measures) {
		this.measures = measures;
	}
}
