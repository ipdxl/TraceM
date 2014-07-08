/**
 * 
 */
package com.sf.tracem.connection;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class Visit {
	private long id_visit;
	private String id_program;
	private String user;
	private String fini;
	private String hini;
	private byte tini;
	private String ffin;
	private String hfin;
	private byte tfin;
	private byte status;
	private String comentario;
	private String id_justificacion;

	public final static String TABLE_NAME = "VISIT";

	public final static String CREATE_TABLE = "CREATE TABLE VISIT("
			+ "ID_VISIT INTEGER"
			+ ", ID_PROGRAM INTEGER NOT NULL REFERENCES SCHEDULE(ID_PROGRAM) ON UPDATE CASCADE ON DELETE CASCADE"
			+ ", FINI TEXT NOT NULL" + ", HINI TEXT NOT NULL"
			+ ", TINI INTEGER NOT NULL CHECK (TINI = 1 OR TINI = 0)"
			+ ", FFIN TEXT NOT NULL" + ", HFIN TEXT NOT NULL"
			+ ", TFIN INTEGER NOT NULL CHECK (TFIN = 1 OR TFIN = 0)"
			+ ", STATUS INTEGER NOT NULL DEFAULT 1" + ", COMENTARIO TEXT"
			+ ", CHECK (STATUS = 1 OR STATUS = 0)"
			+ ", PRIMARY KEY (ID_VISIT,ID_PROGRAM)" + ");";

	public final static String ID_VISIT = "ID_VISIT";
	public final static String COMENTARIO = "COMENTARIO";
	public final static String ID_PROGRAM = Schedule.ID_PROGRAM;
	public final static String FINI = "FINI";
	public final static String HINI = "HINI";
	public final static String FFIN = "FFIN";
	public final static String HFIN = "HFIN";
	public final static String TINI = "TINI";
	public final static String TFIN = "TFIN";
	public final static String STATUS = "STATUS";
	public static final String ID_JUSTIFICATION = "ID_JUSTIFICATION";

	public final static String[] COLUMN_NAMES = new String[] { ID_VISIT,
			ID_PROGRAM, COMENTARIO, FINI, HINI, FFIN, HFIN, TINI, TFIN, STATUS };

	/**
	 * @return the iD_VISIT
	 */
	public long getID_VISIT() {
		return id_visit;
	}

	/**
	 * @param iD_VISIT
	 *            the iD_VISIT to set
	 */
	public void setID_VISIT(long iD_VISIT) {
		id_visit = iD_VISIT;
	}

	/**
	 * @return the iD_PROGRAM
	 */
	public String getID_PROGRAM() {
		return id_program;
	}

	/**
	 * @param iD_PROGRAM
	 *            the iD_PROGRAM to set
	 */
	public void setID_PROGRAM(String iD_PROGRAM) {
		id_program = iD_PROGRAM;
	}

	/**
	 * @return the zUSER
	 */
	public String getZUSER() {
		return user;
	}

	/**
	 * @param zUSER
	 *            the zUSER to set
	 */
	public void setUSER(String zUSER) {
		user = zUSER;
	}

	/**
	 * @return the fINI
	 */
	public String getFINI() {
		return fini;
	}

	/**
	 * @param fINI
	 *            the fINI to set
	 */
	public void setFINI(String fINI) {
		fini = fINI;
	}

	/**
	 * @return the hINI
	 */
	public String getHINI() {
		return hini;
	}

	/**
	 * @param hINI
	 *            the hINI to set
	 */
	public void setHINI(String hINI) {
		hini = hINI;
	}

	/**
	 * @return the tINI
	 */
	public byte getTINI() {
		return tini;
	}

	/**
	 * @param tINI
	 *            the tINI to set
	 */
	public void setTINI(byte tINI) {
		tini = tINI;
	}

	/**
	 * @return the fFIN
	 */
	public String getFFIN() {
		return ffin;
	}

	/**
	 * @param fFIN
	 *            the fFIN to set
	 */
	public void setFFIN(String fFIN) {
		ffin = fFIN;
	}

	/**
	 * @return the hFIN
	 */
	public String getHFIN() {
		return hfin;
	}

	/**
	 * @param hFIN
	 *            the hFIN to set
	 */
	public void setHFIN(String hFIN) {
		hfin = hFIN;
	}

	/**
	 * @return the tFIN
	 */
	public byte getTFIN() {
		return tfin;
	}

	/**
	 * @param b
	 *            the tFIN to set
	 */
	public void setTFIN(byte b) {
		tfin = b;
	}

	/**
	 * @return the sTATUS
	 */
	public byte getSTATUS() {
		return status;
	}

	/**
	 * @param i
	 *            the sTATUS to set
	 */
	public void setSTATUS(byte i) {
		status = i;
	}

	/**
	 * @return the cOMENTARIO
	 */
	public String getCOMENTARIO() {
		return comentario;
	}

	/**
	 * @param cOMENTARIO
	 *            the cOMENTARIO to set
	 */
	public void setCOMENTARIO(String cOMENTARIO) {
		comentario = cOMENTARIO;
	}

	/**
	 * @return the iD_JUSTIFICATION
	 */
	public String getID_JUSTIFICATION() {
		return id_justificacion;
	}

	/**
	 * @param iD_JUSTIFICATION
	 *            the iD_JUSTIFICATION to set
	 */
	public void setID_JUSTIFICATION(String iD_JUSTIFICATION) {
		id_justificacion = iD_JUSTIFICATION;
	}
}
