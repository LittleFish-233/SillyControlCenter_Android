package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShezhiActivity extends AppCompatActivity {
    Yonghu yonghu_ = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);


        ActionBar bar = getSupportActionBar();
        bar.setTitle("设置");

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

        ((EditText)findViewById(R.id.editTextTextPersonName2)).setText(yonghu_.Peizhi_.Shuju.getZhujiming());
    }


    public void onClick_1(View view) {
        yonghu_.Peizhi_.Shuju.setZhujiming(((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString());
        yonghu_.Peizhi_.Baocun_jiben(getApplicationContext());
        finish();
    }
}