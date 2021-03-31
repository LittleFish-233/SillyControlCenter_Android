package com.example.sillycontrolcenter_android;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Timer;

public class card_ad_ extends FrameLayout {

    public Shebei_AD shebei_ad=null;
    Timer timer;
    View mView;

    private OnClickListener onClickListener;

    public card_ad_(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public card_ad_(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(R.layout.card_ad, this, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public card_ad_(@NonNull Context context) {
        this(context, null);
    }

    public void chushihua(Shebei_AD shebei_ad_)
    {
        shebei_ad=shebei_ad_;
    }
    public void gengxin()
    {
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
    public void setViewOnlickListener(OnClickListener onlickListener) {
        this.onClickListener = onlickListener;
        // cardView=findViewById(R.id.card_view_2);
        mView.setClickable(true);
        mView.setOnClickListener(onlickListener);
    }

}
