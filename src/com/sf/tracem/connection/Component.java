package com.sf.tracem.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Component implements Serializable {
	private String material;
	private String activity;
	private String matl_desc;
	private String requirement_quantity;
	private String requirement_quantity_unit;

	/**
	 * Table name
	 */
	public final static String TABLE_NAME = "COMPONENT";
	/**
	 * Order number
	 * 
	 * @see OrdersTable#AUFNR
	 */
	public final static String AUFNR = "AUFNR";
	/**
	 * Material Number
	 */
	public final static String MATERIAL = "MATERIAL";
	/**
	 * Operation/Activity Number
	 */
	public final static String ACTIVITY = "ACTIVITY";
	/**
	 * Material Description (Short Text)
	 */
	public final static String MATL_DESC = "MATL_DESC";
	/**
	 * Requirement quantity of the component
	 */
	public final static String REQUIREMENT_QUANTITY = "REQUIREMENT_QUANTITY";
	/**
	 * Base Unit of Measure
	 */
	public final static String REQUIREMENT_QUANTITY_UNIT = "REQUIREMENT_QUANTITY_UNIT";
	/**
	 * CREATE TABLE statement
	 */
	public final static String CREATE_TABLE = "CREATE TABLE COMPONENT (\n"
			+ "	  AUFNR 	TEXT\n" + "	, MATERIAL 	TEXT\n"
			+ "	, ACTIVITY 	TEXT\n" + "	, MATL_DESC TEXT\n"
			+ "	, REQUIREMENT_QUANTITY 		REAL\n"
			+ "	, REQUIREMENT_QUANTITY_UNIT TEXT\n"
			+ "	, PRIMARY KEY (MATERIAL, ACTIVITY)\n" + ");";

	public String getMATERIAL() {
		return material;
	}

	public void setMATERIAL(String mATERIAL) {
		material = mATERIAL;
	}

	public String getACTIVITY() {
		return activity;
	}

	public void setACTIVITY(String aCTIVITY) {
		activity = aCTIVITY;
	}

	public String getMATL_DESC() {
		return matl_desc;
	}

	public void setMATL_DESC(String mATL_DESC) {
		matl_desc = mATL_DESC;
	}

	public String getREQUIREMENT_QUANTITY() {
		return requirement_quantity;
	}

	public void setREQUIREMENT_QUANTITY(String rEQUIREMENT_QUANTITY) {
		requirement_quantity = rEQUIREMENT_QUANTITY;
	}

	public String getREQUIREMENT_QUANTITY_UNIT() {
		return requirement_quantity_unit;
	}

	public void setREQUIREMENT_QUANTITY_UNIT(String rEQUIREMENT_QUANTITY_UNIT) {
		requirement_quantity_unit = rEQUIREMENT_QUANTITY_UNIT;
	}
}
