package com.sf.tracem.connection;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class OrderDetails implements Serializable {
	private List<Component> components;
	private List<HeaderOrder> header;
	private List<Operation> operations;
	private List<Partner> partners;
	private List<Message> errors;
	private List<Equipment> equipments;

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<HeaderOrder> getHeader() {
		return header;
	}

	public void setHeader(List<HeaderOrder> header) {
		this.header = header;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	public List<Message> getMessages() {
		return errors;
	}

	public void setMessages(List<Message> errors) {
		this.errors = errors;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
}
