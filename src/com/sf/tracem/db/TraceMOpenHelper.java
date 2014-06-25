/**
 *
 */
package com.sf.tracem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author José Guadalupe Mandujano Serrano
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
		db.execSQL(MenuTable.CREATE_TABLE);
		db.execSQL(PartnerTable.CREATE_TABLE);
		db.execSQL(ScheduleTable.CREATE_TABLE);
		db.execSQL(OrdersTable.CREATE_TABLE);
		db.execSQL(OrderScheduleTable.CREATE_TABLE);
		db.execSQL(HeaderOrderTable.CREATE_TABLE);
		db.execSQL(OperationTable.CREATE_TABLE);
		db.execSQL(ComponentTable.CREATE_TABLE);
		db.execSQL(EquipmentTable.CREATE_TABLE);
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
		db.delete(MenuTable.TABLE_NAME, null, null);
		db.delete(EquipmentTable.TABLE_NAME, null, null);
		db.delete(OperationTable.TABLE_NAME, null, null);
		db.delete(ComponentTable.TABLE_NAME, null, null);
		db.delete(HeaderOrderTable.TABLE_NAME, null, null);
		db.delete(OrdersTable.TABLE_NAME, null, null);
		db.delete(ScheduleTable.TABLE_NAME, null, null);
		db.delete(PartnerTable.TABLE_NAME, null, null);
	}
}
