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
public class Menu implements Serializable {

	private byte idMenu;
	private byte idFather;

	public Menu(byte idMenu, byte idFather) {
		this.idMenu = idMenu;
		this.idFather = idFather;
	}

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idMenu
	 */
	public byte getIdMenu() {
		return idMenu;
	}

	/**
	 * @param idMenu
	 *            the idMenu to set
	 */
	public void setIdMenu(byte idMenu) {
		this.idMenu = idMenu;
	}

	/**
	 * @return the idFather
	 */
	public byte getIdFather() {
		return idFather;
	}

	/**
	 * @param idFather
	 *            the idFather to set
	 */
	public void setIdFather(byte idFather) {
		this.idFather = idFather;
	}

	@Override
	public String toString() {
		return "" + getIdMenu();
	}
}
