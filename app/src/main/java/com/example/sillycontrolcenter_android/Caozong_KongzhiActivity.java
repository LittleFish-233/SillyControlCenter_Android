package com.example.sillycontrolcenter_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Caozong_KongzhiActivity extends AppCompatActivity {
    Yonghu yonghu_ = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caozong__kongzhi);


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

        ((EditText)findViewById(R.id.editTextTextPersonName2)).setText(yonghu_.dangqian_kongzhi.Shuju.Zhujiming);
    }
    public void onClick_1(View view) {
        yonghu_.dangqian_kongzhi.Xiugai_mingzi(((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString(),yonghu_);
        finish();
    }
}