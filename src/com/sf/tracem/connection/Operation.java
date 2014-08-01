package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Operation implements Serializable {

	private String activity, work_cntr, description, conf_no, plant,
			duration_normal_unit, aufnr;
	double duration_normal;
	private int commited;

	private int complete;
	/**
	 * Operation table name
	 */
	public final static String TABLE_NAME = "OPERATION";
	/**
	 * Order number
	 * 
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = Order.AUFNR;
	/**
	 * Operation/Activity Number
	 */
	public final static String ACTIVITY = "ACTIVITY";
	/**
	 * Work center
	 */
	public final static String WORK_CNTR = "WORK_CNTR";
	/**
	 * Operation short text
	 */
	public final static String DESCRIPTION = "DESCRIPTION";
	/**
	 * Plant
	 */
	public final static String PLANT = "PLANT";
	/**
	 * Normal duration of the activity
	 */
	public final static String DURATION_NORMAL = "DURATION_NORMAL";
	/**
	 * Normal duration/unit
	 */
	public final static String DURATION_NORMAL_UNIT = "DURATION_NORMAL_UNIT";
	/**
	 * Indicator: No Remaining Work Expected
	 */
	public final static String COMPLETE = "COMPLETE";
	/**
	 * Create operation table statement
	 */
	public final static String COMMITED = "COMMITED";

	public final static String CREATE_TABLE = "CREATE TABLE OPERATION ("
			+ " ACTIVITY		INTEGER"
			+ " , AUFNR 		TEXT REFERENCES ORDERS (AUFNR) ON DELETE CASCADE ON UPDATE CASCADE"
			+ " , WORK_CNTR 	INTEGER"
			+ " , DESCRIPTION 	TEXT"
			// +", CONF_NO 		TEXT"
			+ " , PLANT 		INTEGER"
			+ " , DURATION_NORMAL 			REAL"
			+ " , DURATION_NORMAL_UNIT 		TEXT"
			// +", DURATION_NORMAL_UNIT_ISO 	TEXT"
			+ " , COMPLETE 					INTEGER"
			+ " , COMMITED INTEGER NOT NULL DEFAULT 0 CHECK (COMMITED = 1 OR COMMITED = 0)"
			+ " , PRIMARY KEY (ACTIVITY,AUFNR)" + " );";

	public final static String[] TRIGGERS = new String[] {
			"CREATE TRIGGER update_order_status"
					+ " AFTER UPDATE OF COMPLETE ON OPERATION"
					+ " WHEN NEW.COMPLETE = 1"
					+ " BEGIN	"
					+ " 	UPDATE ORDERS SET ORDER_STATUS = 1"
					+ " 		WHERE AUFNR = NEW.AUFNR"
					+ " 		AND 0 NOT IN (SELECT COMPLETE FROM OPERATION WHERE AUFNR = NEW.AUFNR);"
					+ " 	UPDATE ORDERS SET ORDER_STATUS = 2"
					+ " 		WHERE AUFNR = NEW.AUFNR"
					+ " 		AND 0  IN (SELECT COMPLETE FROM OPERATION WHERE AUFNR = NEW.AUFNR)"
					+ " 		AND 1  IN (SELECT COMPLETE FROM OPERATION WHERE AUFNR = NEW.AUFNR);"
					+ " END",
			"CREATE TRIGGER insert_operation"
					+ " AFTER INSERT ON OPERATION"
					+ " BEGIN"
					+ " UPDATE OPERATION SET COMMITED = 1 WHERE AUFNR = NEW.AUFNR AND ACTIVITY = NEW.ACTIVITY AND COMPLETE = 1;"
					+ " END" };

	public static final String[] COLUMN_NAMES = new String[] { ACTIVITY, AUFNR,
			COMPLETE, DESCRIPTION, DURATION_NORMAL, DURATION_NORMAL_UNIT,
			PLANT, WORK_CNTR, COMMITED };

	public Operation() {
	}

	public Operation(String activity, String work_cntr, String description,
			String conf_no, String plant, double duration_normal,
			String duration_normal_unit, int complete) {
		this.activity = activity;
		this.work_cntr = work_cntr;
		this.description = description;
		this.conf_no = conf_no;
		this.plant = plant;
		this.duration_normal = duration_normal;
		this.duration_normal_unit = duration_normal_unit;
		this.complete = complete;
	}

	public String getACTIVITY() {
		return activity;
	}

	public void setACTIVITY(String ACTIVITY) {
		this.activity = ACTIVITY;
	}

	public String getWORK_CNTR() {
		return work_cntr;
	}

	public void setWORK_CNTR(String WORK_CNTR) {
		this.work_cntr = WORK_CNTR;
	}

	public String getDESCRIPTION() {
		return description;
	}

	public void setDESCRIPTION(String DESCRIPTION) {
		this.description = DESCRIPTION;
	}

	public String getCONF_NO() {
		return conf_no;
	}

	public void setCONF_NO(String CONF_NO) {
		this.conf_no = CONF_NO;
	}

	public String getPLANT() {
		return plant;
	}

	public void setPLANT(String PLANT) {
		this.plant = PLANT;
	}

	public double getDURATION_NORMAL() {
		return duration_normal;
	}

	public void setDURATION_NORMAL(double DURATION_NORMAL) {
		this.duration_normal = DURATION_NORMAL;
	}

	public String getDURATION_NORMAL_UNIT() {
		return duration_normal_unit;
	}

	public void setDURATION_NORMAL_UNIT(String DURATION_NORMAL_UNIT) {
		this.duration_normal_unit = DURATION_NORMAL_UNIT;
	}

	public int getCOMPLETE() {
		return complete;
	}

	public void setCOMPLETE(int COMPLETE) {
		this.complete = COMPLETE;
	}

	/**
	 * @return the commited
	 */
	public int getCommited() {
		return commited;
	}

	/**
	 * @param commited
	 *            the commited to set
	 */
	public void setCommited(int commited) {
		this.commited = commited;
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
}
