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

public class Shebei_AD extends Shebei_Fu {

    /// <summary>
/// 初始化设备
/// </summary>
/// <param name="weiyi_shibie">唯一识别号</param>
/// <param name="shuju_xin">新数据</param>
    @Override
    public void Chushihua(String weiyi_shibie, Context context) {
        Shuju.Weiyi_shibie = weiyi_shibie;
        Shuju.Shebei_shibie=1;
        Duqu(context);
    }

    /// <summary>
/// 更新设备数据
/// </summary>
/// <param name="shuju_xin"></param>
    @Override
    public void Gengxin(String shuju_xin) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Shuju = objectMapper.readValue(shuju_xin, Shuju_Shebei_AD.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /// <summary>
/// 更新设备数据
/// </summary>
/// <param name="shuju_xin"></param>
    public void Gengxin(Shuju_Shebei_AD shuju_Shebei_AD) {

        Shuju = shuju_Shebei_AD;
    }

    /// <summary>
/// 保存
/// </summary>
    public void Baocun(Context context) throws IOException {
        if (shifou_zhengzai == true) {
            return;
        }
        shifou_zhengzai = true;
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
        objectMapper.writeValue(jsonGenerator, Shuju);
        jsonGenerator.close();
        String jsonStrUser = stringWriter.toString();


        //保存到文件
        String filename = Shuju.Weiyi_shibie + "shuju.json";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(jsonStrUser.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        shifou_zhengzai = false;
    }

    /// <summary>
/// 读取
/// </summary>
/// <returns>是否成功</returns>
    public void Duqu(Context context) {
        try {
            //打开文件
            FileInputStream fis = context.openFileInput(Shuju.Weiyi_shibie + "shuju.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder linshi = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    linshi.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }
            String contents = linshi.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            Shuju = objectMapper.readValue(contents, Shuju_Shebei_AD.class);

        } catch (  IOException e) {
            e.printStackTrace();
        }
    }
    public String Shuju_json() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
        objectMapper.writeValue(jsonGenerator, Shuju);
        jsonGenerator.close();
       String employeeJson = stringWriter.toString();

        return employeeJson;
    }

    public void Chushihua(Context context) {
        Duqu(context);
    }

}
