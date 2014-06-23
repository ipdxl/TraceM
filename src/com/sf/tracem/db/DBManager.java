/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sf.tracem.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sf.tracem.connection.Schedule;
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

		Cursor ordersCursor = traceMrdb.query(OrdersTable.TABLE_NAME,
				OrdersTable.COLUMN_NAMES, OrdersTable.ASSIGNED_STATUS + " = ?",
				new String[] { "0" }, null, null, OrdersTable.AUFNR);

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

		if (cursor.moveToFirst()) {
			do {
				ZEORDER order = new ZEORDER();
				order.setADDRESS(cursor.getString(0));
				order.setASSIGNED_STATUS(cursor.getShort(1));
				order.setAUFART(cursor.getString(2));
				order.setAUFNR(cursor.getString(3));
				order.setAUFTEXT(cursor.getString(4));
				order.setCO_GSTRP(cursor.getString(5));
				order.setEXP_DAYS(cursor.getString(6));
				order.setEXP_STATUS(cursor.getString(7));
				order.setID_PROGRAM(cursor.getString(8));
				order.setORDER_STATUS(cursor.getInt(9));
				order.setPARTNER(cursor.getString(10));
				order.setZHOURS(cursor.getString(11));
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
			values.put(OrdersTable.ASSIGNED_STATUS, order.getASSIGNED_STATUS());
			values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

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
			values.put(OrdersTable.ASSIGNED_STATUS, order.getASSIGNED_STATUS());
			values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

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
			do {
				Schedule item = new Schedule();
				item.setCREATE_DATE(cursor.getString(0));
				item.setID_PROGRAM(cursor.getString(1));
				item.setSTATUS(cursor.getString(2));
				schedules.add(item);
			} while (cursor.moveToNext());
		}

		return schedules;
	}

	public void insertSchedules(List<Schedule> schedules) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();

		for (Schedule item : schedules) {
			values.clear();
			values.put(ScheduleTable.CREATE_DATE, item.getCREATE_DATE());
			values.put(ScheduleTable.ID_PROGRAM, item.getID_PROGRAM());
			values.put(ScheduleTable.STATUS, item.getSTATUS());

			traceMwdb.insert(ScheduleTable.TABLE_NAME, null, values);
		}

	}

	public List<ZEORDER> getScheduleDetail(int year, int week) {
		traceMrdb = toh.getReadableDatabase();

		String id = getID_Program(year, week);

		List<ZEORDER> orders;

		Cursor ordersCursor = traceMrdb.query(OrdersTable.TABLE_NAME,
				OrdersTable.COLUMN_NAMES, OrdersTable.ID_PROGRAM + " = ?",
				new String[] { id }, null, null, OrdersTable.AUFNR);

		orders = getOrdersFrom(ordersCursor);

		return orders;
	}

	public void updateSchedule(List<ZEORDER> oldSchedule,
			List<ZEORDER> schedule, int year, int week) {
		String id = getID_Program(year, week);

		for (ZEORDER order : oldSchedule) {
			order.setID_PROGRAM(null);
		}

		updateOrders(oldSchedule);

		for (ZEORDER order : schedule) {
			order.setID_PROGRAM(id);
		}

		updateOrders(schedule);
	}

	private String getID_Program(int year, int week) {
		return "" + year + (week < 10 ? "0" + week : "" + week);
	}

	public void deleteSchedule(int year, int week) {
		String id = getID_Program(year, week);

		List<ZEORDER> schedule = getScheduleDetail(year, week);

		for (ZEORDER order : schedule) {
			order.setID_PROGRAM(null);
		}
		updateOrders(schedule);

		traceMwdb.delete(ScheduleTable.TABLE_NAME, ScheduleTable.ID_PROGRAM
				+ " = ?", new String[] { id });

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
