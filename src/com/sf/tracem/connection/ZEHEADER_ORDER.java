package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZEHEADER_ORDER implements Serializable {
	private String MN_WKCTR_ID;
	private String EQUIPMENT;
	private String PLANGROUP;

	public String getMN_WKCTR_ID() {
		return MN_WKCTR_ID;
	}

	public void setMN_WKCTR_ID(String mN_WKCTR_ID) {
		MN_WKCTR_ID = mN_WKCTR_ID;
	}

	public String getEQUIPMENT() {
		return EQUIPMENT;
	}

	public void setEQUIPMENT(String eQUIPMENT) {
		EQUIPMENT = eQUIPMENT;
	}

	public String getPLANGROUP() {
		return PLANGROUP;
	}

	public void setPLANGROUP(String pLANGROUP) {
		PLANGROUP = pLANGROUP;
	}

	public String getNOTIF_NO() {
		return NOTIF_NO;
	}

	public void setNOTIF_NO(String nOTIF_NO) {
		NOTIF_NO = nOTIF_NO;
	}

	private String NOTIF_NO;
}
