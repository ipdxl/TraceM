package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Equipment implements Serializable {

	private String equnr;
	private String eqktxt;

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
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = "AUFNR";
	/**
	 * Description of technical object
	 */
	public final static String EQTXT = "EQTXT";
	/**
	 * CREATE TABLE Equipment
	 */
	public final static String CREATE_TABLE = 
			"CREATE TABLE EQUIPMENT("
			+ "EQUNR TEXT PRIMARY KEY"
			+ ", AUFNR TEXT REFERENCES ORDERS(AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ ", EQTXT TEXT" + ");";

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
}
