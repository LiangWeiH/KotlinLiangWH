package com.base.fruitbase.protocal.netutil.callback;

/**
 * @time 2017/9/23 13:44
 */
public interface ICallBack {

    void onStart();

    void onSuccess(String response);

    void onFinish();

    void onError(String errMsg);

    void onUrl(String url);
}
