/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.tracem.db;

/**
 *
 * @author José Guadalupe Mandujano Serrano
 */
public class PartnerTable {

    public final static String TABLE_NAME = "PARTNER";

    /**
     * Partner Function
     */
    public final static String PARTN_ROLE = "PARTN_ROLE";
    /**
     * Partner
     */
    public final static String PARTNER = "PARTNER";
    /**
     * Description
     */
    public final static String ROL_TEXT = "ROL_TEXT";
    /**
     * Name 1
     */
    public final static String NAME = "NAME";
    /**
     * Partner address
     */
    public final static String ADDRESS = "ADDRESS";
    /**
     * Latitude
     *
     * @see #LONGITUDE
     */
    public final static String LATITUDE = "ADDRESS";
    /**
     * Longitude
     *
     * @see #LATITUDE
     */
    public final static String LONGITUDE = "ADDRESS";

    public final static String CREATE_TABLE
            = "CREATE TABLE PARTNER (\n"
            + "	  PARTNER 		TEXT PRIMARY KEY\n"
            + "	, PARTNER_ROLE	TEXT\n"
            + "	, ROL_TEXT 		TEXT\n"
            + "	, NAME			TEXT\n"
            + "	, ADDRESS		TEXT\n"
            + "	, LATITUDE		TEXT\n"
            + "	, LONGITUDE		TEXT\n"
            + ");";
}
