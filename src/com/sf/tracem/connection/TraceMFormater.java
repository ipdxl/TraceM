/**
 * 
 */
package com.sf.tracem.connection;

import java.util.Locale;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class TraceMFormater {

	public final static String DATE_FORMATE = "d\\d\\d\\d-\\d\\d-\\d\\d";

	public static String formatDate(int year, int month, int day) {
		return String.format(Locale.US, "%tY-%tm-%td", year, month, day);
	}

	public static String formatHour(int hour, int minute, int second) {
		return String.format(Locale.US, "%tH:%tM:%tS", hour, minute, second);
	}

	public static int getYear(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}

	public static int getMonth(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}

	public static int getDay(String date) {
		return Integer.parseInt(date.substring(9));
	}

	public static int getHour(String time) {
		return Integer.parseInt(time.substring(0, 2));
	}

	public static int getMinute(String time) {
		return Integer.parseInt(time.substring(3, 5));
	}

	public static int getSecond(String time) {
		return Integer.parseInt(time.substring(6));
	}

	public static int getScheduleYear(String iDProgram) {
		return Integer.parseInt(iDProgram.substring(0, 4));
	}

	public static int getScheduleWeek(String iDProgram) {
		return Integer.parseInt(iDProgram.substring(4));
	}

	public static String getBool(int complete) {
		return complete == 1 ? "X" : "";
	}

}
