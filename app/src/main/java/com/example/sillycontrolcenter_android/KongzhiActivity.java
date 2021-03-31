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

public class KongzhiActivity extends AppCompatActivity {
    Timer timer = null;
    Yonghu yonghu_ = null;
    Lock lock = new ReentrantLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kongzhi);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("控制中心");


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
                            Kongzhi kongzhi =  yonghu_.dangqian_kongzhi;
                            ((TextView)findViewById(R.id.textView6)).setText(kongzhi.Shuju.Zhujiming);
                            ((TextView)findViewById(R.id.textView2)).setText("连接设备数："+String.valueOf(kongzhi.Shebei_Fus.size()));


                            //循环界面列表 查找不在的控制器将其移除
                            LinearLayout layout = findViewById(R.id.linear1);

                            for (int i=0;i<layout.getChildCount();i++) {
                                String weiyi = null;
                                boolean shifou = false;

                                //判断是否是控件
                                if (layout.getChildAt(i) instanceof card_kongzhi_) {
                                    card_kongzhi_ kongzhi_ui = (card_kongzhi_) layout.getChildAt(i);
                                    kongzhi_ui.gengxin();
                                    weiyi = kongzhi_ui.kongzhi_.Shuju.Weiyi_shibie;
                                } else {
                                    if (layout.getChildAt(i) instanceof card_pc_) {
                                        card_pc_ kongzhi_ui = (card_pc_) layout.getChildAt(i);
                                        kongzhi_ui.gengxin();
                                        weiyi = kongzhi_ui.shebei_pc.Shuju.Weiyi_shibie;
                                    } else {
                                        if (layout.getChildAt(i) instanceof card_ad_) {
                                            card_ad_ kongzhi_ui = (card_ad_) layout.getChildAt(i);
                                            kongzhi_ui.gengxin();
                                            weiyi = kongzhi_ui.shebei_ad.Shuju.Weiyi_shibie;
                                        } else {
                                            continue;
                                        }
                                    }

                                }

                                for (int k = 0; k < kongzhi.Shebei_Fus.size(); k++) {
                                    //找到则直接下一个
                                    if (weiyi == kongzhi.Shebei_Fus.get(k).Shuju.Weiyi_shibie) {
                                        shifou = true;

                                        break;
                                    }
                                }
                                //未找到匹配的
                                if (shifou == false) {
                                    layout.removeViewAt(i);
                                }
                            }
                            //循环后台控制器列表 查找不在ui的将其添加
                            for (int k = 0; k < kongzhi.Shebei_Fus.size(); k++)
                            {
                                boolean shifou = false;
                                for (int i=0;i<layout.getChildCount();i++) {
                                    String weiyi = null;
                                    //判断是否是控件
                                    if (layout.getChildAt(i) instanceof card_kongzhi_) {
                                        card_kongzhi_ kongzhi_ui = (card_kongzhi_) layout.getChildAt(i);
                                        weiyi = kongzhi_ui.kongzhi_.Shuju.Weiyi_shibie;
                                    } else {
                                        if (layout.getChildAt(i) instanceof card_pc_) {
                                            card_pc_ kongzhi_ui = (card_pc_) layout.getChildAt(i);
                                            weiyi = kongzhi_ui.shebei_pc.Shuju.Weiyi_shibie;
                                        } else {
                                            if (layout.getChildAt(i) instanceof card_ad_) {
                                                card_ad_ kongzhi_ui = (card_ad_) layout.getChildAt(i);
                                                weiyi = kongzhi_ui.shebei_ad.Shuju.Weiyi_shibie;
                                            } else {
                                                continue;
                                            }
                                        }
                                    }

                                    //找到则直接下一个
                                    if (weiyi == kongzhi.Shebei_Fus.get(k).Shuju.Weiyi_shibie) {
                                        shifou = true;
                                        break;
                                    }
                                }
                                if (shifou == false)
                                {
                                    //未找到匹配的
                                    switch (kongzhi.Shebei_Fus.get(k).Shuju.Shebei_shibie)
                                    {
                                        case 0:
                                            card_pc_ linshi = new card_pc_(getApplicationContext());

                                            linshi.setViewOnlickListener(this::onClick_3);
                                            linshi.chushihua((Shebei_PC) kongzhi.Shebei_Fus.get(k));
                                            linshi.gengxin();
                                            layout.addView(linshi);
                                            break;
                                        case 1:
                                            card_ad_ linshi_ = new card_ad_(getApplicationContext());

                                            linshi_.setViewOnlickListener(this::onClick_4);
                                            linshi_.chushihua((Shebei_AD) kongzhi.Shebei_Fus.get(k));
                                            linshi_.gengxin();
                                            layout.addView(linshi_);
                                            break;
                                    }


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
                        yonghu_.dangqian_pc=((card_pc_)view).shebei_pc;
                        Intent intent = new Intent(getApplicationContext(), PCActivity.class);
                        startActivity(intent);
                    }
                    private void onClick_4(View view) {
                        yonghu_.dangqian_ad=((card_ad_)view).shebei_ad;
                        Intent intent = new Intent(getApplicationContext(), ADActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }, 0, 1000);
    }

    public void onClick_1(View view) {
        yonghu_.Mianban_.Remove_fu(yonghu_.dangqian_kongzhi,yonghu_.Peizhi_,getApplicationContext());
        finish();
    }
    public void onClick_2(View view) {
        Intent intent = new Intent(getApplicationContext(), Caozong_KongzhiActivity.class);
        startActivity(intent);
    }
}