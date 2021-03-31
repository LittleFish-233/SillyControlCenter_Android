package com.example.sillycontrolcenter_android;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class myReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ComponentName name=new ComponentName(context.getApplicationContext(), Shuju_Update_Server.class);
        if(serverIsRunning(context,name)==false)
            context.startService(new Intent(context, Shuju_Update_Server.class));
    }

    private boolean serverIsRunning(Context context, ComponentName componentName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> runningServices
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() <= 0) {
            return false;
        }

        for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
            if (componentName.equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
