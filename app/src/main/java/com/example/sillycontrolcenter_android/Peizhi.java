package com.example.sillycontrolcenter_android;
import android.content.Context;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class Peizhi
{

    /// <summary>
    /// 版本
    /// </summary>
    public static int banben = 1;

    /// <summary>
    /// 数据
    /// </summary>
    public Shuju_peizhi Shuju  = new Shuju_peizhi();

    private static boolean shifou_zhengzai = false;

    /// <summary>
    /// 保存基本信息
    /// </summary>
    public void Baocun_jiben(Context context)
    {
        if (shifou_zhengzai)
        {
            return;
        }
        shifou_zhengzai = true;


        //序列化数据
        //添加基本数据
        // JSON对象序列化
        String employeeJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, Shuju);
            jsonGenerator.close();
            employeeJson = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //保存到文件
        String filename = "peizhi_new.json";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE))
        {
            fos.write(employeeJson.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        shifou_zhengzai = false;
    }
    /// <summary>
    /// 读取基本信息
    /// </summary>
    /// <returns>是否成功</returns>
    public void Duqu_jiben(Context context)
    {
        try
        {
            //打开文件
            FileInputStream fis = context.openFileInput("peizhi_new.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder linshi= new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader))
            {
                String line = reader.readLine();
                while (line != null)
                {
                    linshi.append(line);
                    line = reader.readLine();
                }
            }
            catch (IOException e)
            {
                // Error occurred when opening raw file for reading.
            }
            String contents = linshi.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            Shuju = objectMapper.readValue(contents, Shuju_peizhi.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            Baocun_jiben(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /// <summary>
    /// 添加服务器IP
    /// </summary>
    /// <param name="fuwuqi_Ip"></param>
    public void Add_ip(Fuwuqi_ip fuwuqi_Ip,Context context)
    {
        for(int i=0;i< Shuju.Fuwuqi_Ips.size();i++)
        {
            if(Shuju.Fuwuqi_Ips.get(i).Ip==fuwuqi_Ip.Ip&&Shuju.Fuwuqi_Ips.get(i).Daunkou==fuwuqi_Ip.Daunkou)
            {
                return;
            }
        }

        Shuju.Fuwuqi_Ips.add(fuwuqi_Ip);
        Baocun_jiben(context);
    }
    /// <summary>
    /// 修改背景图片
    /// </summary>
    /// <param name="file"></param>
    public void Xiugai_beijing(String file,Context context)
    {
        //复制文件
        // string linshi = lujing + "beijing." + Gongju.huoqu_kuozhang(file);
        // File.Copy(file, linshi,true);
        //保存路径
        Shuju.Beijing = file;
        Baocun_jiben(context);
    }
    /// <summary>
    /// 修改头像图片
    /// </summary>
    /// <param name="file"></param>
    public void Xiugai_touxiang(String file,Context context)
    {
        //复制文件
        //string linshi = lujing + "touxiang." + Gongju.huoqu_kuozhang(file);
        //File.Copy(file, linshi, true);
        //保存路径
        Shuju.Touxiang = file;
        Baocun_jiben(context);
    }


    public void Remove_ip(Fuwuqi_ip fuwuqi_Ip,Context context)
    {
        try
        {
            for (int i=0;i< Shuju.Fuwuqi_Ips.size();i++)
            {
                if (Shuju.Fuwuqi_Ips.get(i).Ip.equals(fuwuqi_Ip.Ip) && Shuju.Fuwuqi_Ips.get(i).Daunkou == fuwuqi_Ip.Daunkou)
                {
                    Shuju.Fuwuqi_Ips.remove(i);
                    break;
                }
            }

            Baocun_jiben(context);
        }
        catch(Exception exc)
        {

        }
    }
}
