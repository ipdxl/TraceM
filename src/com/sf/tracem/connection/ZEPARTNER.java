package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZEPARTNER implements Serializable{

	private String PARTN_ROLE;
	private String ROL_TEXT;
	private String PARTNER;
	private String NAME;
	private String ADDRESS;

	public String getPARTN_ROLE() {
		return PARTN_ROLE;
	}

	public void setPARTN_ROLE(String pARTN_ROLE) {
		PARTN_ROLE = pARTN_ROLE;
	}

	public String getPARTNER() {
		return PARTNER;
	}

	public void setPARTNER(String pARTNER) {
		PARTNER = pARTNER;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getROL_TEXT() {
		return ROL_TEXT;
	}

	public void setROL_TEXT(String rOL_TEXT) {
		ROL_TEXT = rOL_TEXT;
	}
}
