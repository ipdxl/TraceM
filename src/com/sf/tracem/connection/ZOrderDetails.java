package com.sf.tracem.connection;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ZOrderDetails implements Serializable {
	private List<ZECOMPONENTS_ORDER> components;
	private List<ZEHEADER_ORDER> header;
	private List<ZEOPERATION_ORDER> operations;
	private List<ZEPARTNER> partners;
	private List<Message> errors;
	private List<ZEOBJECT_ORDER> equipments;

	public List<ZECOMPONENTS_ORDER> getComponents() {
		return components;
	}

	public void setComponents(List<ZECOMPONENTS_ORDER> components) {
		this.components = components;
	}

	public List<ZEHEADER_ORDER> getHeader() {
		return header;
	}

	public void setHeader(List<ZEHEADER_ORDER> header) {
		this.header = header;
	}

	public List<ZEOPERATION_ORDER> getOperations() {
		return operations;
	}

	public void setOperations(List<ZEOPERATION_ORDER> operations) {
		this.operations = operations;
	}

	public List<ZEPARTNER> getPartners() {
		return partners;
	}

	public void setPartners(List<ZEPARTNER> partners) {
		this.partners = partners;
	}

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}

	public List<ZEOBJECT_ORDER> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<ZEOBJECT_ORDER> equipments) {
		this.equipments = equipments;
	}
}
