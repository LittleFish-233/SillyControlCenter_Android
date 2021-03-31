package com.example.sillycontrolcenter_android;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public  class Yonghu extends Application {

    public boolean shifou_chushihua = false;
    public Peizhi Peizhi_ = new Peizhi();
    public Mianban Mianban_ = new Mianban();
    public Notification notification = null;
    DatagramSocket datagramSocket = null;
    DatagramSocket datagramSocket2 = null;
    BatteryReceiver receiver = null;
    Shebei_PC dangqian_pc=null;
    Shebei_AD dangqian_ad=null;
    Kongzhi dangqian_kongzhi=null;

    public void chushihua(Context context) throws SocketException {
        Peizhi_.Duqu_jiben(context);
        Mianban_.Chushihua(Peizhi_.Shuju.getWeiyi_shibie(), context);
        datagramSocket = new DatagramSocket(22336);
        datagramSocket2 = new DatagramSocket(22334);//接收的数据通道
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver();
        registerReceiver(receiver, filter);
    }
}








