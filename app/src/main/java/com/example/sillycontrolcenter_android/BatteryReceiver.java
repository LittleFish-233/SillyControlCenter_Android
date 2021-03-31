package com.example.sillycontrolcenter_android;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.os.BatteryManager.EXTRA_TEMPERATURE;

public class BatteryReceiver extends BroadcastReceiver {

    public float bat=0;
    public float tem=0;

    public BatteryReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int current = intent.getExtras().getInt("level");// 获得当前电量
        int total = intent.getExtras().getInt("scale");// 获得总电量
        int temperature = intent.getIntExtra(EXTRA_TEMPERATURE, -1);

        tem=(float) temperature/10;
        bat= (float) current  / total;
    }
}
