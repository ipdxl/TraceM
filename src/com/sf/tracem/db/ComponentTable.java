package com.sf.tracem.db;

public class ComponentTable {
    
    /**
     * Table name
     */
    public final static String TABLE_NAME = "COMPONENT";
    /**
     * Order number
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
            + "	  AUFNR 	TEXT\n"
            + "	, MATERIAL 	TEXT\n"
            + "	, ACTIVITY 	TEXT\n"
            + "	, MATL_DESC TEXT\n"
            + "	, REQUIREMENT_QUANTITY 		REAL\n"
            + "	, REQUIREMENT_QUANTITY_UNIT TEXT\n"
            + "	, PRIMARY KEY (MATERIAL, ACTIVITY)\n"
            + ");";
}
