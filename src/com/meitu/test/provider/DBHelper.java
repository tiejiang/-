package com.meitu.test.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applist.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME = "application_list";
	public static final String COL_PACKAGENAME = "application_package_name";
	public static final String COL_SWITCH_STATE = "application_switch_state";
	
	public static final String AUTOHORITY = "com.meitu.mobile.provider";
	public static final int ITEM_ID = 1;
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/application_list");
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "
			+ TABLE_NAME
			+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_PACKAGENAME + " TEXT, "
			+ COL_SWITCH_STATE + " NUMERIC"
			+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
}
