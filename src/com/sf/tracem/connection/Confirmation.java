package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Confirmation implements Serializable {

	private String activity, un_act_dur, exec_start_date, exec_fin_date,
			exec_start_time, exec_fin_time, conf_text;
	double actual_dur;
	private int complete;

	public final static String ACTIVITY = "ACTIVITY";
	public final static String ACTUAL_DUR = "ACTUAL_DUR";
	public final static String UN_ACT_DUR = "UN_ACT_DUR";
	public final static String EXEC_FIN_DATE = "EXEC_FIN_DATE";
	public final static String EXEC_START_DATE = "EXEC_START_DATE";
	public final static String EXEC_START_TIME = "EXEC_START_TIME";
	public final static String EXEC_FIN_TIME = "EXEC_FIN_TIME";
	public final static String CONF_TEXT = "CONF_TEXT";
	public final static String COMPLETE = "COMPLETE";

	public Confirmation() {
	}

	public String getACTIVITY() {
		return activity;
	}

	public void setACTIVITY(String ACTIVITY) {
		this.activity = ACTIVITY;
	}

	public double getACTUAL_DUR() {
		return actual_dur;
	}

	public void setACTUAL_DUR(double ACTUAL_DUR) {
		this.actual_dur = ACTUAL_DUR;
	}

	public String getUN_ACT_DUR() {
		return un_act_dur;
	}

	public void setUN_ACT_DUR(String UN_ACT_DUR) {
		this.un_act_dur = UN_ACT_DUR;
	}

	public String getEXEC_START_DATE() {
		return exec_start_date;
	}

	public void setEXEC_START_DATE(String EXEC_START_DATE) {
		this.exec_start_date = EXEC_START_DATE;
	}

	public String getEXEC_FIN_DATE() {
		return exec_fin_date;
	}

	public void setEXEC_FIN_DATE(String EXEC_FIN_DATE) {
		this.exec_fin_date = EXEC_FIN_DATE;
	}

	public String getEXEC_START_TIME() {
		return exec_start_time;
	}

	public void setEXEC_START_TIME(String EXEC_START_TIME) {
		this.exec_start_time = EXEC_START_TIME;
	}

	public String getEXEC_FIN_TIME() {
		return exec_fin_time;
	}

	public void setEXEC_FIN_TIME(String EXEC_FIN_TIME) {
		this.exec_fin_time = EXEC_FIN_TIME;
	}

	public String getCONF_TEXT() {
		return conf_text;
	}

	public void setCONF_TEXT(String CONF_TEXT) {
		this.conf_text = CONF_TEXT;
	}

	public int getCOMPLETE() {
		return complete;
	}

	public void setCOMPLETE(int COMPLETE) {
		this.complete = COMPLETE;
	}

}
