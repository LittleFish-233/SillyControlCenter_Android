package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCActivity extends AppCompatActivity {
    Timer timer = null;
    Yonghu yonghu_ = null;
    Lock lock = new ReentrantLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_c);

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
                            Shebei_PC shebei_pc =  yonghu_.dangqian_pc;
                            ((TextView)findViewById(R.id.textView5)).setText(shebei_pc.Shuju.Mingchen);
                            ((TextView)findViewById(R.id.textView8)).setText("处理器："+((Shuju_Shebei_PC)shebei_pc.Shuju).Cpu_info);
                            ((TextView)findViewById(R.id.textView10)).setText("显卡："+((Shuju_Shebei_PC)shebei_pc.Shuju).Gpu_info);
                            ((TextView)findViewById(R.id.textView9)).setText("内存："+((Shuju_Shebei_PC)shebei_pc.Shuju).Ram_info);
                            ((TextView)findViewById(R.id.textView7)).setText("系统："+((Shuju_Shebei_PC)shebei_pc.Shuju).Sys_info);

                            String str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Cpu_use);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView14)).setText(str+" %");

                            str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Cpu_wendu);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView16)).setText(str+" ℃");

                            str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Gpu_use);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView18)).setText(str+" %");

                            str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Gpu_wendu);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView20)).setText(str+" ℃");

                            str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Ram_use);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView22)).setText(str+" %");


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