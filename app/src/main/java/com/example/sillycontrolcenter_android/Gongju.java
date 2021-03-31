package com.example.sillycontrolcenter_android;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gongju {

    static ActivityManager mActivityManager;

    public static Shuju_Shebei_AD Huoqu_shebei_xinxi(Yonghu yonghu_,Context context) {

        try {Shuju_Shebei_AD shuju_shebei_ad = new Shuju_Shebei_AD();
            shuju_shebei_ad.Dianliang = yonghu_.receiver.bat;
            shuju_shebei_ad.Dianchi_Wendu=yonghu_.receiver.tem;
            shuju_shebei_ad.Ram_use= getPenter(context);
            shuju_shebei_ad.Hdd_use= getAvailSpace(Environment.getDataDirectory().getAbsolutePath());

            return shuju_shebei_ad;
        } catch (Exception e) {
            e.printStackTrace();
            return  new Shuju_Shebei_AD();
        }

    }
    public static float getPenter(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;

            return ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }
    public  static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }
    public static void showAvailableSize(){

        float romSize =getAvailSpace(Environment.getDataDirectory().getAbsolutePath());//手机内部存储大小


        float sdSize =getAvailSpace(Environment.getExternalStorageDirectory().getAbsolutePath());//外部存储大小

    }

    /**

      * 获取某个目录的可用空间

      */

    public static float getAvailSpace(String path){

        StatFs statfs = new StatFs(path);


        long count = statfs.getFreeBytes();//获取可用分区块的个数

        long count_s=statfs.getTotalBytes();

        return 1- (float)count/count_s;

    }

}