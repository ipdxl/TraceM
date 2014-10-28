package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {

	private String address;
	private String aufnr;
	private String aufart;
	private String coGstrp;
	private String auftext;
	private String partner;
	private int orderStatus;
	private String expDays;
	private String expStatus;
	private float zHours;

	/**
	 * Orders table name
	 */
	public final static String TABLE_NAME = "ORDERS";

	/**
	 * Order Number
	 */
	public static final String AUFNR = "AUFNR";

	/**
	 * Order Type
	 */
	public static final String AUFART = "AUFART";

	/**
	 * Basic start date
	 */
	public static final String CO_GSTRP = "CO_GSTRP";

	/**
	 * Description
	 */
	public static final String AUFTEXT = "AUFTEXT";

	/**
	 * Partner name
	 * 
	 * @see Partner#PARTNER
	 */
	public static final String PARTNER = "PARTNER";

	/**
	 * Partner address
	 */
	public static final String ADDRESS = "ADDRESS";

	/**
	 * Order status
	 */
	public static final String ORDER_STATUS = "ORDER_STATUS";

	/**
	 * Expiration days
	 */
	public static final String EXP_DAYS = "EXP_DAYS";

	/**
	 * Expiration status
	 */
	public static final String EXP_STATUS = "EXP_STATUS";

	/**
	 * Order status
	 */
	public static final String ZHOURS = "ZHOURS";

	// /**
	// * Asigned program status
	// */
	// public static final String ASSIGNED_STATUS = "ASSIGNED_STATUS";

	// /**
	// * Id program which this order corresponds
	// *
	// * @see ProgramTable#ID_Program
	// */
	// public final static String ID_PROGRAM = ScheduleTable.ID_PROGRAM;

	/**
	 * Creation order table statement
	 */
	public final static String CREATE_TABLE = "CREATE TABLE ORDERS ("
			+ "AUFNR 		TEXT PRIMARY KEY"
			// + ", ID_PROGRAM	TEXT REFERENCES SCHEDULE(ID_PROGRAM) "
			// + " ON UPDATE CASCADE ON DELETE NO ACTION"
			+ ", AUFART		TEXT" 
			+ ", CO_GSTRP	TEXT" 
			+ ", AUFTEXT 	TEXT"
			+ ", PARTNER 	TEXT NOT NULL REFERENCES PARTNER (PARTNER)"
			+ ", ADDRESS	TEXT" 
			+ ", ORDERS_STATUS 	TEXT"
			+ ", EXP_DAYS 		INTEGER" 
			+ ", EXP_STATUS 	TEXT" 
			+ ", ZHOURS 		TEXT"
			+ ", ORDER_STATUS INTEGER" 
			+ ")";

	/**
	 * Column names array ordered alphabetically
	 */
	public final static String[] COLUMN_NAMES = new String[] { ADDRESS
			// , ASSIGNED_STATUS
			, AUFART, AUFNR, AUFTEXT, CO_GSTRP, EXP_DAYS, EXP_STATUS
			// , ID_PROGRAM
			, ORDER_STATUS, PARTNER, ZHOURS };

	/**
	 * @return the pARTNER
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the pARTNER to set
	 */
	public void setPARTNER(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the oRDER_STATUS
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param os
	 *            the oRDER_STATUS to set
	 */
	public void setOrderStatus(int os) {
		orderStatus = os;
	}

	/**
	 * @return the eXP_DAYS
	 */
	public String getExpDays() {
		return expDays;
	}

	/**
	 * @param ed
	 *            the eXP_DAYS to set
	 */
	public void setExpDays(String ed) {
		expDays = ed;
	}

	/**
	 * @return the eXP_STATUS
	 */
	public String getExpStatus() {
		return expStatus;
	}

	/**
	 * @param es
	 *            the eXP_STATUS to set
	 */
	public void setExpStatus(String es) {
		expStatus = es;
	}

	/**
	 * @return the ZHours
	 */
	public float getZHhours() {
		return zHours;
	}

	/**
	 * @param zH
	 *            the zHOURS to set
	 */
	public void setZHhours(float zH) {
		zHours = zH;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String aDDRESS) {
		address = aDDRESS;
	}

	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = "" + Integer.parseInt(aufnr);
	}

	public String getAufart() {
		return aufart;
	}

	public void setAufart(String aufart) {
		this.aufart = aufart;
	}

	public String getCoGstrp() {
		return coGstrp;
	}

	public void setCoGstrp(String coGstrp) {
		this.coGstrp = coGstrp;
	}

	public String getAuftext() {
		return auftext;
	}

	public void setAuftext(String auftext) {
		this.auftext = auftext;
	}

}
