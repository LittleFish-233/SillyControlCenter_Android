package com.example.sillycontrolcenter_android;
import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class Mianban
{

    /// <summary>
    /// 控制中心列表
    /// </summary>
    public LinkedList<Kongzhi> Kongzhis = new LinkedList<Kongzhi>();

    /// <summary>
    /// 当前主机设备数据
    /// </summary>
    public Shebei_AD Shebei_dangqian;

    /// <summary>
    /// 初始化
    /// </summary>
    /// <param name="weiyishibie">唯一识别号</param>
    public void Chushihua(String weiyishibie, Context context)
    {
        Shebei_dangqian = new Shebei_AD();
        Shebei_dangqian.Chushihua(weiyishibie,context);
    }
    /// <summary>
    /// 添加新的控制中心
    /// </summary>
    /// <param name="fuwuqi_Ip"></param>
    public void Add_kongzhi(Fuwuqi_ip fuwuqi_Ip,Peizhi peizhi,Yonghu yonghu_) throws Exception {
        //检查
        for(int i=0;i<peizhi.Shuju.Fuwuqi_Ips.size();i++)
        {
            if(peizhi.Shuju.Fuwuqi_Ips.get(i).Daunkou==fuwuqi_Ip.Daunkou&&peizhi.Shuju.Fuwuqi_Ips.get(i).Ip==fuwuqi_Ip.Ip)
            {
                return;
            }
        }

        //尝试连接
        Lianjie(fuwuqi_Ip,yonghu_);
    }


    /// <summary>
    /// 连接控制中心
    /// </summary>
    /// <param name="ip">ip地址</param>
    /// <param name="duankou">端口</param>
    public void Lianjie(Fuwuqi_ip fuwuqi_Ip,Yonghu yonghu_) throws Exception {
        new Thread(new Runnable(){
            @Override
            public void run() {
                //发送当前设备数据
                String jieguo = null;
                try {
                    jieguo = Shebei_dangqian.Shuju_json();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //发送当前设备数据
                SendMethod sendMethod=new SendMethod(yonghu_.datagramSocket);

                try {
                    sendMethod.sendMessage(jieguo, fuwuqi_Ip.Ip, fuwuqi_Ip.Daunkou);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /// <summary>
    /// 调用此方法更新本地主机数据
    /// </summary>
    public void Gengxin_bendi(Peizhi peizhi,Context context,Yonghu yonghu_) throws IOException {
        //获取数据
        Shuju_Shebei_AD shuju_Shebei_ = Gongju.Huoqu_shebei_xinxi(yonghu_,context);
        shuju_Shebei_.Shebei_shibie = Shebei_dangqian.Shuju.Shebei_shibie;
        shuju_Shebei_.Mingchen = peizhi.Shuju.getZhujiming();
        shuju_Shebei_.Weiyi_shibie = peizhi.Shuju.getWeiyi_shibie();
        Shebei_dangqian.Shuju = shuju_Shebei_;
        Shebei_dangqian.Baocun(context);
    }

    /// <summary>
    /// 向每一个控制中心传递当前数据
    /// </summary>
    /// <param name="fuwuqi_Ips"></param>
    public void Send_shuju(List<Fuwuqi_ip> fuwuqi_Ips,Yonghu yonghu_) throws Exception {
        for (int i=0;i<fuwuqi_Ips.size();i++)
        {
            Lianjie(fuwuqi_Ips.get(i),yonghu_);
        }

    }
    /// <summary>
    /// 建立UDP服务器监听 尝试异步使用
    /// </summary>
    public void Udp_jianting(Context context,Yonghu yonghu_) {

        try {
            while (true) {

                GetMethod getMethod = new GetMethod(yonghu_.datagramSocket2);//实例化接收方法的引用
                String[] strInfo = getMethod.getMessage();
                //分析控制中心
                Fuwuqi_ip fuwuqi_ip = new Fuwuqi_ip();
                strInfo[0] = strInfo[0].substring(1,strInfo[0].length());
                fuwuqi_ip.Ip = strInfo[0];
                fuwuqi_ip.Daunkou = Integer.parseInt(strInfo[1]);
                Fengxi_shuju(strInfo[2], fuwuqi_ip, context);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }

    /// <summary>
    /// 移除服务器
    /// </summary>
    /// <param name="kongzhi"></param>
    public void Remove_fu(Kongzhi kongzhi,Peizhi peizhi,Context context)
    {
            peizhi.Remove_ip(kongzhi.Fuwuqi_Ip_,context);
            for(int i=0;i<Kongzhis.size();i++)
            {
                if(kongzhi.Shuju.Weiyi_shibie==Kongzhis.get(i).Shuju.Weiyi_shibie)
                {
                    Kongzhis.remove(i);
                }

            }
    }

    /// <summary>
    /// 分析从控制中心获取的数据
    /// </summary>
    /// <param name="json_str"></param>
    public void Fengxi_shuju(String json_str,Fuwuqi_ip fuwuqi_Ip,Context context)
    {
        try
        {
            Shuju_chuang shuju_Chuang = null;
            Kongzhi kongzhi = null;
            ObjectMapper objectMapper = new ObjectMapper();
            shuju_Chuang = objectMapper.readValue(json_str, Shuju_chuang.class);

            if(shuju_Chuang!=null)
            {


                //查找是否存在控制中心
                for(int i=0;i<Kongzhis.size();i++)
                {
                    if(Kongzhis.get(i).Shuju.Weiyi_shibie.equals(shuju_Chuang.Kongzhi.Weiyi_shibie))
                    {
                        kongzhi = Kongzhis.get(i);
                    }
                }
                if(kongzhi==null)
                {
                    kongzhi = new Kongzhi();
                    kongzhi.chushihua(shuju_Chuang.Kongzhi,fuwuqi_Ip);
                    Kongzhis.add(kongzhi);
                }

                //调用控制中心更新
                kongzhi.Gengxin(shuju_Chuang,context);
            }
        }
        catch (Exception ignored)
        {
ignored.printStackTrace();
        }
    }

}
