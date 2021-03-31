package com.example.sillycontrolcenter_android;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.ACTIVITY_SERVICE;

public class Shuju_Update_Server extends Service {
    private Timer timer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //创建一个通知管理器
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //        获取Notification实例
            Notification notification = new NotificationCompat.Builder(this, "123456789")
                    .setContentTitle("Silly Control Center 挂件")
                    .setContentText("此通知确保后台更新正常启动")
                    .setWhen(System.currentTimeMillis())
                    //             .setAutoCancel(false) 点击通知栏后是否消失
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bilibili_ico))
                    // .setCustomContentView(remoteView) // 设置自定义的RemoteView，需要API最低为24
                    .setSmallIcon(R.drawable.bilibili_ico)
                    // 设置点击通知栏后跳转地址
                    .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                    .build();
//        添加渠道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("123456789", "subscribeName", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("description");
                notificationManager.createNotificationChannel(channel);
            }
            // 设置常驻 Flag
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            //展示通知栏
            notificationManager.notify(123, notification);
            startForeground(123, notification);

            IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
            localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
            myReceiver searchReceiver = new myReceiver();
            registerReceiver(searchReceiver, localIntentFilter);
            return START_STICKY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();//更新时间
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //获取基类
                Yonghu yonghu_ = (Yonghu) (getApplicationContext());
                if (!yonghu_.shifou_chushihua) {
                    try {
                        yonghu_.chushihua(getApplicationContext());
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    yonghu_.shifou_chushihua = true;
                }

                try
                {
                    //刷新本地数据
                    yonghu_.Mianban_.Gengxin_bendi(yonghu_.Peizhi_,getApplicationContext(),yonghu_);
                    //向控制中心发送数据
                    yonghu_.Mianban_.Send_shuju(yonghu_.Peizhi_.Shuju.Fuwuqi_Ips,yonghu_);
                }
                catch (Exception exc)
                {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, "获取信息失败，详细信息<" + exc.getMessage() + ">", duration);
                    toast.show();
                    exc.printStackTrace();
                }
            }
        }, 0, 1000);

        //启动监听
        new Thread(new Runnable(){
            @Override
            public void run() {
                Yonghu yonghu_ = (Yonghu) (getApplicationContext());
                if (!yonghu_.shifou_chushihua) {
                    try {
                        yonghu_.chushihua(getApplicationContext());
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    yonghu_.shifou_chushihua = true;
                }
                yonghu_.Mianban_.Udp_jianting(getApplicationContext(),yonghu_);
            }
        }).start();


    }

    public void update(int appWidgetId,Context context)
    {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, Shuju_Update_Server.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }
}
