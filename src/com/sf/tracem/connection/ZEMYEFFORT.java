package com.sf.tracem.connection;

import java.util.List;

public class ZEMYEFFORT {
	private List<Message> errors;
	private String CONFIRM;
	private String PCONFIRMADAS;
	private String PENDIENTE;
	private String TOT;
	private String T_CONFIRM;
	private String T_ESTIM;
	private String PROGRAM;
	
	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}

	public String getCONFIRMADAS() {
		return CONFIRM;
	}

	public void setCONFIRMADAS(String CONFIRMADAS) {
		this.CONFIRM = CONFIRMADAS;
	}

	public String getPCONFIRMADAS() {
		return PCONFIRMADAS;
	}

	public void setPCONFIRMADAS(String PCONFIRMADAS) {
		this.PCONFIRMADAS = PCONFIRMADAS;
	}

	public String getPENDIENTES() {
		return PENDIENTE;
	}

	public void setPENDIENTES(String PENDIENTES) {
		this.PENDIENTE = PENDIENTES;
	}

	public String getTOTAL() {
		return TOT;
	}

	public void setTOTAL(String TOTAL) {
		this.TOT = TOTAL;
	}

	public String getT_CONFIRMADOS() {
		return T_CONFIRM;
	}

	public void setT_CONFIRMADOS(String t_CONFIRMADOS) {
		T_CONFIRM = t_CONFIRMADOS;
	}

	public String getT_ESTIMADOS() {
		return T_ESTIM;
	}

	public void setT_ESTIMADOS(String t_ESTIMADOS) {
		T_ESTIM = t_ESTIMADOS;
	}
	
	public String getID_SCHEDULE() {
		return PROGRAM;
	}

	public void setID_SCHEDULE(String pROGRAM) {
		PROGRAM = pROGRAM;
	}

	public static final String CREATE_TABLE = "CREATE TABLE EFFORT("
			+ "ID_SCHEDULE TEXT PRIMARY KEY"
			+ ", CONFIRMADAS INTEGER"
			+ ", PENDIENTES INTEGER"
			+ ", P_CONF INTEGER"
			+ ", TOTAL INTEGER"
			+ ", T_CONFIRMADOS REAL"
			+ ", T_ESTIMADOS REAL"
			+ ")";
	
	public static final String CREATE_DATE = "CREATE_DATE";
	
	//effrot table name
	public static final String TABLE_NAME = "EFFORT";
	
	public static final String ID_SCHEDULE = "ID_SCHEDULE";
	public static final String CONFIRMADAS = "CONFIRMADAS";
	public static final String PENDIENTES = "PENDIENTES";
	public static final String P_CONF = "P_CONF";
	public static final String TOTAL = "TOTAL";
	public static final String T_CONFIRMADOS = "T_CONFIRMADOS";
	public static final String T_ESTIMADOS = "T_ESTIMADOS";
	
	public final static String[] COLUMN_NAMES = new String[] { 
		ID_SCHEDULE, CONFIRMADAS, PENDIENTES, 
		P_CONF, TOTAL, T_CONFIRMADOS, T_ESTIMADOS };
	
}
