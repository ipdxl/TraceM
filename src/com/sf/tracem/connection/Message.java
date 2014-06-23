/**
 * 
 */
package com.sf.tracem.connection;

import java.io.Serializable;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
@SuppressWarnings("serial")
public class Message implements Serializable {
	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	private char type;
	private int number;
	private String message;
}
