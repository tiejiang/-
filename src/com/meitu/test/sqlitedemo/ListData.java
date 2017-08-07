package com.meitu.test.sqlitedemo;

import com.meitu.test.provider.DBHelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ListData extends Activity{
	private ListView listview;
	private DBHelper dbHelper;
	private SQLiteDatabase sqlDB;
	private Cursor mCursor;
	private CursorAdapter mCursorAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenditure);
		listview = (ListView)findViewById(R.id.listview);
		showListData();
	}
	public void showListData(){
		dbHelper = new DBHelper(ListData.this);
		sqlDB = dbHelper.getWritableDatabase();
		String[] colums = new String[] {"_id", DBHelper.COL_PACKAGENAME, DBHelper.COL_SWITCH_STATE};
		mCursor = sqlDB.query(DBHelper.TABLE_NAME, colums, null, null, null, null, null);
		String[] headers = new String[] {DBHelper.COL_PACKAGENAME, DBHelper.COL_SWITCH_STATE};
		mCursorAdapter = new SimpleCursorAdapter(this, R.layout.expenditure_item, mCursor, headers, 
				new int[]{R.id.text1, R.id.text2});
		listview.setAdapter(mCursorAdapter);
	}
	
}
