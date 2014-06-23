package com.sf.tracem.db;

public class EquipmentTable {
	/**
	 * Equipment number
	 */
	public final static String TABLE_NAME = "EQUIPMENT";
	/**
	 * Equipment number
	 */
	public final static String EQUNR = "EQUNR";
	/**
	 * Order number
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = "AUFNR";
	/**
	 * Description of technical object
	 */
	public final static String EQTXT = "EQTXT";
	/**
	 * CREATE TABLE Equipment
	 */
	public final static String CREATE_TABLE = 
	"CREATE TABLE EQUIPMENT ("
	  +"EQUNR TEXT PRIMARY KEY"
	  +", AUFNR TEXT"
	  +", EQTXT TEXT"
	+");";
}
