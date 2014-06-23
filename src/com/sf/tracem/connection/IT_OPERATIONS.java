package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IT_OPERATIONS implements Serializable {

	String ACTIVITY, WORK_CNTR, DESCRIPTION, CONF_NO, PLANT, DURATION_NORMAL,
			DURATION_NORMAL_UNIT, DURATION_NORMAL_UNIT_ISO, COMPLETE;

	public IT_OPERATIONS() {
	}

	public IT_OPERATIONS(String ACTIVITY, String WORK_CNTR, String DESCRIPTION,
			String CONF_NO, String PLANT, String DURATION_NORMAL,
			String DURATION_NORMAL_UNIT, String DURATION_NORMAL_UNIT_ISO,
			String COMPLETE) {
		this.ACTIVITY = ACTIVITY;
		this.WORK_CNTR = WORK_CNTR;
		this.DESCRIPTION = DESCRIPTION;
		this.CONF_NO = CONF_NO;
		this.PLANT = PLANT;
		this.DURATION_NORMAL = DURATION_NORMAL;
		this.DURATION_NORMAL_UNIT = DURATION_NORMAL_UNIT;
		this.DURATION_NORMAL_UNIT_ISO = DURATION_NORMAL_UNIT_ISO;
		this.COMPLETE = COMPLETE;
	}

	public String getACTIVITY() {
		return ACTIVITY;
	}

	public void setACTIVITY(String ACTIVITY) {
		this.ACTIVITY = ACTIVITY;
	}

	public String getWORK_CNTR() {
		return WORK_CNTR;
	}

	public void setWORK_CNTR(String WORK_CNTR) {
		this.WORK_CNTR = WORK_CNTR;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String DESCRIPTION) {
		this.DESCRIPTION = DESCRIPTION;
	}

	public String getCONF_NO() {
		return CONF_NO;
	}

	public void setCONF_NO(String CONF_NO) {
		this.CONF_NO = CONF_NO;
	}

	public String getPLANT() {
		return PLANT;
	}

	public void setPLANT(String PLANT) {
		this.PLANT = PLANT;
	}

	public String getDURATION_NORMAL() {
		return DURATION_NORMAL;
	}

	public void setDURATION_NORMAL(String DURATION_NORMAL) {
		this.DURATION_NORMAL = DURATION_NORMAL;
	}

	public String getDURATION_NORMAL_UNIT() {
		return DURATION_NORMAL_UNIT;
	}

	public void setDURATION_NORMAL_UNIT(String DURATION_NORMAL_UNIT) {
		this.DURATION_NORMAL_UNIT = DURATION_NORMAL_UNIT;
	}

	public String getDURATION_NORMAL_UNIT_ISO() {
		return DURATION_NORMAL_UNIT_ISO;
	}

	public void setDURATION_NORMAL_UNIT_ISO(String DURATION_NORMAL_UNIT_ISO) {
		this.DURATION_NORMAL_UNIT_ISO = DURATION_NORMAL_UNIT_ISO;
	}

	public String getCOMPLETE() {
		return COMPLETE;
	}

	public void setCOMPLETE(String COMPLETE) {
		this.COMPLETE = COMPLETE;
	}
}
