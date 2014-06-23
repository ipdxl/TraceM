/**
 * @author José Guadalupe Mandujano Serrano
 */
package com.sf.tracem.connection;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ZEOPERATION_ORDER implements Serializable{

	private String ACTIVITY;
	private String WORK_CNTR;
	private String DESCRIPTION;
	private String DURATION_NORMAL;
	private String DURATION_NORMAL_UNIT;

	/**
	 * Gets the Operation/Activity Number
	 * 
	 * @return Operation/Activity Number
	 */
	public String getACTIVITY() {
		return ACTIVITY;
	}

	public void setACTIVITY(String aCTIVITY) {
		ACTIVITY = aCTIVITY;
	}

	/**
	 * Gets the Work center
	 * 
	 * @return Work center
	 */
	public String getWORK_CNTR() {
		return WORK_CNTR;
	}

	public void setWORK_CNTR(String wORK_CNTR) {
		WORK_CNTR = wORK_CNTR;
	}

	/**
	 * Gets the operation description Describes the operation or sub-operation
	 * 
	 * @return operation description
	 */
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	/**
	 * Gets the Normal duration of the activity
	 * 
	 * @return Normal duration of the activity
	 */
	public String getDURATION_NORMAL() {
		return DURATION_NORMAL;
	}

	public void setDURATION_NORMAL(String dURATION_NORMAL) {
		DURATION_NORMAL = dURATION_NORMAL;
	}

	/**
	 * Gets the Normal duration/unit
	 * 
	 * @return Normal duration/unit
	 */
	public String getDURATION_NORMAL_UNIT() {
		return DURATION_NORMAL_UNIT;
	}

	public void setDURATION_NORMAL_UNIT(String dURATION_NORMAL_UNIT) {
		DURATION_NORMAL_UNIT = dURATION_NORMAL_UNIT;
	}

}
