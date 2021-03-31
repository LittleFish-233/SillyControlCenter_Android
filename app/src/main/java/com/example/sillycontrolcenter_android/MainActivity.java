package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    Timer timer = null;
    Yonghu yonghu_ = null;
    Lock lock = new ReentrantLock();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//land
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//port
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("主页");

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
                            Shuju_Shebei_AD shuju_shebei_ad = (Shuju_Shebei_AD) yonghu_.Mianban_.Shebei_dangqian.Shuju;
                            ((TextView)findViewById(R.id.textView6)).setText(shuju_shebei_ad.Mingchen);
                            String str = String.valueOf(shuju_shebei_ad.Dianliang*100);
                            str = str.substring(0,str.indexOf(".")+0);
                            ((TextView)findViewById(R.id.textView2)).setText("电量："+str+"%");

                            str = String.valueOf(shuju_shebei_ad.Dianchi_Wendu);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView)).setText("电池温度："+str+"℃");
                            str = String.valueOf(shuju_shebei_ad.Ram_use);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView4)).setText("内存占用率："+str+"%");
                             str = String.valueOf(shuju_shebei_ad.Hdd_use*100);
                            str = str.substring(0,str.indexOf(".")+2);
                            ((TextView)findViewById(R.id.textView3)).setText("储存占用率："+str+"%");


                            //循环界面列表 查找不在的控制器将其移除
                            LinearLayout layout = findViewById(R.id.linear1);
                            for (int i=0;i<layout.getChildCount();i++)
                            {
                                boolean shifou = false;
                                card_kongzhi_ kongzhi_ui = (card_kongzhi_) layout.getChildAt(i);


                                for (int k=0;k<yonghu_.Mianban_.Kongzhis.size();k++)
                                {
                                    //找到则直接下一个
                                    if (kongzhi_ui.kongzhi_.Shuju.Weiyi_shibie == yonghu_.Mianban_.Kongzhis.get(k).Shuju.Weiyi_shibie)
                                    {
                                        shifou = true;
                                        //更新
                                        kongzhi_ui.gengxin();
                                        break;
                                    }
                                }
                                //未找到匹配的
                                if (shifou == false)
                                {
                                    layout.removeViewAt(i);
                                }
                            }
                            //循环后台控制器列表 查找不在ui的将其添加
                            for (int i=0;i<yonghu_.Mianban_.Kongzhis.size();i++)
                            {
                                boolean shifou = false;
                                for (int k=0;k<layout.getChildCount();k++)
                                {
                                    card_kongzhi_ kongzhi_ui = (card_kongzhi_) layout.getChildAt(k);

                                    //找到则直接下一个
                                    if (kongzhi_ui.kongzhi_.Shuju.Weiyi_shibie == yonghu_.Mianban_.Kongzhis.get(i).Shuju.Weiyi_shibie)
                                    {
                                        shifou = true;
                                    }
                                }
                                if (shifou == false)
                                {
                                    //未找到匹配的
                                    card_kongzhi_ linshi = new card_kongzhi_(getApplicationContext());
                                    linshi.setViewOnlickListener(this::onClick_3);
                                    linshi.chushihua(yonghu_.Mianban_.Kongzhis.get(i));
                                    linshi.gengxin();
                                    layout.addView(linshi);
                                }
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        finally {
                            lock.unlock();
                        }
                    }

                    private void onClick_3(View view) {
                        yonghu_.dangqian_kongzhi=((card_kongzhi_)view).kongzhi_;
                        Intent intent = new Intent(getApplicationContext(), KongzhiActivity.class);
                        startActivity(intent);
                    }

                });
            }
        }, 0, 1000);

        //启动后台任务 监听消息 发送数据
        Intent localIntent = new Intent();
        localIntent.setClass(this, Shuju_Update_Server.class); // 销毁时重新启动Service
        this.startService(localIntent);

        LinearLayout layout = findViewById(R.id.linear1);
        card_kongzhi_ cardKongzhi=new card_kongzhi_(getApplicationContext());
        layout.addView(cardKongzhi);
    }

    //添加控制中心连接
    public void onClick_1(View view) {
        Intent intent = new Intent(this, LianjieActivity.class);
        startActivity(intent);
    }
    //设置
    public void onClick_2(View view) {
        Intent intent = new Intent(this, ShezhiActivity.class);
        startActivity(intent);
    }

}