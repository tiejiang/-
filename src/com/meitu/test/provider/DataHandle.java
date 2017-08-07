package com.meitu.test.provider;

import java.util.Vector;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;

public class DataHandle {
	private DBHelper dbHelper;
	private SQLiteDatabase sqlDb;
	private Cursor mCursor;
	private CursorAdapter mCursorAdapter;
	private Vector<String> mPackageName;
	private ContentResolver mContentResolver;
	
	public DataHandle(Context context){
		dbHelper = new DBHelper(context);
		sqlDb = dbHelper.getReadableDatabase();
	}
	//insert
	public void saveSwitchState(Context context, String packageName, boolean state){
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.COL_PACKAGENAME, packageName);
		cv.put(DBHelper.COL_SWITCH_STATE, state);
		sqlDb.insert(DBHelper.TABLE_NAME, null, cv);
	}
	
	//delete
	public void deleteData(Context context, String packageName){
		String whereClause = "application_package_name=?";
		String[] whereArgs = {packageName};
		sqlDb.delete("application_list",whereClause,whereArgs);
	}
	
	//delete
	public void deleteData(String packageName){
		String whereClause = "application_package_name=?";
		String[] whereArgs = {packageName};
		sqlDb.delete("application_list",whereClause,whereArgs);
	}
	
	//query
	public boolean queryPackageName(String packageName){
		boolean isSameName = false;
//		mCursor = sqlDb.query(DBHelper.TABLE_NAME, new String[]{DBHelper.COL_PACKAGENAME}, "name like ?", new String[]{"%ljq%"}, null, null, null, null);
		mCursor = sqlDb.rawQuery("select * from application_list", null);
		while (mCursor.moveToNext()) {
		   int indexNum = mCursor.getInt(0); //获取第一列的值
		   String name = mCursor.getString(1);
//		   String name1 = mCursor.getString(2);
		   if (packageName.equals(name)) {
				mCursor.close();
			   return true;
		   }
		}
		mCursor.close();
		return false;
	}
	
	//query
	public Vector<String> loadPackageName(){
		Vector<String> mPackageName = new Vector<String>();
		mCursor = sqlDb.rawQuery("select * from application_list", null);
		while (mCursor.moveToNext()) {
		   String name = mCursor.getString(1);
		   mPackageName.add(name);
		}
		mCursor.close();
		return mPackageName;
	}
	
	
	public String showListData(Context context, int position){
		String[] colums = new String[] {"_id", DBHelper.COL_PACKAGENAME, DBHelper.COL_SWITCH_STATE};
		mCursor = sqlDb.query(DBHelper.TABLE_NAME, colums, null, null, null, null, null);
		if (mCursor.moveToFirst()) {
			String packageName = mCursor.getString(position);
			return packageName;
		}
		return null;
	}
	
//	public void queryData(){
//    	mContentResolver = this..getContentResolver();
//        mCursor = mContentResolver.query(CONTENT_URI, new String[]{"application_package_name"}, 
//        		null, null, null);
//        while (mCursor.moveToNext()) {
//        	Toast.makeText(this, mCursor.getString(1), Toast.LENGTH_SHORT).show();
//		}
//        
//    }
	
}
