package com.meitu.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PackageNameProvider extends ContentProvider{
	private DBHelper mDBHelper;
	private SQLiteDatabase mSQLiteDatabase;
	private UriMatcher mUriMatcher;
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mDBHelper = new DBHelper(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor mCursor = null;
		mSQLiteDatabase = mDBHelper.getWritableDatabase();
//		if (mUriMatcher.match(uri)==DBHelper.ITEM_ID) {
			mCursor = mSQLiteDatabase.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
//		}
		
		return mCursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
