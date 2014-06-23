package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZEORDER implements Serializable {

	private String ADDRESS;
	private String AUFNR;
	private String AUFART;
	private String CO_GSTRP;
	private String AUFTEXT;
	private String PARTNER;
	private int ORDER_STATUS;
	private String EXP_DAYS;
	private String EXP_STATUS;
	private String ZHOURS;
	private int ASSIGNED_STATUS;
	private String ID_PROGRAM;

	/**
	 * @return the pARTNER
	 */
	public String getPARTNER() {
		return PARTNER;
	}

	/**
	 * @param pARTNER
	 *            the pARTNER to set
	 */
	public void setPARTNER(String pARTNER) {
		PARTNER = pARTNER;
	}

	/**
	 * @return the oRDER_STATUS
	 */
	public int getORDER_STATUS() {
		return ORDER_STATUS;
	}

	/**
	 * @param oRDER_STATUS
	 *            the oRDER_STATUS to set
	 */
	public void setORDER_STATUS(int oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}

	/**
	 * @return the eXP_DAYS
	 */
	public String getEXP_DAYS() {
		return EXP_DAYS;
	}

	/**
	 * @param eXP_DAYS
	 *            the eXP_DAYS to set
	 */
	public void setEXP_DAYS(String eXP_DAYS) {
		EXP_DAYS = eXP_DAYS;
	}

	/**
	 * @return the eXP_STATUS
	 */
	public String getEXP_STATUS() {
		return EXP_STATUS;
	}

	/**
	 * @param eXP_STATUS
	 *            the eXP_STATUS to set
	 */
	public void setEXP_STATUS(String eXP_STATUS) {
		EXP_STATUS = eXP_STATUS;
	}

	/**
	 * @return the zHOURS
	 */
	public String getZHOURS() {
		return ZHOURS;
	}

	/**
	 * @param zHOURS
	 *            the zHOURS to set
	 */
	public void setZHOURS(String zHOURS) {
		ZHOURS = zHOURS;
	}

	/**
	 * @return the aSSIGNED_STATUS
	 */
	public int getASSIGNED_STATUS() {
		return ASSIGNED_STATUS;
	}

	/**
	 * @param i
	 *            the aSSIGNED_STATUS to set
	 */
	public void setASSIGNED_STATUS(int ASSIGNED_STATUS) {
		this.ASSIGNED_STATUS = ASSIGNED_STATUS;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getAUFNR() {
		return AUFNR;
	}

	public void setAUFNR(String aUFNR) {
		AUFNR = "" + Integer.parseInt(aUFNR);
	}

	public String getAUFART() {
		return AUFART;
	}

	public void setAUFART(String aUFART) {
		AUFART = aUFART;
	}

	public String getCO_GSTRP() {
		return CO_GSTRP;
	}

	public void setCO_GSTRP(String cO_GSTRP) {
		CO_GSTRP = cO_GSTRP;
	}

	public String getAUFTEXT() {
		return AUFTEXT;
	}

	public void setAUFTEXT(String aUFTEXT) {
		AUFTEXT = aUFTEXT;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return ID_PROGRAM;
	}

	/**
	 * @param iD_PROGRAM the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String iD_PROGRAM) {
		ID_PROGRAM = iD_PROGRAM;
	}

}
