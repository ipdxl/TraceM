/**
 * 
 */
package com.sf.tracem.connection;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class Visit {
	private long ID_VISIT;
	private String ID_PROGRAM;
	private String ZUSER;
	private String FINI;
	private String HINI;
	private byte TINI;
	private String FFIN;
	private String HFIN;
	private byte TFIN;
	private byte STATUS;
	private String COMENTARIO;
	private String ID_JUSTIFICATION;

	/**
	 * @return the iD_VISIT
	 */
	public long getID_VISIT() {
		return ID_VISIT;
	}

	/**
	 * @param iD_VISIT
	 *            the iD_VISIT to set
	 */
	public void setID_VISIT(long iD_VISIT) {
		ID_VISIT = iD_VISIT;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return ID_PROGRAM;
	}

	/**
	 * @param iD_PROGRAM
	 *            the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String iD_PROGRAM) {
		ID_PROGRAM = iD_PROGRAM;
	}

	/**
	 * @return the zUSER
	 */
	public String getZUSER() {
		return ZUSER;
	}

	/**
	 * @param zUSER
	 *            the zUSER to set
	 */
	public void setZUSER(String zUSER) {
		ZUSER = zUSER;
	}

	/**
	 * @return the fINI
	 */
	public String getFINI() {
		return FINI;
	}

	/**
	 * @param fINI
	 *            the fINI to set
	 */
	public void setFINI(String fINI) {
		FINI = fINI;
	}

	/**
	 * @return the hINI
	 */
	public String getHINI() {
		return HINI;
	}

	/**
	 * @param hINI
	 *            the hINI to set
	 */
	public void setHINI(String hINI) {
		HINI = hINI;
	}

	/**
	 * @return the tINI
	 */
	public byte getTINI() {
		return TINI;
	}

	/**
	 * @param tINI
	 *            the tINI to set
	 */
	public void setTINI(byte tINI) {
		TINI = tINI;
	}

	/**
	 * @return the fFIN
	 */
	public String getFFIN() {
		return FFIN;
	}

	/**
	 * @param fFIN
	 *            the fFIN to set
	 */
	public void setFFIN(String fFIN) {
		FFIN = fFIN;
	}

	/**
	 * @return the hFIN
	 */
	public String getHFIN() {
		return HFIN;
	}

	/**
	 * @param hFIN
	 *            the hFIN to set
	 */
	public void setHFIN(String hFIN) {
		HFIN = hFIN;
	}

	/**
	 * @return the tFIN
	 */
	public byte getTFIN() {
		return TFIN;
	}

	/**
	 * @param b
	 *            the tFIN to set
	 */
	public void setTFIN(byte b) {
		TFIN = b;
	}

	/**
	 * @return the sTATUS
	 */
	public byte getSTATUS() {
		return STATUS;
	}

	/**
	 * @param i
	 *            the sTATUS to set
	 */
	public void setSTATUS(byte i) {
		STATUS = i;
	}

	/**
	 * @return the cOMENTARIO
	 */
	public String getCOMENTARIO() {
		return COMENTARIO;
	}

	/**
	 * @param cOMENTARIO
	 *            the cOMENTARIO to set
	 */
	public void setCOMENTARIO(String cOMENTARIO) {
		COMENTARIO = cOMENTARIO;
	}

	/**
	 * @return the iD_JUSTIFICATION
	 */
	public String getID_JUSTIFICATION() {
		return ID_JUSTIFICATION;
	}

	/**
	 * @param iD_JUSTIFICATION
	 *            the iD_JUSTIFICATION to set
	 */
	public void setID_JUSTIFICATION(String iD_JUSTIFICATION) {
		ID_JUSTIFICATION = iD_JUSTIFICATION;
	}
}
