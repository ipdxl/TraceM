/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.tracem.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.sf.tracem.connection.OrderSchedule;
import com.sf.tracem.connection.Schedule;
import com.sf.tracem.connection.TraceMFormater;
import com.sf.tracem.connection.ZEORDER;

/**
 * 
 * @author José Guadalupe Mandujano Serrano
 */
public class DBManager {

	private TraceMOpenHelper toh;
	private SQLiteDatabase traceMrdb;
	private SQLiteDatabase traceMwdb;

	public DBManager(Context context) {
		toh = new TraceMOpenHelper(context);
	}

	public List<ZEORDER> getOrders() {
		traceMrdb = toh.getReadableDatabase();

		List<ZEORDER> orders;

		Cursor ordersCursor = traceMrdb.query(OrdersTable.TABLE_NAME,
				OrdersTable.COLUMN_NAMES, null, null, null, null,
				OrdersTable.AUFNR);

		orders = getOrdersFrom(ordersCursor);

		return orders;
	}

	public List<ZEORDER> getUnassignedOrders() {
		traceMrdb = toh.getReadableDatabase();

		List<ZEORDER> orders;

		String query =
		// String
		// .format(Locale.US,
		"SELECT ORDERS.* FROM ORDERS WHERE AUFNR NOT IN (SELECT AUFNR FROM ORDERS_SCHEDULE)";
		// "SELECT O.* FROM %S AS O INNER JOIN %S AS OS ON O.%S = OS.%S WHERE OS.%S = ?",
		// OrdersTable.TABLE_NAME, OrderScheduleTable.TABLE_NAME,
		// OrdersTable.AUFNR, OrderScheduleTable.AUFNR,
		// OrderScheduleTable.ID_PROGRAM);

		Cursor ordersCursor = traceMrdb.rawQuery(query, null);

		// Cursor ordersCursor = traceMrdb.query(OrdersTable.TABLE_NAME,
		// OrdersTable.COLUMN_NAMES, OrdersTable.ID_PROGRAM + " = ?",
		// new String[] { id }, null, null, OrdersTable.AUFNR);

		orders = getOrdersFrom(ordersCursor);

		return orders;
	}

	/**
	 * 
	 * @param cursor
	 *            Cursor with orders
	 * @return A list of orders
	 */
	private List<ZEORDER> getOrdersFrom(Cursor cursor) {
		List<ZEORDER> orders = new ArrayList<ZEORDER>();

		Map<String, Integer> columnsMap = new ArrayMap<String, Integer>();

		for (String column : OrdersTable.COLUMN_NAMES) {
			columnsMap.put(column, cursor.getColumnIndex(column));
		}

		if (cursor.moveToFirst()) {
			do {
				ZEORDER order = new ZEORDER();
				order.setADDRESS(cursor.getString(columnsMap
						.get(OrdersTable.ADDRESS)));
				// order.setASSIGNED_STATUS(cursor.getShort(columnsMap
				// .get(OrdersTable.ASSIGNED_STATUS)));
				order.setAUFART(cursor.getString(columnsMap
						.get(OrdersTable.AUFART)));
				order.setAUFNR(cursor.getString(columnsMap
						.get(OrdersTable.AUFNR)));
				order.setAUFTEXT(cursor.getString(columnsMap
						.get(OrdersTable.AUFTEXT)));
				order.setCO_GSTRP(cursor.getString(columnsMap
						.get(OrdersTable.CO_GSTRP)));
				order.setEXP_DAYS(cursor.getString(columnsMap
						.get(OrdersTable.EXP_DAYS)));
				order.setEXP_STATUS(cursor.getString(columnsMap
						.get(OrdersTable.EXP_STATUS)));
				// order.setID_PROGRAM(cursor.getString(8));
				order.setORDER_STATUS(cursor.getInt(columnsMap
						.get(OrdersTable.ORDER_STATUS)));
				order.setPARTNER(cursor.getString(columnsMap
						.get(OrdersTable.PARTNER)));
				order.setZHOURS(cursor.getString(columnsMap
						.get(OrdersTable.ZHOURS)));
				orders.add(order);
			} while (cursor.moveToNext());
		}

		return orders;
	}

	/**
	 * Insert orders in sqlite database
	 * 
	 * @param orders
	 */
	public void insertOrders(List<ZEORDER> orders) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();
		for (ZEORDER order : orders) {
			values.clear();
			if (order.getPARTNER() != null) {
				values.put(PartnerTable.PARTNER, order.getPARTNER());
				values.put(PartnerTable.ADDRESS, order.getADDRESS());
				long result = traceMwdb.insert(PartnerTable.TABLE_NAME, null,
						values);
				if (result != -1) {
					Log.i("Insert Partner", "" + result);
				} else {
					Log.e("Insert Partner", "" + result);
				}
			}
		}

		for (ZEORDER order : orders) {
			values.clear();
			values.put(OrdersTable.AUFNR, order.getAUFNR());
			values.put(OrdersTable.AUFART, order.getAUFART());
			values.put(OrdersTable.CO_GSTRP, order.getCO_GSTRP());
			values.put(OrdersTable.AUFTEXT, order.getAUFTEXT());
			values.put(OrdersTable.PARTNER, order.getPARTNER());
			values.put(PartnerTable.ADDRESS, order.getADDRESS());
			values.put(OrdersTable.ORDER_STATUS, order.getORDER_STATUS());
			values.put(OrdersTable.EXP_DAYS, order.getEXP_DAYS());
			values.put(OrdersTable.EXP_STATUS, order.getEXP_STATUS());
			values.put(OrdersTable.ZHOURS, order.getZHOURS());
			// values.put(OrdersTable.ASSIGNED_STATUS,
			// order.getASSIGNED_STATUS());
			// values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

			long result = traceMwdb
					.insert(OrdersTable.TABLE_NAME, null, values);
			if (result != -1) {
				Log.i("Insert Order", "" + result);
			} else {
				Log.e("Insert Order", "" + result);
			}
		}
	}

	public void updateOrders(List<ZEORDER> orders) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();

		for (ZEORDER order : orders) {
			values.clear();
			values.put(OrdersTable.AUFNR, order.getAUFNR());
			values.put(OrdersTable.AUFART, order.getAUFART());
			values.put(OrdersTable.CO_GSTRP, order.getCO_GSTRP());
			values.put(OrdersTable.AUFTEXT, order.getAUFTEXT());
			values.put(OrdersTable.PARTNER, order.getPARTNER());
			values.put(PartnerTable.ADDRESS, order.getADDRESS());
			values.put(OrdersTable.ORDER_STATUS, order.getORDER_STATUS());
			values.put(OrdersTable.EXP_DAYS, order.getEXP_DAYS());
			values.put(OrdersTable.EXP_STATUS, order.getEXP_STATUS());
			values.put(OrdersTable.ZHOURS, order.getZHOURS());
			// values.put(OrdersTable.ASSIGNED_STATUS,
			// order.getASSIGNED_STATUS());
			// values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

			long result = traceMwdb.update(OrdersTable.TABLE_NAME, values,
					OrdersTable.AUFNR + " = ?",
					new String[] { order.getAUFNR() });
			if (result != -1) {
				Log.i("Insert Order", "" + result);
			} else {
				Log.e("Insert Order", "" + result);
			}
		}
	}

	public List<Schedule> getSchedules() {
		traceMrdb = toh.getReadableDatabase();
		Cursor cursor = traceMrdb.query(ScheduleTable.TABLE_NAME,
				ScheduleTable.COLUMN_NAMES, null, null, null, null, null);
		List<Schedule> schedules = new ArrayList<Schedule>();

		if (cursor.moveToFirst()) {

			Map<String, Integer> scheduleMap = new ArrayMap<String, Integer>();

			for (String column : ScheduleTable.COLUMN_NAMES) {
				scheduleMap.put(column, cursor.getColumnIndex(column));
			}

			do {
				Schedule item = new Schedule();
				item.setCREATE_DATE(cursor.getString(scheduleMap
						.get(ScheduleTable.CREATE_DATE)));
				item.setID_PROGRAM(cursor.getString(scheduleMap
						.get(ScheduleTable.ID_PROGRAM)));
				item.setSTATUS(cursor.getString(scheduleMap
						.get(ScheduleTable.STATUS)));
				schedules.add(item);
			} while (cursor.moveToNext());
		}

		return schedules;
	}

	public void insertSchedule(Schedule schedule) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(ScheduleTable.CREATE_DATE, schedule.getCREATE_DATE());
		values.put(ScheduleTable.ID_PROGRAM, schedule.getID_PROGRAM());
		values.put(ScheduleTable.STATUS, schedule.getSTATUS());

		traceMwdb.insert(ScheduleTable.TABLE_NAME, null, values);
	}

	public void insertScheduleDetail(String iDProgram, List<ZEORDER> orders) {

		updateSchedule(orders, iDProgram);

	}

	public List<ZEORDER> getScheduleDetail(int year, int week) {
		traceMrdb = toh.getReadableDatabase();

		String id = getID_Program(year, week);

		List<ZEORDER> orders;

		String query =
		// String
		// .format(Locale.US,
		"SELECT ORDERS.* FROM ORDERS  INNER JOIN ORDERS_SCHEDULE ON ORDERS.AUFNR = ORDERS_SCHEDULE.AUFNR WHERE ORDERS_SCHEDULE.ID_PROGRAM = ?";
		// "SELECT O.* FROM %S AS O INNER JOIN %S AS OS ON O.%S = OS.%S WHERE OS.%S = ?",
		// OrdersTable.TABLE_NAME, OrderScheduleTable.TABLE_NAME,
		// OrdersTable.AUFNR, OrderScheduleTable.AUFNR,
		// OrderScheduleTable.ID_PROGRAM);

		Cursor ordersCursor = traceMrdb.rawQuery(query, new String[] { id });

		// Cursor ordersCursor = traceMrdb.query(OrdersTable.TABLE_NAME,
		// OrdersTable.COLUMN_NAMES, OrdersTable.ID_PROGRAM + " = ?",
		// new String[] { id }, null, null, OrdersTable.AUFNR);

		orders = getOrdersFrom(ordersCursor);

		return orders;
	}

	public void updateSchedule(List<ZEORDER> newSchedule, String iDProgram) {
		updateSchedule(newSchedule, TraceMFormater.getScheduleYear(iDProgram),
				TraceMFormater.getScheduleWeek(iDProgram));
	}

	public void updateSchedule(List<ZEORDER> newSchedule, int year, int week) {
		String id = getID_Program(year, week);

		List<OrderSchedule> oss = new ArrayList<OrderSchedule>();

		for (ZEORDER order : newSchedule) {
			OrderSchedule os = new OrderSchedule();
			os.setAUFNR(order.getAUFNR());
			os.setID_PROGRAM(id);
			oss.add(os);
		}

		traceMwdb = toh.getWritableDatabase();

		traceMwdb.delete(OrderScheduleTable.TABLE_NAME,
				OrderScheduleTable.ID_PROGRAM + " = ?", new String[] { id });

		for (OrderSchedule os : oss) {
			ContentValues values = new ContentValues();
			values.put(OrderScheduleTable.AUFNR, os.getAUFNR());
			values.put(OrderScheduleTable.ID_PROGRAM, os.getID_PROGRAM());
			traceMwdb.insert(OrderScheduleTable.TABLE_NAME, null, values);
		}

	}

	private String getID_Program(int year, int week) {
		return "" + year + (week < 10 ? "0" + week : "" + week);
	}

	public void deleteSchedule(int year, int week) {
		String id = getID_Program(year, week);

		traceMwdb.delete(OrderScheduleTable.TABLE_NAME,
				ScheduleTable.ID_PROGRAM + " = ?", new String[] { id });

	}

	public String getActiveSchedule() {

		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(ScheduleTable.TABLE_NAME,
				ScheduleTable.COLUMN_NAMES, ScheduleTable.STATUS + " = ?",
				new String[] { "002" }, null, null, null);

		String idProgram = null;
		if (cursor.moveToFirst()) {
			idProgram = cursor.getString(1);
		}

		return idProgram;
	}

}
