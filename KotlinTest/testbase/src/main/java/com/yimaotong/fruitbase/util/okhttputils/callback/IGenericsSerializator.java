package com.yimaotong.fruitbase.util.okhttputils.callback;

/**
 * Created by JimGong on 2016/6/23.
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
