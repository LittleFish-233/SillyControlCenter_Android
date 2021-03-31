package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ADActivity extends AppCompatActivity {
    Timer timer = null;
    Yonghu yonghu_ = null;
    Lock lock = new ReentrantLock();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d);


        ActionBar bar = getSupportActionBar();
        bar.setTitle("详细信息");


        //初始化
        try {
            yonghu_ = (Yonghu) this.getApplication();
            if (!yonghu_.shifou_chushihua) {
                yonghu_.chushihua(this);
                yonghu_.shifou_chushihua = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //启动定时器 更新UI
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void  run() {
                        lock.lock();
                        try
                        {
                            //刷新前台数据
                            Shebei_AD shebei_ad =  yonghu_.dangqian_ad;
                            ((TextView)findViewById(R.id.textView5)).setText(shebei_ad.Shuju.Mingchen);
                            String str = String.valueOf(((Shuju_Shebei_AD)shebei_ad.Shuju).Dianliang*100);
                            str = str.substring(0,str.indexOf(".")+0);
                            ((TextView)findViewById(R.id.textView8)).setText("电量："+str+"%");

                            str = String.valueOf(((Shuju_Shebei_AD)shebei_ad.Shuju).Dianchi_Wendu);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView10)).setText("电池温度："+str+"℃");
                            str = String.valueOf(((Shuju_Shebei_AD)shebei_ad.Shuju).Ram_use);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView9)).setText("内存占用率："+str+"%");
                            str = String.valueOf(((Shuju_Shebei_AD)shebei_ad.Shuju).Hdd_use*100);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView7)).setText("储存占用率："+str+"%");

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        finally {
                            lock.unlock();
                        }
                    }
                });
            }
        }, 0, 1000);
    }
}