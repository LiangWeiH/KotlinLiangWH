package com.yimaotong.fruitbase.protocal.netutil.request;

import com.yimaotong.fruitbase.protocal.netutil.NetUtil;
import com.yimaotong.fruitbase.protocal.netutil.callback.ICallBack;
import com.yimaotong.fruitbase.util.okhttputils.OkHttpUtils;
import com.yimaotong.fruitbase.util.okhttputils.builder.GetBuilder;
import com.yimaotong.fruitbase.util.okhttputils.callback.StringCallback;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;

/**
 * @time 2017/9/23 13:40
 */
public class GetRequest implements IRequest {

    public String url;
    public Object tag;
    public Map<String, String> params = new TreeMap<>();

    @Override
    public GetRequest url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public GetRequest param(String key, String value) {
        params.put(key, value);
        return this;
    }

    @Override
    public IRequest fileParam(String key, File file) throws Exception {
        throw new Exception("get请求不能提交文件");
    }

    @Override
    public GetRequest tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public void excute(final ICallBack callBack) {
        excute(callBack, false);
    }

    /**
     * 执行网络请求
     *
     * @param callBack
     * @param isNeedLogin 是否需要登录
     */
    @Override
    public void excute(final ICallBack callBack, boolean isNeedLogin) {

        if (isNeedLogin)
            if (NetUtil.extra != null && !NetUtil.extra.isLogined()) {
                return;
            }

        if (callBack!=null)
            callBack.onStart();

        Map<String, String> stringMap;
        if (NetUtil.mSignStrategy != null)
            stringMap = NetUtil.mSignStrategy.sign(params);
        else
            stringMap = params;
        GetBuilder getBuilder = OkHttpUtils.get()
                .url(url)
                .params(stringMap);
        if (tag != null)
            getBuilder.tag(tag);
        getBuilder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (callBack != null)
                    callBack.onError(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (callBack != null)
                    callBack.onSuccess(response);
            }
        });
    }
}
