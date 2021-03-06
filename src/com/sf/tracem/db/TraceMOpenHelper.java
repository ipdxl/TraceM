/**
 *
 */
package com.sf.tracem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sf.tracem.connection.Component;
import com.sf.tracem.connection.Equipment;
import com.sf.tracem.connection.HeaderOrder;
import com.sf.tracem.connection.MeasurementPoint;
import com.sf.tracem.connection.Menu;
import com.sf.tracem.connection.Operation;
import com.sf.tracem.connection.Order;
import com.sf.tracem.connection.OrderSchedule;
import com.sf.tracem.connection.Partner;
import com.sf.tracem.connection.Schedule;
import com.sf.tracem.connection.Visit;
import com.sf.tracem.connection.VisitLog;

/**
 * @author Jos� Guadalupe Mandujano Serrano
 * 
 */
public class TraceMOpenHelper extends SQLiteOpenHelper {

	/**
	 * Database name
	 */
	public final static String DB_NAME = "TRACEM";

	/**
	 * @param context
	 */
	public TraceMOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Menu.CREATE_TABLE);
		db.execSQL(Partner.CREATE_TABLE);
		db.execSQL(Schedule.CREATE_TABLE);
		db.execSQL(Order.CREATE_TABLE);
		db.execSQL(OrderSchedule.CREATE_TABLE);
		db.execSQL(HeaderOrder.CREATE_TABLE);
		db.execSQL(Operation.CREATE_TABLE);
		db.execSQL(Component.CREATE_TABLE);
		db.execSQL(Equipment.CREATE_TABLE);
		db.execSQL(MeasurementPoint.CREATE_TABLE);
		db.execSQL(Visit.CREATE_TABLE);
		db.execSQL(VisitLog.CREATE_TABLE);

		for (String trigger : Operation.TRIGGERS) {
			db.execSQL(trigger);
		}
		for (String trigger : Visit.TRIGGERS) {
			db.execSQL(trigger);
		}
		for (String trigger : MeasurementPoint.TRIGGERS) {
			db.execSQL(trigger);
		}
		for (String trigger : Schedule.TRIGGERS) {
			db.execSQL(trigger);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void clear() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Menu.TABLE_NAME, null, null);
		db.close();
		clearData();
	}

	public void clearData() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(MeasurementPoint.TABLE_NAME, null, null);
		db.delete(Equipment.TABLE_NAME, null, null);
		db.delete(Component.TABLE_NAME, null, null);
		db.delete(HeaderOrder.TABLE_NAME, null, null);
		db.delete(Operation.TABLE_NAME, null, null);
		db.delete(VisitLog.TABLE_NAME, null, null);
		db.delete(Visit.TABLE_NAME, null, null);
		db.delete(Order.TABLE_NAME, null, null);
		db.delete(Schedule.TABLE_NAME, null, null);
		db.delete(Partner.TABLE_NAME, null, null);
		db.close();
	}
}
