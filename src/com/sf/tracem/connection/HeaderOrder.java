package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HeaderOrder implements Serializable {
	private String mn_wkctr_id;
	private String equipment;
	private String plangroup;
	private String notif_no;
	private String aufnr;

	/**
	 * Order Number Reference
	 * 
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = Operation.AUFNR;

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
	public final static String CREATE_TABLE = "CREATE TABLE "
			+ TABLE_NAME
			+ " ("
			+ AUFNR
			+ " TEXT PRIMARY KEY REFERENCES ORDERS (AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ ", " + MN_WKCTR_ID + " TEXT" + ", " + EQUIPMENT + " TEXT" + ", "
			+ PLANGROUP + " TEXT" + ", " + NOTIF_NO + " TEXT" + ")";

	public String getMN_WKCTR_ID() {
		return mn_wkctr_id;
	}

	public void setMN_WKCTR_ID(String mN_WKCTR_ID) {
		mn_wkctr_id = mN_WKCTR_ID;
	}

	public String getEQUIPMENT() {
		return equipment;
	}

	public void setEQUIPMENT(String eQUIPMENT) {
		equipment = eQUIPMENT;
	}

	public String getPLANGROUP() {
		return plangroup;
	}

	public void setPLANGROUP(String pLANGROUP) {
		plangroup = pLANGROUP;
	}

	public void setNOTIF_NO(String nOTIF_NO) {
		this.notif_no = nOTIF_NO;
	}

	/**
	 * @return the notif_no
	 */
	public String getNOTIF_NO() {
		return notif_no;
	}

	public String getAUFNR() {
		return aufnr;
	}

	public void setAUFNR(String aufnr) {
		this.aufnr = aufnr;
	}
}
