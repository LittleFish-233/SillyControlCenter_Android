package com.example.sillycontrolcenter_android;
import android.content.Context;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Objects;

public class Kongzhi
{
    /// <summary>
    /// 数据
    /// </summary>
    public Shuju_kongzhi Shuju = new Shuju_kongzhi();
    public Fuwuqi_ip Fuwuqi_Ip_ ;
    /// <summary>
    /// 设备列表
    /// </summary>
    public LinkedList<Shebei_Fu> Shebei_Fus = new LinkedList<Shebei_Fu>();
    /// <summary>
    /// 初始化
    /// </summary>
    /// <param name="shuju_Kongzhi"></param>
    public void chushihua(Shuju_kongzhi shuju_Kongzhi,Fuwuqi_ip fuwuqi_Ip)
    {
        Shuju = shuju_Kongzhi;
        Fuwuqi_Ip_ = fuwuqi_Ip;
    }
    /// <summary>
    /// 更新设备数据
    /// </summary>
    /// <param name="json_str">包含所有设备数据的外层JSON</param>
    public void Gengxin(Shuju_chuang shuju_Chuang, Context context) {
        //更新控制中心数据
        Shuju.Zhujiming = shuju_Chuang.Kongzhi.Zhujiming;
        //更新PC设备数据
        for (int i = 0; i < shuju_Chuang.shuju_Shebei_PCs.size(); i++) {
            Shebei_PC shebei_PC = null;
            //根据唯一识别号匹配设备
            for (int k = 0; k < Shebei_Fus.size(); k++) {
                if (Objects.equals(Shebei_Fus.get(k).Shuju.Weiyi_shibie, shuju_Chuang.shuju_Shebei_PCs.get(i).Weiyi_shibie)) {
                    shebei_PC = (Shebei_PC) Shebei_Fus.get(k);
                    break;
                }
            }

            //未匹配到设备
            if (shebei_PC == null) {
                shebei_PC = new Shebei_PC();
                shebei_PC.Chushihua(shuju_Chuang.shuju_Shebei_PCs.get(i).Weiyi_shibie, context);
                Shebei_Fus.add(shebei_PC);
            }

            shebei_PC.Gengxin(shuju_Chuang.shuju_Shebei_PCs.get(i));
        }

        //更新AD设备数据
        for (int i = 0; i < shuju_Chuang.shuju_Shebei_ADs.size(); i++) {
            Shebei_AD shebei_AD = null;
            //根据唯一识别号匹配设备
            for (int k = 0; k < Shebei_Fus.size(); k++) {
                if (Objects.equals(Shebei_Fus.get(k).Shuju.Weiyi_shibie, shuju_Chuang.shuju_Shebei_ADs.get(i).Weiyi_shibie)) {
                    shebei_AD = (Shebei_AD) Shebei_Fus.get(k);
                    break;
                }
            }

            //未匹配到设备
            if (shebei_AD == null) {
                shebei_AD = new Shebei_AD();
                shebei_AD.Chushihua(shuju_Chuang.shuju_Shebei_ADs.get(i).Weiyi_shibie, context);
                Shebei_Fus.add(shebei_AD);
            }

            shebei_AD.Gengxin(shuju_Chuang.shuju_Shebei_ADs.get(i));
        }

        //删除已经断开连接设备
        for (int k = 0; k < Shebei_Fus.size(); k++) {
            boolean shifou_cunzai=false;
            //根据唯一识别号匹配设备
            for (int i = 0; i < shuju_Chuang.shuju_Shebei_PCs.size(); i++) {
                if (Objects.equals(Shebei_Fus.get(k).Shuju.Weiyi_shibie, shuju_Chuang.shuju_Shebei_PCs.get(i).Weiyi_shibie)) {
                    shifou_cunzai=true;
                    break;
                }
            }
            for (int i = 0; i < shuju_Chuang.shuju_Shebei_ADs.size(); i++) {
                if (Objects.equals(Shebei_Fus.get(k).Shuju.Weiyi_shibie, shuju_Chuang.shuju_Shebei_ADs.get(i).Weiyi_shibie)) {
                    shifou_cunzai=true;
                    break;
                }
            }
            //未匹配到设备
            if (shifou_cunzai == false) {
                Shebei_Fus.remove(k);
            }
        }
    }
    /// <summary>
    /// 发送命令
    /// </summary>
    public void Send_Mingling(Mingling_Fu mingling_Fu,Yonghu yonghu_) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送当前设备数据
                String jieguo = null;
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    StringWriter stringWriter = new StringWriter();
                    JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
                    objectMapper.writeValue(jsonGenerator, mingling_Fu);
                    jsonGenerator.close();
                    jieguo = stringWriter.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //发送当前设备数据
                SendMethod sendMethod = new SendMethod(yonghu_.datagramSocket);

                try {
                    sendMethod.sendMessage(jieguo, Fuwuqi_Ip_.Ip, Fuwuqi_Ip_.Daunkou);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /// <summary>
    /// 修改控制中心名称
    /// </summary>
    public void Xiugai_mingzi(String new_name,Yonghu yonghu_)
    {
        Mingling_Fu mingling_Fu = new Mingling_Fu();
        mingling_Fu.Mingling = "修改主机名";
          mingling_Fu.Canshu = new_name;
        try {
            Send_Mingling(mingling_Fu,yonghu_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
