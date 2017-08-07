package com.meitu.test.sqlitedemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.meitu.test.provider.DataHandle;

public class Holder extends RelativeLayout  {
	private ImageView mIcon;
	TextView name;
	Switch switchState;
	Context mContext;
	private String packageName;
  

	public Holder(Context context) {
        super(context);
        
    }

    public Holder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Holder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
	
    public void setContext(Context context) {
    	mContext = context;
    }
    
    @Override
    protected void onFinishInflate() {
    	super.onFinishInflate();
		mIcon = (ImageView)findViewById(R.id.application_logo);
		name = (TextView)findViewById(R.id.application_name);
		switchState = (Switch)findViewById(R.id.passwordswitch);
		switchState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				DataHandle mDataHandle = new DataHandle(mContext);
//				String appName = (String) name.getText();
				String packageName = getPackageName();
				if (isChecked) {
					Log.i("HERE", "Here package" + packageName);
					if (!mDataHandle.queryPackageName(packageName)) {
						Log.i("HERE", "Here in database");
						mDataHandle.saveSwitchState(mContext, packageName, isChecked);
					}
				}else {
					if (mDataHandle.queryPackageName(packageName)) {
						Log.i("Here", "Here remove");
						mDataHandle.deleteData(mContext, packageName);
					}
				}
			}
		});
    }
 
	public void setSwitchState(boolean isChecked) {
		switchState.setChecked(isChecked);
	}
	public void setNameIcon(String appName, Drawable icon) {
		name.setText(appName);
		mIcon.setImageDrawable(icon);
	}
	public void setPackageName(String packageName){
		this.packageName = packageName; 
	}

	public String getPackageName() {
		return packageName;
	}

}