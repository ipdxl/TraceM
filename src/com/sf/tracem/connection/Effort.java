package com.sf.tracem.connection;

import java.util.List;

public class Effort {
	private List<Message> messages;
	private String confirmadas;
	private String pcondirmadas;
	private String pendientes;
	private String total;
	private String tConfirmados;
	private String tEstimados;
	private String idProgram;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getConfirmadas() {
		return confirmadas;
	}

	public void setConfirmadas(String CONFIRMADAS) {
		this.confirmadas = CONFIRMADAS;
	}

	public String getPConfirmadas() {
		return pcondirmadas;
	}

	public void setPConfirmadas(String PCONFIRMADAS) {
		this.pcondirmadas = PCONFIRMADAS;
	}

	public String getPendientes() {
		return pendientes;
	}

	public void setPendientes(String PENDIENTES) {
		this.pendientes = PENDIENTES;
	}

	public String getTOTAL() {
		return total;
	}

	public void setTotal(String TOTAL) {
		this.total = TOTAL;
	}

	public String getTConfirmados() {
		return tConfirmados;
	}

	public void setTConfirmados(String t_CONFIRMADOS) {
		tConfirmados = t_CONFIRMADOS;
	}

	public String getTEstimados() {
		return tEstimados;
	}

	public void setTEstimados(String t_ESTIMADOS) {
		tEstimados = t_ESTIMADOS;
	}

	public String getIdProgram() {
		return idProgram;
	}

	public void setIdProgram(String pROGRAM) {
		idProgram = pROGRAM;
	}

	public static final String CREATE_TABLE = "CREATE TABLE EFFORT("
			+ " ID_PROGRAM TEXT PRIMARY KEY REFERENCES SCHEDULE(ID_PROGRAM) ON UPDATE CASCADE ON DELETE CASCADE"
			+ ", CONFIRMADAS INTEGER" + ", PENDIENTES INTEGER"
			+ ", P_CONF INTEGER" + ", TOTAL INTEGER" + ", T_CONFIRMADOS REAL"
			+ ", T_ESTIMADOS REAL" + ")";

	public static final String CREATE_DATE = "CREATE_DATE";

	// effrot table name
	public static final String TABLE_NAME = "EFFORT";

	public static final String ID_PROGRAM = Schedule.ID_PROGRAM;
	public static final String CONFIRMADAS = "CONFIRMADAS";
	public static final String PENDIENTES = "PENDIENTES";
	public static final String P_CONF = "P_CONF";
	public static final String TOTAL = "TOTAL";
	public static final String T_CONFIRMADOS = "T_CONFIRMADOS";
	public static final String T_ESTIMADOS = "T_ESTIMADOS";

	public final static String[] COLUMN_NAMES = new String[] { ID_PROGRAM,
			CONFIRMADAS, PENDIENTES, P_CONF, TOTAL, T_CONFIRMADOS, T_ESTIMADOS };

}
