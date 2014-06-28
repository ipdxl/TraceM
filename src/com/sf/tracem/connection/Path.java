package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Path implements Serializable {
	private Partner partner;
	private Order[] orders;

	/**
	 * @return
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	/**
	 * @return the orders
	 */
	public Order[] getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(Order[] orders) {
		this.orders = orders;
	}

}
