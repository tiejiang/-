package com.meitu.test.sqlitedemo;

import com.meitu.test.provider.DataHandle;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

public class ApplicationsActivity extends Activity {
	
	private ApplicationsFragment mApplicationList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        
        ApplicationsFragment mApplicationsFragment = new ApplicationsFragment();
		getFragmentManager().beginTransaction()
		.replace(R.id.container, new ApplicationsFragment()).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 if (item.getItemId() == android.R.id.home) {
			
			finish();
			return true;
		}
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof ApplicationsFragment) {
			mApplicationList = (ApplicationsFragment)fragment;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
