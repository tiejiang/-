package com.meitu.test.sqlitedemo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.meitu.test.provider.DataHandle;

@SuppressLint("NewApi") 
public class ApplicationsFragment extends Fragment {
	
	private Activity mActivity;
	private ListView mListView;
	private ApplicationsAdapter mApplicationsAdapter;
	private boolean mLoading;
	private static Vector<String> mPackageNameList;
	private static Vector<String> mDeletePackageNameList;
	
	@Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	mActivity = activity;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.fragment_application_list, null);
		mListView = (ListView) root.findViewById(R.id.app_list);
		Button button = (Button)root.findViewById(R.id.resetpassword);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), ListData.class);
				startActivity(intent);
//				mActivity.finish();
			}
		});
		return root;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		loadInstallApps();
	}
	
	private void loadInstallApps() {
		if (mLoading) {
			return ;
		}
		mLoading = true;
		new LoadInstallApps().execute();
	}
	
	private class LoadInstallApps extends AsyncTask<Void, Void, SparseArray<AppInfo>> {
		@Override
		protected SparseArray<AppInfo> doInBackground(Void... params) {
			Intent intent = new Intent(Intent.ACTION_MAIN, null);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			List<ResolveInfo> apps = mActivity.getPackageManager().queryIntentActivities(intent, 0);
			SparseArray<AppInfo> appInfos = new SparseArray<AppInfo>();
			SparseArray<AppInfo> appInfosMoment = new SparseArray<AppInfo>();
			
			AppInfo appInfo = null;
			
			DataHandle mDataHandle = new DataHandle(mActivity);
			mPackageNameList = new Vector<String>();
			
			for (ResolveInfo info : apps) {
				appInfo = new AppInfo();
				appInfo.packageName = info.activityInfo.packageName;
				appInfo.appName = info.loadLabel(mActivity.getPackageManager()).toString();
				appInfo.icon = info.loadIcon(mActivity.getPackageManager());
				
				String packageName = appInfo.packageName;
				if (mDataHandle.queryPackageName(packageName)) {
					mPackageNameList.add(packageName);
					appInfos.put(appInfos.size(), appInfo);
				}
				else {
					appInfosMoment.put(appInfosMoment.size(), appInfo);
				}
				
//				appInfos.put(appInfos.size(), appInfo);
				
			}
			for (int i = 0; i < appInfosMoment.size(); i++) {
				appInfo = new AppInfo();
				appInfo.packageName = appInfosMoment.get(i).packageName;
				appInfo.appName = appInfosMoment.get(i).appName;
				appInfo.icon = appInfosMoment.get(i).icon;
				appInfos.put(mPackageNameList.size() + i, appInfo);
			}
			
			return appInfos;
		}
		
		@Override
        protected void onPostExecute(SparseArray<AppInfo> appList) {
			if (appList != null && appList.size() > 0) {
				if (mApplicationsAdapter == null) {
					mApplicationsAdapter = new ApplicationsAdapter(mActivity);
					mListView.setAdapter(mApplicationsAdapter);
				}
				mApplicationsAdapter.bindInstallApps(appList);
			} else {
				Toast.makeText(mActivity, "ц╩сп╪сть",Toast.LENGTH_SHORT).show();
			}
			mLoading = false;
		}
	}
	
	private class AppInfo {
		String packageName;
		String appName;
		Drawable icon;
//		boolean isChecked;
	}

	public static class ApplicationsAdapter extends BaseAdapter {
		
		private Context mContext;
		private LayoutInflater mInflater;
		private SparseArray<AppInfo> mInstallApps;		
//		private Vector<String> mOpenedPackages;
		public static HashMap<Integer, String> mOpenedPackages ;
//		private Cursor mCursor;
		
		public ApplicationsAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
//			mOpenedPackages = new Vector<String>();
			mOpenedPackages = new HashMap<Integer,String>();
			mDeletePackageNameList = new Vector<String>();
		}

		@Override
		public int getCount() {
			if (mInstallApps == null) {
				return 0;
			} else {
				return mInstallApps.size();
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			Log.i("Item", "item " + String.valueOf(position));
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			Log.i("ItemId", "ItemId " + String.valueOf(position));
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.application_list_item, null);
				Holder holder = new Holder(convertView);
				convertView.setTag(holder);
			}
			Holder holder = (Holder)convertView.getTag();
			AppInfo appInfo = mInstallApps.get(position);
			holder.icon.setImageDrawable(appInfo.icon);
			holder.name.setText(appInfo.appName);
			holder.packageName.setText(appInfo.packageName);
			
			log(holder);
			holder.switchState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					String packageName = mInstallApps.get(position).packageName;
					if (isChecked) {
						mOpenedPackages.put(position, packageName);	
					}else {
						mOpenedPackages.remove(position);
						mDeletePackageNameList.add(packageName);
					}
				}
			});
			
//			String packageName = mInstallApps.get(position).packageName;
			String packageName = appInfo.packageName;
			
			if (mPackageNameList.contains(packageName)) {
				holder.switchState.setChecked(true);
				mOpenedPackages.put(position, packageName);
				mPackageNameList.remove(packageName);
			}
			if (mOpenedPackages.get(position) != null) {
				holder.switchState.setChecked(true);
			} else {
				holder.switchState.setChecked(false);
				
			}
//			holder.switchState.setChecked(mOpenedPackages.get(position) == null ? false : true);
			return convertView;
		}
		
		public class Holder {
			ImageView icon;
			TextView name;
			TextView packageName;
			Switch switchState;
			
			public Holder(View view) {
				icon = (ImageView)view.findViewById(R.id.application_logo);
				name = (TextView)view.findViewById(R.id.application_name);
				packageName = (TextView)view.findViewById(R.id.package_name);
				switchState = (Switch)view.findViewById(R.id.passwordswitch);
			}
		}
		/**
		 * @param apps
		 */
		public void bindInstallApps(SparseArray<AppInfo> apps) {
			mInstallApps = apps;
			this.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Toast.makeText(mActivity, "onpause", Toast.LENGTH_SHORT).show();
		savePackageName();
	}
	
	public void savePackageName(){
		DataHandle mDataHandle = new DataHandle(mActivity);
		HashMap<Integer, String> mOpenedPackages = new HashMap<Integer, String>();
		mOpenedPackages = ApplicationsAdapter.mOpenedPackages;
		
		Iterator iter = mOpenedPackages.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String packageName = (String) entry.getValue();
			if (packageName != null && !mDataHandle.queryPackageName(packageName)) {
				mDataHandle.saveSwitchState(mActivity, packageName, true);
			}
		}
		
//		for (int i = 0; i < mOpenedPackages.size(); i++) {
//			
//			String packageName = mOpenedPackages.get(i);
//			if (packageName != null && !mDataHandle.queryPackageName(packageName)) {
//				mDataHandle.saveSwitchState(mActivity, packageName, true);
//			}
//		}
		for (int i = 0; i < mDeletePackageNameList.size(); i++) {
			String packageName = mDeletePackageNameList.get(i);
			if (mDataHandle.queryPackageName(packageName)) {
				mDataHandle.deleteData(packageName);
			}
		}
		ApplicationsAdapter.mOpenedPackages.clear();
		mOpenedPackages.clear();
		mDeletePackageNameList.clear();
	}
	
	private static final String TAG = ApplicationsFragment.class.getSimpleName();
	private static void log(Object msg) {
		Log.d(TAG, msg + "");
	}

}
