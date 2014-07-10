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

import com.sf.tracem.connection.Component;
import com.sf.tracem.connection.Equipment;
import com.sf.tracem.connection.HeaderOrder;
import com.sf.tracem.connection.MeasurementPoint;
import com.sf.tracem.connection.Operation;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.OrderDetails;
import com.sf.tracem.connection.OrderSchedule;
import com.sf.tracem.connection.Partner;
import com.sf.tracem.connection.Schedule;
import com.sf.tracem.connection.TraceMFormater;
import com.sf.tracem.connection.Visit;

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

	public List<Order> getOrders() {
		traceMrdb = toh.getReadableDatabase();

		List<Order> orders;

		Cursor ordersCursor = traceMrdb.query(Order.TABLE_NAME,
				Order.COLUMN_NAMES, null, null, null, null, Order.AUFNR);

		orders = getOrdersFrom(ordersCursor);

		return orders;
	}

	public List<Order> getUnassignedOrders() {
		traceMrdb = toh.getReadableDatabase();

		List<Order> orders;

		String query =
		// String
		// .format(Locale.US,
		"SELECT " + Order.TABLE_NAME
				+ ".* FROM ORDERS WHERE AUFNR NOT IN (SELECT AUFNR FROM "
				+ OrderSchedule.TABLE_NAME + " INNER JOIN "
				+ Schedule.TABLE_NAME
				+ " ON ORDERS_SCHEDULE.ID_PROGRAM = SCHEDULE.ID_PROGRAM"
				+ " WHERE SCHEDULE.STATUS <> '003') AND " + Order.ORDER_STATUS
				+ " <> 1";
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
	private List<Order> getOrdersFrom(Cursor cursor) {
		List<Order> orders = new ArrayList<Order>();

		Map<String, Integer> columnsMap = new ArrayMap<String, Integer>();

		for (String column : Order.COLUMN_NAMES) {
			columnsMap.put(column, cursor.getColumnIndex(column));
		}

		if (cursor.moveToFirst()) {
			do {
				Order order = new Order();
				order.setADDRESS(cursor.getString(columnsMap.get(Order.ADDRESS)));
				// order.setASSIGNED_STATUS(cursor.getShort(columnsMap
				// .get(OrdersTable.ASSIGNED_STATUS)));
				order.setAUFART(cursor.getString(columnsMap.get(Order.AUFART)));
				order.setAUFNR(cursor.getString(columnsMap.get(Order.AUFNR)));
				order.setAUFTEXT(cursor.getString(columnsMap.get(Order.AUFTEXT)));
				order.setCO_GSTRP(cursor.getString(columnsMap
						.get(Order.CO_GSTRP)));
				order.setEXP_DAYS(cursor.getString(columnsMap
						.get(Order.EXP_DAYS)));
				order.setEXP_STATUS(cursor.getString(columnsMap
						.get(Order.EXP_STATUS)));
				// order.setID_PROGRAM(cursor.getString(8));
				order.setORDER_STATUS(cursor.getInt(columnsMap
						.get(Order.ORDER_STATUS)));
				order.setPARTNER(cursor.getString(columnsMap.get(Order.PARTNER)));
				order.setZHOURS(cursor.getFloat(columnsMap.get(Order.ZHOURS)));
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
	public void insertOrders(List<Order> orders) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();
		for (Order order : orders) {
			values.clear();
			if (order.getPARTNER() != null) {
				values.put(Partner.PARTNER, order.getPARTNER());
				values.put(Partner.ADDRESS, order.getADDRESS());
				long result = traceMwdb
						.insert(Partner.TABLE_NAME, null, values);
				if (result != -1) {
					Log.i("Insert Partner", "" + result);
				} else {
					Log.e("Insert Partner", "" + result);
				}
			}
		}

		for (Order order : orders) {
			values.clear();
			values.put(Order.AUFNR, order.getAUFNR());
			values.put(Order.AUFART, order.getAUFART());
			values.put(Order.CO_GSTRP, order.getCO_GSTRP());
			values.put(Order.AUFTEXT, order.getAUFTEXT());
			if (order.getPARTNER() == null) {
				continue;
			}
			values.put(Order.PARTNER, order.getPARTNER());
			values.put(Partner.ADDRESS, order.getADDRESS());
			values.put(Order.ORDER_STATUS, order.getORDER_STATUS());
			values.put(Order.EXP_DAYS, order.getEXP_DAYS());
			values.put(Order.EXP_STATUS, order.getEXP_STATUS());
			values.put(Order.ZHOURS, order.getZHOURS());
			// values.put(OrdersTable.ASSIGNED_STATUS,
			// order.getASSIGNED_STATUS());
			// values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

			long result = traceMwdb.insert(Order.TABLE_NAME, null, values);
			if (result != -1) {
				Log.i("Insert Order", "" + result);
			} else {
				Log.e("Insert Order", "" + result);
			}
		}
	}

	public void updateOrders(List<Order> orders) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();

		for (Order order : orders) {
			values.clear();
			values.put(Order.AUFNR, order.getAUFNR());
			values.put(Order.AUFART, order.getAUFART());
			values.put(Order.CO_GSTRP, order.getCO_GSTRP());
			values.put(Order.AUFTEXT, order.getAUFTEXT());
			values.put(Order.PARTNER, order.getPARTNER());
			values.put(Partner.ADDRESS, order.getADDRESS());
			values.put(Order.ORDER_STATUS, order.getORDER_STATUS());
			values.put(Order.EXP_DAYS, order.getEXP_DAYS());
			values.put(Order.EXP_STATUS, order.getEXP_STATUS());
			values.put(Order.ZHOURS, order.getZHOURS());
			// values.put(OrdersTable.ASSIGNED_STATUS,
			// order.getASSIGNED_STATUS());
			// values.put(OrdersTable.ID_PROGRAM, order.getID_PROGRAM());

			long result = traceMwdb.update(Order.TABLE_NAME, values,
					Order.AUFNR + " = ?", new String[] { order.getAUFNR() });
			if (result != -1) {
				Log.i("Insert Order", "" + result);
			} else {
				Log.e("Insert Order", "" + result);
			}
		}
	}

	public List<Schedule> getSchedules() {
		traceMrdb = toh.getReadableDatabase();
		Cursor cursor = traceMrdb.query(Schedule.TABLE_NAME,
				Schedule.COLUMN_NAMES, null, null, null, null, null);
		List<Schedule> schedules = new ArrayList<Schedule>();

		if (cursor.moveToFirst()) {

			Map<String, Integer> scheduleMap = new ArrayMap<String, Integer>();

			for (String column : Schedule.COLUMN_NAMES) {
				scheduleMap.put(column, cursor.getColumnIndex(column));
			}

			do {
				Schedule item = new Schedule();
				item.setCREATE_DATE(cursor.getString(scheduleMap
						.get(Schedule.CREATE_DATE)));
				item.setID_PROGRAM(cursor.getString(scheduleMap
						.get(Schedule.ID_PROGRAM)));
				item.setSTATUS(cursor.getString(scheduleMap
						.get(Schedule.STATUS)));
				schedules.add(item);
			} while (cursor.moveToNext());
		}

		return schedules;
	}

	public void insertSchedule(Schedule schedule) {

		traceMwdb = toh.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(Schedule.CREATE_DATE, schedule.getCREATE_DATE());
		values.put(Schedule.ID_PROGRAM, schedule.getID_PROGRAM());
		values.put(Schedule.STATUS, schedule.getSTATUS());

		traceMwdb.insert(Schedule.TABLE_NAME, null, values);
	}

	public void insertScheduleDetail(String iDProgram, List<Order> orders) {

		updateSchedule(orders, iDProgram);

	}

	public List<Order> getScheduleDetail(String id) {

		int year = getYeat(id);
		int week = getWeek(id);

		return getScheduleDetail(year, week);
	}

	private int getWeek(String id) {
		return Integer.parseInt(id.substring(4));
	}

	private int getYeat(String id) {
		return Integer.parseInt(id.substring(0, 4));
	}

	public List<Order> getScheduleDetail(int year, int week) {
		traceMrdb = toh.getReadableDatabase();

		String id = getID_Program(year, week);

		List<Order> orders;

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

	public void updateSchedule(List<Order> newSchedule, String iDProgram) {
		updateSchedule(newSchedule, TraceMFormater.getScheduleYear(iDProgram),
				TraceMFormater.getScheduleWeek(iDProgram));
	}

	public void updateSchedule(List<Order> newSchedule, int year, int week) {
		String id = getID_Program(year, week);

		List<OrderSchedule> oss = new ArrayList<OrderSchedule>();

		for (Order order : newSchedule) {
			OrderSchedule os = new OrderSchedule();
			os.setAUFNR(order.getAUFNR());
			os.setID_PROGRAM(id);
			oss.add(os);
		}

		traceMwdb = toh.getWritableDatabase();

		traceMwdb.delete(OrderSchedule.TABLE_NAME, OrderSchedule.ID_PROGRAM
				+ " = ?", new String[] { id });

		for (OrderSchedule os : oss) {
			ContentValues values = new ContentValues();
			values.put(OrderSchedule.AUFNR, os.getAUFNR());
			values.put(OrderSchedule.ID_PROGRAM, os.getID_PROGRAM());
			traceMwdb.insert(OrderSchedule.TABLE_NAME, null, values);
		}

	}

	private String getID_Program(int year, int week) {
		return "" + year + (week < 10 ? "0" + week : "" + week);
	}

	public void deleteSchedule(int year, int week) {
		traceMwdb = toh.getWritableDatabase();
		String id = getID_Program(year, week);
		traceMwdb.delete(Schedule.TABLE_NAME, Schedule.ID_PROGRAM + " = ?",
				new String[] { id });
	}

	public String getActiveSchedule() {

		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Schedule.TABLE_NAME,
				Schedule.COLUMN_NAMES, Schedule.STATUS + " = ?",
				new String[] { "002" }, null, null, null);

		String idProgram = null;
		if (cursor.moveToFirst()) {
			idProgram = cursor.getString(1);
		}

		return idProgram;
	}

	public void insertHeader(List<HeaderOrder> headers) {

		traceMwdb = toh.getWritableDatabase();

		for (HeaderOrder header : headers) {
			ContentValues values = new ContentValues();

			values.put(HeaderOrder.AUFNR, header.getAUFNR());
			values.put(HeaderOrder.EQUIPMENT, header.getEQUIPMENT());
			values.put(HeaderOrder.MN_WKCTR_ID, header.getMN_WKCTR_ID());
			values.put(HeaderOrder.NOTIF_NO, header.getNOTIF_NO());
			values.put(HeaderOrder.PLANGROUP, header.getPLANGROUP());

			traceMwdb.insert(HeaderOrder.TABLE_NAME, null, values);
		}

	}

	public void insertEquipment(String order, List<Equipment> equipments) {
		traceMwdb = toh.getWritableDatabase();
		for (Equipment equipment : equipments) {
			ContentValues values = new ContentValues();

			values.put(Equipment.AUFNR, order);
			values.put(Equipment.EQTXT, equipment.getEQKTX());
			values.put(Equipment.EQUNR, equipment.getEQUNR());
			values.put(Equipment.COMPLETE, equipment.getComplete());

			traceMwdb.insert(Equipment.TABLE_NAME, null, values);
		}
	}

	public void insertOperations(String order, List<Operation> operations) {
		traceMwdb = toh.getWritableDatabase();
		for (Operation op : operations) {
			ContentValues values = new ContentValues();

			values.put(Operation.AUFNR, order);
			values.put(Operation.ACTIVITY, op.getACTIVITY());
			values.put(Operation.COMPLETE, op.getCOMPLETE());
			values.put(Operation.DESCRIPTION, op.getDESCRIPTION());
			values.put(Operation.DURATION_NORMAL, op.getDURATION_NORMAL());
			values.put(Operation.DURATION_NORMAL_UNIT,
					op.getDURATION_NORMAL_UNIT());
			values.put(Operation.PLANT, op.getPLANT());
			values.put(Operation.WORK_CNTR, op.getWORK_CNTR());

			traceMwdb.insert(Operation.TABLE_NAME, null, values);
		}
	}

	public void insertComponents(String order, List<Component> components) {
		traceMwdb = toh.getWritableDatabase();
		for (Component comp : components) {
			ContentValues values = new ContentValues();

			values.put(Component.AUFNR, order);
			values.put(Component.ACTIVITY, comp.getACTIVITY());
			values.put(Component.MATERIAL, comp.getMATERIAL());
			values.put(Component.MATL_DESC, comp.getMATL_DESC());
			values.put(Component.REQUIREMENT_QUANTITY,
					comp.getREQUIREMENT_QUANTITY());
			values.put(Component.REQUIREMENT_QUANTITY_UNIT,
					comp.getREQUIREMENT_QUANTITY_UNIT());
			traceMwdb.insert(Component.TABLE_NAME, null, values);
		}
	}

	public OrderDetails getOrderDetails(String aufnr) {
		OrderDetails od = new OrderDetails();
		od.setOperations(getOperations(aufnr));
		od.setEquipments(getEquipments(aufnr));
		od.setComponents(getComponents(aufnr));

		return od;
	}

	private List<Component> getComponents(String aufnr) {

		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Component.TABLE_NAME,
				Component.COLUMN_NAMES, Component.AUFNR + " = ?",
				new String[] { aufnr }, null, null, null);

		List<Component> components = new ArrayList<Component>();

		if (cursor.moveToFirst()) {
			Map<String, Integer> map = getColumnMap(cursor,
					Component.COLUMN_NAMES);
			do {
				Component component = new Component();

				try {
					component.setACTIVITY(cursor.getString(map
							.get(Component.ACTIVITY)));
					component.setMATERIAL(cursor.getString(map
							.get(Component.MATERIAL)));
					component.setMATL_DESC(cursor.getString(map
							.get(Component.MATL_DESC)));
					component.setREQUIREMENT_QUANTITY(cursor.getString(map
							.get(Component.REQUIREMENT_QUANTITY)));
					component.setREQUIREMENT_QUANTITY_UNIT(cursor.getString(map
							.get(Component.REQUIREMENT_QUANTITY_UNIT)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				components.add(component);

			} while (cursor.moveToNext());
		}

		return components;
	}

	private List<Equipment> getEquipments(String aufnr) {

		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Equipment.TABLE_NAME,
				Equipment.COLUMN_NAMES, Equipment.AUFNR + " = ?",
				new String[] { aufnr }, null, null, null);

		List<Equipment> equipments = new ArrayList<Equipment>();

		if (cursor.moveToFirst()) {

			Map<String, Integer> columnMap = getColumnMap(cursor,
					Equipment.COLUMN_NAMES);
			do {
				Equipment equipment = new Equipment();

				equipment.setEQUNR(cursor.getString(columnMap
						.get(Equipment.EQUNR)));
				equipment.setEQKTX(cursor.getString(columnMap
						.get(Equipment.EQTXT)));
				equipment.setComplete(cursor.getInt(columnMap
						.get(Equipment.COMPLETE)));

				equipments.add(equipment);
			} while (cursor.moveToNext());

			for (Equipment equipment : equipments) {
				equipment.setMeasures(getMeasurementPoints(aufnr,
						equipment.getEQUNR()));
			}

		}
		return equipments;
	}

	private List<Operation> getOperations(String aufnr) {
		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Operation.TABLE_NAME,
				Operation.COLUMN_NAMES, Operation.AUFNR + " = ?",
				new String[] { aufnr }, null, null, null);

		List<Operation> operations = new ArrayList<Operation>();

		if (cursor.moveToFirst()) {

			Map<String, Integer> columnMap = getColumnMap(cursor,
					Operation.COLUMN_NAMES);
			do {
				Operation operation = new Operation();

				operation.setACTIVITY(cursor.getString(columnMap
						.get(Operation.ACTIVITY)));
				operation.setCOMPLETE(cursor.getInt(columnMap
						.get(Operation.COMPLETE)));
				operation.setDESCRIPTION(cursor.getString(columnMap
						.get(Operation.DESCRIPTION)));
				operation.setDURATION_NORMAL(cursor.getString(columnMap
						.get(Operation.DURATION_NORMAL)));
				operation.setDURATION_NORMAL_UNIT(cursor.getString(columnMap
						.get(Operation.DURATION_NORMAL_UNIT)));
				operation.setPLANT(cursor.getString(columnMap
						.get(Operation.PLANT)));
				operation.setWORK_CNTR(cursor.getString(columnMap
						.get(Operation.WORK_CNTR)));

				operations.add(operation);
			} while (cursor.moveToNext());
		}
		return operations;
	}

	private Map<String, Integer> getColumnMap(Cursor cursor,
			String[] columnNames) {
		Map<String, Integer> columnMap = new ArrayMap<String, Integer>();

		for (String column : columnNames) {
			columnMap.put(column, cursor.getColumnIndex(column));
		}
		return columnMap;
	}

	public void insertMeasurementPoints(List<MeasurementPoint> points) {

		traceMwdb = toh.getWritableDatabase();

		for (MeasurementPoint point : points) {
			ContentValues values = getMeasurementPointsValues(point);

			traceMwdb.insert(MeasurementPoint.TABLE_NAME, null, values);
		}

	}

	private ContentValues getMeasurementPointsValues(MeasurementPoint point) {
		ContentValues values = new ContentValues();

		values.put(MeasurementPoint.AUFNR, point.getAufnr());
		values.put(MeasurementPoint.EQUNR, point.getEqunr());
		values.put(MeasurementPoint.POINT, point.getPoint());
		values.put(MeasurementPoint.READ, point.getRead());
		values.put(MeasurementPoint.UNIT, point.getUnit());
		values.put(MeasurementPoint.DESCRIPTION, point.getDescription());
		values.put(MeasurementPoint.NOTES, point.getNotes());
		return values;
	}

	public void updateMeasurementPoints(List<MeasurementPoint> points) {

		traceMrdb = toh.getReadableDatabase();

		for (MeasurementPoint point : points) {
			ContentValues values = getMeasurementPointsValues(point);

			int result = traceMrdb.update(MeasurementPoint.TABLE_NAME, values,
					MeasurementPoint.EQUNR + " = ? AND "
							+ MeasurementPoint.AUFNR + " = ?", new String[] {
							point.getEqunr(), point.getAufnr() });

			Log.i("Update Measurement point", "" + result);
		}
	}

	public List<MeasurementPoint> getMeasurementPoints(String aufnr,
			String equnr) {
		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(MeasurementPoint.TABLE_NAME,
				MeasurementPoint.COLUMN_NAMES, MeasurementPoint.EQUNR
						+ " = ? AND " + MeasurementPoint.AUFNR + " = ?",
				new String[] { equnr, aufnr }, null, null, null);

		List<MeasurementPoint> points = new ArrayList<MeasurementPoint>();

		if (cursor.moveToFirst()) {
			Map<String, Integer> map = getColumnMap(cursor,
					MeasurementPoint.COLUMN_NAMES);

			do {
				MeasurementPoint point = new MeasurementPoint();

				point.setAufnr(aufnr);
				point.setEqunr(equnr);
				point.setPoint(cursor.getString(map.get(MeasurementPoint.POINT)));
				point.setRead(cursor.getDouble(map.get(MeasurementPoint.READ)));
				point.setUnit(cursor.getString(map.get(MeasurementPoint.UNIT)));
				point.setDescription(cursor.getString(map
						.get(MeasurementPoint.DESCRIPTION)));
				point.setNotes(cursor.getString(map.get(MeasurementPoint.NOTES)));

				points.add(point);

			} while (cursor.moveToNext());
		}

		return points;
	}

	public void insertVisits(List<Visit> visitList) {

		for (Visit visit : visitList) {
			insertVisit(visit);
		}

	}

	private ContentValues getVisitValues(Visit visit) {

		ContentValues values = new ContentValues();
		values.put(Visit.FFIN, visit.getFFIN());
		values.put(Visit.FINI, visit.getFINI());
		values.put(Visit.HFIN, visit.getHFIN());
		values.put(Visit.HINI, visit.getHINI());
		values.put(Visit.ID_PROGRAM, visit.getID_PROGRAM());
		values.put(Visit.ID_VISIT, visit.getID_VISIT());
		values.put(Visit.STATUS, visit.getSTATUS());
		values.put(Visit.TFIN, visit.getTFIN());
		values.put(Visit.TINI, visit.getTINI());

		return values;
	}

	public List<Visit> getVisits() {
		List<Visit> visitList;
		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Visit.TABLE_NAME, Visit.COLUMN_NAMES,
				null, null, null, null, Visit.STATUS + " desc");

		visitList = getVisitsFromCursor(cursor);

		return visitList;
	}

	private List<Visit> getVisitsFromCursor(Cursor cursor) {
		List<Visit> visitList = new ArrayList<Visit>();

		if (cursor.moveToFirst()) {
			Map<String, Integer> map = getColumnMap(cursor, Visit.COLUMN_NAMES);
			do {
				Visit visit = new Visit();

				visit.setCOMENTARIO(cursor.getString(map.get(Visit.COMENTARIO)));
				visit.setFFIN(cursor.getString(map.get(Visit.FFIN)));
				visit.setFINI(cursor.getString(map.get(Visit.FINI)));
				visit.setHFIN(cursor.getString(map.get(Visit.HFIN)));
				visit.setHINI(cursor.getString(map.get(Visit.HINI)));
				visit.setID_PROGRAM(cursor.getString(map.get(Visit.ID_PROGRAM)));
				visit.setID_VISIT(cursor.getInt(map.get(Visit.ID_VISIT)));
				visit.setSTATUS((byte) cursor.getInt(map.get(Visit.STATUS)));
				visit.setTFIN((byte) cursor.getInt(map.get(Visit.TFIN)));
				visit.setTINI((byte) cursor.getInt(map.get(Visit.TINI)));

				visitList.add(visit);
			} while (cursor.moveToNext());
		}

		return visitList;
	}

	public void insertVisit(Visit visit) {
		traceMwdb = toh.getWritableDatabase();
		ContentValues values = getVisitValues(visit);

		traceMwdb.insert(Visit.TABLE_NAME, null, values);
	}

	/**
	 * 
	 * @return Current active visit or null if there is not an active visit
	 */
	public Visit getActiveVisit() {
		List<Visit> visits;

		traceMrdb = toh.getReadableDatabase();

		Cursor cursor = traceMrdb.query(Visit.TABLE_NAME, Visit.COLUMN_NAMES,
				Visit.STATUS + " = ?", new String[] { "1" }, null, null, null);

		visits = getVisitsFromCursor(cursor);
		if (visits.size() == 0) {
			return null;
		} else {
			return visits.get(0);
		}
	}

	public List<Order> getUncompleteOrders() {
		List<Order> orders = null;
		List<Order> uncompleteorders = new ArrayList<Order>();

		String activeID = getActiveSchedule();

		orders = getScheduleDetail(activeID);

		for (Order order : orders) {
			if (order.getORDER_STATUS() != 1)
				uncompleteorders.add(order);
		}

		return uncompleteorders;
	}
}
