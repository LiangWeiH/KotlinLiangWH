package com.yimaotong.fruitbase.protocal.netutil.request;

import com.yimaotong.fruitbase.protocal.netutil.callback.ICallBack;

import java.io.File;

/**
 * @time 2017/9/25 10:48
 */
public interface IRequest {

    IRequest url(String url);

    IRequest param(String key, String value);

    IRequest fileParam(String key, File file) throws Exception;

    IRequest tag(Object tag);

    void excute(final ICallBack callBack);

    /**
     * 执行网络请求
     * @param callBack
     * @param isNeedLogin 是否需要登录
     */
    void excute(final ICallBack callBack, boolean isNeedLogin);
}
