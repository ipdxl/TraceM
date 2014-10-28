package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Partner implements Serializable {

	private String partn_role;
	private String rol_text;
	private String partner;
	private String name;
	private String address;
	private double latitude, longitude;

	public final static String TABLE_NAME = "PARTNER";

	/**
	 * Partner Function
	 */
	public final static String PARTN_ROLE = "PARTN_ROLE";
	/**
	 * Partner
	 */
	public final static String PARTNER = "PARTNER";
	/**
	 * Description
	 */
	public final static String ROL_TEXT = "ROL_TEXT";
	/**
	 * Name 1
	 */
	public final static String NAME = "NAME";
	/**
	 * Partner address
	 */
	public final static String ADDRESS = "ADDRESS";
	/**
	 * Latitude
	 * 
	 * @see #LONGITUDE
	 */
	public final static String LATITUDE = "LATITUDE";
	/**
	 * Longitude
	 * 
	 * @see #LATITUDE
	 */
	public final static String LONGITUDE = "LONGITUDE";
	
	public final static String[] COLUMN_NAMES = new String[]{
		PARTNER
		,PARTN_ROLE
		,ROL_TEXT
		,NAME
		,ADDRESS
		,LATITUDE
		,LONGITUDE
	};

	public final static String CREATE_TABLE = "CREATE TABLE PARTNER (\n"
			+ "	  PARTNER 		TEXT PRIMARY KEY\n" 
			+ "	, PARTN_ROLE	TEXT\n"
			+ "	, ROL_TEXT 		TEXT\n" 
			+ "	, NAME			TEXT\n"
			+ "	, ADDRESS		TEXT\n" 
			+ "	, LATITUDE		TEXT NOT NULL DEFAULT '0'\n"
			+ "	, LONGITUDE		TEXT NOT NULL DEFAULT '0'\n" 
			+ ");";

	public String getPARTN_ROLE() {
		return partn_role;
	}

	public void setPARTN_ROLE(String pARTN_ROLE) {
		partn_role = pARTN_ROLE;
	}

	public String getPARTNER() {
		return partner;
	}

	public void setPARTNER(String pARTNER) {
		partner = pARTNER;
	}

	public String getNAME() {
		return name;
	}

	public void setNAME(String nAME) {
		name = nAME;
	}

	public String getADDRESS() {
		return address;
	}

	public void setADDRESS(String aDDRESS) {
		address = aDDRESS;
	}

	public String getROL_TEXT() {
		return rol_text;
	}

	public void setROL_TEXT(String rOL_TEXT) {
		rol_text = rOL_TEXT;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
