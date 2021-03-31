package com.example.sillycontrolcenter_android;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Timer;
import java.util.TimerTask;

public class card_kongzhi_ extends FrameLayout {

    public Kongzhi kongzhi_=null;
    Timer timer;
    View mView;

    private OnClickListener onClickListener;

    public card_kongzhi_(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public card_kongzhi_(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(R.layout.card_kongzhi, this, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public card_kongzhi_(@NonNull Context context) {
        this(context, null);
    }

    public void chushihua(Kongzhi kongzhi)
    {
        kongzhi_=kongzhi;
    }
    public void gengxin()
    {
        ((TextView)findViewById(R.id.textView5)).setText(kongzhi_.Shuju.Zhujiming);
        ((TextView)findViewById(R.id.textView8)).setText(String.valueOf("连接设备数："+kongzhi_.Shebei_Fus.size()));
    }
    public void setViewOnlickListener(OnClickListener onlickListener) {
        this.onClickListener = onlickListener;
       // CardView cardView=findViewById(R.id.card_view_2);
        mView.setClickable(true);
        mView.setOnClickListener(onlickListener);
    }

}
