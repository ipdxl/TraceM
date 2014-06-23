package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZECOMPONENTS_ORDER implements Serializable{
	private String MATERIAL;
	private String ACTIVITY;

	public String getMATERIAL() {
		return MATERIAL;
	}

	public void setMATERIAL(String mATERIAL) {
		MATERIAL = mATERIAL;
	}

	public String getACTIVITY() {
		return ACTIVITY;
	}

	public void setACTIVITY(String aCTIVITY) {
		ACTIVITY = aCTIVITY;
	}

	public String getMATL_DESC() {
		return MATL_DESC;
	}

	public void setMATL_DESC(String mATL_DESC) {
		MATL_DESC = mATL_DESC;
	}

	public String getREQUIREMENT_QUANTITY() {
		return REQUIREMENT_QUANTITY;
	}

	public void setREQUIREMENT_QUANTITY(String rEQUIREMENT_QUANTITY) {
		REQUIREMENT_QUANTITY = rEQUIREMENT_QUANTITY;
	}

	public String getREQUIREMENT_QUANTITY_UNIT() {
		return REQUIREMENT_QUANTITY_UNIT;
	}

	public void setREQUIREMENT_QUANTITY_UNIT(String rEQUIREMENT_QUANTITY_UNIT) {
		REQUIREMENT_QUANTITY_UNIT = rEQUIREMENT_QUANTITY_UNIT;
	}

	private String MATL_DESC;
	private String REQUIREMENT_QUANTITY;
	private String REQUIREMENT_QUANTITY_UNIT;
}
