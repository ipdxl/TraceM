package com.sf.tracem.connection;

import java.util.List;

public class ZEMYEFFORT {
	private List<Message> errors;
	private String CONFIRMADAS;
	private String PCONFIRMADAS;
	private String PENDIENTES;
	private String TOTAL;

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}

	public String getCONFIRMADAS() {
		return CONFIRMADAS;
	}

	public void setCONFIRMADAS(String CONFIRMADAS) {
		this.CONFIRMADAS = CONFIRMADAS;
	}

	public String getPCONFIRMADAS() {
		return PCONFIRMADAS;
	}

	public void setPCONFIRMADAS(String PCONFIRMADAS) {
		this.PCONFIRMADAS = PCONFIRMADAS;
	}

	public String getPENDIENTES() {
		return PENDIENTES;
	}

	public void setPENDIENTES(String PENDIENTES) {
		this.PENDIENTES = PENDIENTES;
	}

	public String getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(String TOTAL) {
		this.TOTAL = TOTAL;
	}
}
