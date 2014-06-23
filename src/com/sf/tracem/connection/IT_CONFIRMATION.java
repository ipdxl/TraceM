package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IT_CONFIRMATION implements Serializable{

	String ACTIVITY, ACTUAL_DUR, UN_ACT_DUR, UN_ACT_DUR_ISO, EXEC_START_DATE,
			EXEC_FIN_DATE, EXEC_START_TIME, EXEC_FIN_TIME, CONF_TEXT, COMPLETE;

	public IT_CONFIRMATION() {
	}

	public IT_CONFIRMATION(String ACTIVITY, String ACTUAL_DUR,
			String UN_ACT_DUR, String UN_ACT_DUR_ISO, String EXEC_START_DATE,
			String EXEC_FIN_DATE, String EXEC_START_TIME, String EXEC_FIN_TIME,
			String CONF_TEXT, String COMPLETE) {

		this.ACTIVITY = ACTIVITY;
		this.ACTUAL_DUR = ACTUAL_DUR;
		this.UN_ACT_DUR = UN_ACT_DUR;
		this.UN_ACT_DUR_ISO = UN_ACT_DUR_ISO;
		this.EXEC_START_DATE = EXEC_START_DATE;
		this.EXEC_FIN_DATE = EXEC_FIN_DATE;
		this.EXEC_START_TIME = EXEC_START_TIME;
		this.EXEC_FIN_TIME = EXEC_FIN_TIME;
		this.CONF_TEXT = CONF_TEXT;
		this.COMPLETE = COMPLETE;
	}

	public String getACTIVITY() {
		return ACTIVITY;
	}

	public void setACTIVITY(String ACTIVITY) {
		this.ACTIVITY = ACTIVITY;
	}

	public String getACTUAL_DUR() {
		return ACTUAL_DUR;
	}

	public void setACTUAL_DUR(String ACTUAL_DUR) {
		this.ACTUAL_DUR = ACTUAL_DUR;
	}

	public String getUN_ACT_DUR() {
		return UN_ACT_DUR;
	}

	public void setUN_ACT_DUR(String UN_ACT_DUR) {
		this.UN_ACT_DUR = UN_ACT_DUR;
	}

	public String getUN_ACT_DUR_ISO() {
		return UN_ACT_DUR_ISO;
	}

	public void setUN_ACT_DUR_ISO(String UN_ACT_DUR_ISO) {
		this.UN_ACT_DUR_ISO = UN_ACT_DUR_ISO;
	}

	public String getEXEC_START_DATE() {
		return EXEC_START_DATE;
	}

	public void setEXEC_START_DATE(String EXEC_START_DATE) {
		this.EXEC_START_DATE = EXEC_START_DATE;
	}

	public String getEXEC_FIN_DATE() {
		return EXEC_FIN_DATE;
	}

	public void setEXEC_FIN_DATE(String EXEC_FIN_DATE) {
		this.EXEC_FIN_DATE = EXEC_FIN_DATE;
	}

	public String getEXEC_START_TIME() {
		return EXEC_START_TIME;
	}

	public void setEXEC_START_TIME(String EXEC_START_TIME) {
		this.EXEC_START_TIME = EXEC_START_TIME;
	}

	public String getEXEC_FIN_TIME() {
		return EXEC_FIN_TIME;
	}

	public void setEXEC_FIN_TIME(String EXEC_FIN_TIME) {
		this.EXEC_FIN_TIME = EXEC_FIN_TIME;
	}

	public String getCONF_TEXT() {
		return CONF_TEXT;
	}

	public void setCONF_TEXT(String CONF_TEXT) {
		this.CONF_TEXT = CONF_TEXT;
	}

	public String getCOMPLETE() {
		return COMPLETE;
	}

	public void setCOMPLETE(String COMPLETE) {
		this.COMPLETE = COMPLETE;
	}

}
