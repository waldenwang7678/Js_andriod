package com.wangjt.jsandjsbridgetest;

import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangjt on 2017/8/18.
 * 读取js 文件
 */

public class FileUtils {
    public static String readFile(Context context, String jsName) {
        String jsStr = "";
        try {
            InputStream mIs = context.getResources().getAssets().open(jsName);
            if (mIs != null) {
                byte buff[] = new byte[1024];
                ByteArrayOutputStream outTemp = new ByteArrayOutputStream();
                int len = 0;
                while ((len = mIs.read(buff)) != -1) {
                    outTemp.write(buff, 0, len);
                }
                jsStr = outTemp.toString();
            } else {
                Toast.makeText(context, "js加载失败", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsStr;
    }
}
