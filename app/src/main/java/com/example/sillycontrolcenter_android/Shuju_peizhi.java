package com.example.sillycontrolcenter_android;
import java.util.LinkedList;
import java.util.Random;

/// <summary>
/// 配置信息
/// </summary>
public class Shuju_peizhi {
    private String Zhujiming = "";

    /// <summary>
    /// 主机名称
    /// </summary>
    public String getZhujiming() {
        if (Zhujiming.equals("")) {
            Zhujiming = android.os.Build.MODEL;
        }
        return Zhujiming;
    }

    public void setZhujiming(String value) {
        Zhujiming = value;
    }

    private String Weiyi_shibie = "";
    /// <summary>
    /// 唯一识别号 16位数字
    /// </summary>
    ///

    public String getWeiyi_shibie() {

        if (Weiyi_shibie.equals("")) {
            Random rd = new Random();

            Weiyi_shibie = String.valueOf((rd.nextInt(9999999) + 10000000)) + String.valueOf((rd.nextInt(9999999) + 10000000));
        }
        return Weiyi_shibie;
    }

    public void setWeiyi_shibie(String value) {
        Weiyi_shibie = value;
    }

    /// <summary>
    /// 控制中心列表
    /// </summary>
    public LinkedList<Fuwuqi_ip> Fuwuqi_Ips = new LinkedList<Fuwuqi_ip>();

    /// <summary>
    /// 背景图片
    /// </summary>
    public String Beijing = "1";

    /// <summary>
    /// 主机头像
    /// </summary>
    public String Touxiang = "1";

}
