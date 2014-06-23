package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Path implements Serializable {
	private ZEPARTNER partner;
	private ZEORDER[] orders;

	/**
	 * @return
	 */
	public ZEPARTNER getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(ZEPARTNER partner) {
		this.partner = partner;
	}

	/**
	 * @return the orders
	 */
	public ZEORDER[] getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(ZEORDER[] orders) {
		this.orders = orders;
	}

}
