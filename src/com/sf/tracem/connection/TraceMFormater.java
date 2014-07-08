/**
 * 
 */
package com.sf.tracem.connection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author José Guadalupe Mandujano Serrano
 * 
 */
public class TraceMFormater {

	public final static String DATE_FORMATE = "yyyy-MM-dd";
	private static final String TIME_FORMATE = "hh:mm:ss";

	public static String getDate(int year, int month, int day) {
		SimpleDateFormat formater = new SimpleDateFormat(DATE_FORMATE,
				Locale.US);

		Calendar cal = new GregorianCalendar(year, month, day);
		Date date = cal.getTime();

		String sDate = formater.format(date);
		return sDate;
	}

	public static String getTime(int hour, int minute, int second) {
		SimpleDateFormat formater = new SimpleDateFormat(TIME_FORMATE,
				Locale.US);

		Calendar cal = new GregorianCalendar(0, 0, 0, hour, minute, second);
		Date date = cal.getTime();

		String time = formater.format(date);
		return time;
	}

	public static int getYear(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}

	public static int getMonth(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}

	public static int getDay(String date) {
		return Integer.parseInt(date.substring(8));
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

	public static String getDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		Date date = cal.getTime();

		SimpleDateFormat formater = new SimpleDateFormat(DATE_FORMATE,
				Locale.US);

		String currentDate = formater.format(date);

		return currentDate;
	}

	public static String getTime() {
		Calendar cal = new GregorianCalendar();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

		Date date = cal.getTime();

		SimpleDateFormat formater = new SimpleDateFormat(TIME_FORMATE,
				Locale.US);

		String time = formater.format(date);

		return time;
	}

}
