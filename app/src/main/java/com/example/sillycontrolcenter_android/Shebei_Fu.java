package com.example.sillycontrolcenter_android;
import android.content.Context;

import java.io.IOException;

public abstract class Shebei_Fu
{
    /// <summary>
    /// 数据
    /// </summary>
    public Shuju_Shebei Shuju = new Shuju_Shebei();


    protected boolean shifou_zhengzai = false;

    /// <summary>
    /// 初始化设备
    /// </summary>
    /// <param name="weiyi_shibie">唯一识别号</param>
    /// <param name="shuju_xin">新数据</param>
    public abstract void Chushihua(String weiyi_shibie,Context context);

    /// <summary>
    /// 更新设备数据
    /// </summary>
    /// <param name="shuju_xin"></param>
    public abstract void Gengxin(String shuju_xin);

    /// <summary>
    /// 保存
    /// </summary>
    public abstract void Baocun(Context context) throws IOException;

    /// <summary>
    /// 读取
    /// </summary>
    /// <returns>是否成功</returns>
    public abstract void Duqu(Context context);

}
