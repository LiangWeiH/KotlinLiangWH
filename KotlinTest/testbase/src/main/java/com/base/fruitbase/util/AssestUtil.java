package com.base.fruitbase.util;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  on 2017/9/24.
 */

public class AssestUtil {

    //从资源文件中获取分类json
    public static String getAssetsData(Activity activity, String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = activity.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }
}
