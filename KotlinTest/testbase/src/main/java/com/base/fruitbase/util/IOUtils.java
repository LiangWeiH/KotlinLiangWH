package com.base.fruitbase.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具类
 */
public class IOUtils {

    /**
     * 关闭流资源
     * @param closeable
     */
    public static  void close(Closeable closeable){
        if (closeable!= null){
            try {
                closeable.close();
            } catch (IOException e) {
                System.err.println("异常："+e.getMessage());
            }
        }
    }
}
