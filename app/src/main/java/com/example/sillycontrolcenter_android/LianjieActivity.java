package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LianjieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lianjie);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("连接");
    }

    public void onClick_1(View view) throws Exception {
        try {

            //初始化
            Yonghu yonghu_ = (Yonghu) this.getApplication();
            if (!yonghu_.shifou_chushihua) {
                yonghu_.chushihua(this);
                yonghu_.shifou_chushihua = true;
            }
            Fuwuqi_ip fuwuqi_ip=new Fuwuqi_ip();
            fuwuqi_ip.Ip=(String) ((TextView)findViewById(R.id.editTextTextPersonName2)).getText().toString();
            fuwuqi_ip.Daunkou=Integer.parseInt(((TextView)findViewById(R.id.editTextTextPersonName)).getText().toString());
            yonghu_.Mianban_.Add_kongzhi(fuwuqi_ip,yonghu_.Peizhi_,yonghu_);
            yonghu_.Peizhi_.Add_ip(fuwuqi_ip,getApplicationContext());

            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

