package com.sf.tracem.connection;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ZEMYPROGRAM implements Serializable {

	private List<Message> errors;
	private List<ZEORDER> orders;

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}

	public List<ZEORDER> getORDERS() {
		return orders;
	}

	public void setORDERS(List<ZEORDER> list) {
		this.orders = list;
	}
}
