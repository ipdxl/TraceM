package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZEOBJECT_ORDER implements Serializable {

	private String EQUNR;
	private String EQKTX;

	public String getEQUNR() {
		return EQUNR;
	}

	public void setEQUNR(String eQUNR) {
		EQUNR = eQUNR;
	}

	public String getEQKTX() {
		return EQKTX;
	}

	public void setEQKTX(String eQKTX) {
		EQKTX = eQKTX;
	}
}
