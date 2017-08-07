package com.meitu.test.receiver.sqlitedemo.receiver;


import com.meitu.test.provider.DataHandle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	
        PackageManager manager = context.getPackageManager();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
//            Toast.makeText(context, "��װ�ɹ�"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            
            DataHandle mDataHandle = new DataHandle(context);
            mDataHandle.deleteData(context, packageName);
            
//            Toast.makeText(context, "ж�سɹ�"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
//            Toast.makeText(context, "�滻�ɹ�"+packageName, Toast.LENGTH_LONG).show();
        }
        

    }
    
}
