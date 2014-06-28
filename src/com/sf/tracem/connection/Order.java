package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {

	private String address;
	private String aufnr;
	private String aufart;
	private String co_gstrp;
	private String auftext;
	private String partner;
	private int order_status;
	private String exp_days;
	private String exp_status;
	private String zhours;

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
			+ ", AUFART		TEXT" + ", CO_GSTRP	TEXT" + ", AUFTEXT 	TEXT"
			+ ", PARTNER 	TEXT NO NULL REFERENCES PARTNER (PARTNER)"
			+ ", ADDRESS	TEXT" + ", ORDERS_STATUS 	TEXT"
			+ ", EXP_DAYS 		INTEGER" + ", EXP_STATUS 	TEXT" + ", ZHOURS 		TEXT"
			+ ", ORDER_STATUS INTEGER" + ")";

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
	public String getPARTNER() {
		return partner;
	}

	/**
	 * @param pARTNER
	 *            the pARTNER to set
	 */
	public void setPARTNER(String pARTNER) {
		partner = pARTNER;
	}

	/**
	 * @return the oRDER_STATUS
	 */
	public int getORDER_STATUS() {
		return order_status;
	}

	/**
	 * @param oRDER_STATUS
	 *            the oRDER_STATUS to set
	 */
	public void setORDER_STATUS(int oRDER_STATUS) {
		order_status = oRDER_STATUS;
	}

	/**
	 * @return the eXP_DAYS
	 */
	public String getEXP_DAYS() {
		return exp_days;
	}

	/**
	 * @param eXP_DAYS
	 *            the eXP_DAYS to set
	 */
	public void setEXP_DAYS(String eXP_DAYS) {
		exp_days = eXP_DAYS;
	}

	/**
	 * @return the eXP_STATUS
	 */
	public String getEXP_STATUS() {
		return exp_status;
	}

	/**
	 * @param eXP_STATUS
	 *            the eXP_STATUS to set
	 */
	public void setEXP_STATUS(String eXP_STATUS) {
		exp_status = eXP_STATUS;
	}

	/**
	 * @return the zHOURS
	 */
	public String getZHOURS() {
		return zhours;
	}

	/**
	 * @param zHOURS
	 *            the zHOURS to set
	 */
	public void setZHOURS(String zHOURS) {
		zhours = zHOURS;
	}

	public String getADDRESS() {
		return address;
	}

	public void setADDRESS(String aDDRESS) {
		address = aDDRESS;
	}

	public String getAUFNR() {
		return aufnr;
	}

	public void setAUFNR(String aUFNR) {
		aufnr = "" + Integer.parseInt(aUFNR);
	}

	public String getAUFART() {
		return aufart;
	}

	public void setAUFART(String aUFART) {
		aufart = aUFART;
	}

	public String getCO_GSTRP() {
		return co_gstrp;
	}

	public void setCO_GSTRP(String cO_GSTRP) {
		co_gstrp = cO_GSTRP;
	}

	public String getAUFTEXT() {
		return auftext;
	}

	public void setAUFTEXT(String aUFTEXT) {
		auftext = aUFTEXT;
	}

	// /**
	// * @return the iD_PROGRAM
	// */
	// public String getID_PROGRAM() {
	// return ID_PROGRAM;
	// }
	//
	// /**
	// * @param iD_PROGRAM the iD_PROGRAM to set
	// */
	// public void setID_PROGRAM(String iD_PROGRAM) {
	// ID_PROGRAM = iD_PROGRAM;
	// }

}
