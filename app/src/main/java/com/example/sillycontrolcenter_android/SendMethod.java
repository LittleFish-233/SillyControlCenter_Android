package com.example.sillycontrolcenter_android;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendMethod {
    private DatagramPacket datagramPacket;//发送数据报
    private InetAddress destInetAddress;//目标地址,ip需要包装为它才能传入包
    private DatagramSocket datagramSocket;//发送通道
    public SendMethod(DatagramSocket datagramSocket){
        this.datagramSocket=datagramSocket;
    }
    /**
     * 发信息的方法
     */
    public void sendMessage(String str,String ip,int port)throws Exception{
        Log.v("MainActivity", str);
        Log.v("MainActivity", ip);
        Log.v("MainActivity", String.valueOf(port));
        destInetAddress=InetAddress.getByName(ip);//把ip包装为地址
        byte[] buffer=str.getBytes();
        try {
            datagramPacket=new DatagramPacket(buffer,buffer.length,destInetAddress,port);//创建数据报
            datagramSocket.send(datagramPacket); //发送数据报
            Log.v("MainActivity", "成功发送的消息："+str);
        } catch (Exception e) {
            Log.v("MainActivity", "error");
            e.printStackTrace();
        }
    }
}
