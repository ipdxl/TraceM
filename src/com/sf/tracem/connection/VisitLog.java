package com.sf.tracem.connection;

public class VisitLog {

	public static final String TABLE_NAME = "VISIT_LOG";

	public static final String CREATE_TABLE = 
			"CREATE TABLE VISIT_LOG("
			+ "	ID_VISIT TEXT" 
			+ "	, ID DATETIME DEFAULT CURRENT_TIMESTAMP"
			+ "	, ZDATE TEXT" 
			+ "	, ZHOUR TEXT" 
			+ "	, ID_EVENT INTEGER"
			+ "	, TEXT_EVENT" 
			+ "	, PRIMARY KEY (ID_VISIT,ID)" + ")";

	public static final String ID_VISIT = Visit.ID_VISIT;
	public static final String DATE = "ZDATE";
	public static final String HOUR = "ZHOUR";
	public static final String ID_EVENT = "ID_EVENT";
	public static final String TEXT_EVENT = "TEXT_EVENT";
	
	public static final String[] COLUMN_NAMES = new String[]{
		ID_VISIT
		,DATE
		,HOUR
		,ID_EVENT
		,TEXT_EVENT
	};
	
	private long id_visit;
	private String date;
	private String hour;
	private long id_event;
	private String text_event;
	/**
	 * @return the id_visit
	 */
	public long getId_visit() {
		return id_visit;
	}
	/**
	 * @param id_visit the id_visit to set
	 */
	public void setId_visit(long id_visit) {
		this.id_visit = id_visit;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}
	/**
	 * @return the id_event
	 */
	public long getId_event() {
		return id_event;
	}
	/**
	 * @param id_event the id_event to set
	 */
	public void setId_event(long id_event) {
		this.id_event = id_event;
	}
	/**
	 * @return the text_event
	 */
	public String getText_event() {
		return text_event;
	}
	/**
	 * @param text_event the text_event to set
	 */
	public void setText_event(String text_event) {
		this.text_event = text_event;
	}
}
