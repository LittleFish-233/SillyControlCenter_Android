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

public class card_pc_ extends FrameLayout {

    public Shebei_PC shebei_pc=null;
    Timer timer;
    View mView;

    private OnClickListener onClickListener;

    public card_pc_(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public card_pc_(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(R.layout.card_pc, this, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public card_pc_(@NonNull Context context) {
        this(context, null);
    }

    public void chushihua(Shebei_PC shebei_pc_)
    {
        shebei_pc=shebei_pc_;
    }
    public void gengxin()
    {
        ((TextView)findViewById(R.id.textView5)).setText(shebei_pc.Shuju.Mingchen);
        String str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Cpu_use);
        str = str.substring(0,str.indexOf(".")+2);
        ((TextView)findViewById(R.id.textView8)).setText("CPU利用率："+str+" %");

        str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Cpu_wendu);
        str = str.substring(0,str.indexOf(".")+2);
        ((TextView)findViewById(R.id.textView10)).setText("CPU温度："+str+" ℃");

        str = String.valueOf(((Shuju_Shebei_PC)shebei_pc.Shuju).Ram_use);
        str = str.substring(0,str.indexOf(".")+2);
        ((TextView)findViewById(R.id.textView9)).setText("内存使用率："+str+" %");

        ((TextView)findViewById(R.id.textView7)).setText("系统正常运行时间："+((Shuju_Shebei_PC)shebei_pc.Shuju).Sys_time);
    }
    public void setViewOnlickListener(OnClickListener onlickListener) {
        this.onClickListener = onlickListener;
        //CardView cardView=findViewById(R.id.card_view_2);
        mView.setClickable(true);
        mView.setOnClickListener(onlickListener);
    }

}
