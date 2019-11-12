package com.yimaotong.fruitbase.protocal.netutil.request;

import android.util.Log;

import com.yimaotong.fruitbase.protocal.netutil.NetUtil;
import com.yimaotong.fruitbase.protocal.netutil.callback.ICallBack;
import com.yimaotong.fruitbase.util.ToastUtil;
import com.yimaotong.fruitbase.util.okhttputils.OkHttpUtils;
import com.yimaotong.fruitbase.util.okhttputils.builder.PostFormBuilder;
import com.yimaotong.fruitbase.util.okhttputils.callback.StringCallback;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.Call;

/**
 * @time 2017/9/23 13:40
 */
public class PostRequest implements IRequest {

    public String url;
    public Object tag;
    public Map<String, String> params = new TreeMap<>();
    public Map<String, File> fileMapParams = new TreeMap<>();

    @Override
    public PostRequest url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostRequest param(String key, String value) {
        params.put(key, value);
        return this;
    }

    @Override
    public IRequest fileParam(String key, File file) {
        fileMapParams.put(key,file);
        return this;
    }

    @Override
    public PostRequest tag(Object tag) {
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
            callBack.onUrl(url);

        if (callBack!=null)
            callBack.onStart();

        Map<String, String> stringMap;
        if (NetUtil.mSignStrategy != null)
            stringMap = NetUtil.mSignStrategy.sign(params);
        else
            stringMap = params;
        PostFormBuilder postBuilder = OkHttpUtils.post()
                .url(url)
                .params(stringMap);
        if (fileMapParams.size()>0){
            Set<String> keySet = fileMapParams.keySet();
            for (String key : keySet) {
                postBuilder.addFile(key,fileMapParams.get(key).getName(),fileMapParams.get(key));
            }
        }
        if (tag != null)
            postBuilder.tag(tag);
        try {
            postBuilder.build().execute(new StringCallback() {
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
        }catch (Exception e){
            Log.e("网络请求异常",e.getMessage());
            ToastUtil.showToast("参数错误");
        }

    }
}
