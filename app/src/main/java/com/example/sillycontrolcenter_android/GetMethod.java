package com.example.sillycontrolcenter_android;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GetMethod {
    private DatagramPacket datagramPacket;//用来接收的数据报
    private DatagramSocket datagramSocket;//接收的通道
    /**
     * 构造方法
     * @param datagramSocket
     */
    public GetMethod(DatagramSocket datagramSocket){
        this.datagramSocket=datagramSocket;
    }
    /**
     * 获取消息的方法
     * @return
     */
    public String[] getMessage(){
        String str=null;
        try{

            byte[] buffer=new byte[64*1024];//初始化字节
            datagramPacket=new DatagramPacket(buffer,64*1024);//创建一个空的包准备接收
            Log.v("MainActivity","等待接收数据报");
            datagramSocket.receive(datagramPacket);//等待接收数据报
            String str_l= datagramPacket.getAddress().toString();
            String str_2=String.valueOf( datagramPacket.getPort());
            str=new String(buffer).trim();//trim可以去掉多余的空格
            Log.v("MainActivity", "收到了字符串："+str);

            String[] linshi=new String[3];
            linshi[0]=str_l;
            linshi[1]=str_2;
            linshi[2]=str;
            return linshi;
        }catch(Exception e){
            e.printStackTrace();
            Log.v("MainActivity", "接收错误");
            return null;
        }
    }
}
