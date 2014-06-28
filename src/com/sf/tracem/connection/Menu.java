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

	/**
	 * Menu table name
	 */
	public final static String TABLE_NAME = "MENU";
	/**
	 * Id menu column
	 */
	public final static String ID_MENU = "ID_MENU";
	/**
	 * id father column
	 */
	public final static String ID_FATHER = "ID_FATHER";

	/**
	 * Creation menu table statement
	 * 
	 * @see #CREATE_TABLE
	 */
	public final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + ID_MENU + " INTEGER PRIMARY KEY" + ", " + ID_FATHER
			+ " INTEGER REFERENCES " + TABLE_NAME + "(" + ID_MENU + ")" + ")";

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
