package com.base.fruitbase.protocal.netutil;

import android.text.TextUtils;

import androidx.collection.ArrayMap;

import com.base.fruitbase.protocal.netutil.extra.IExtra;
import com.base.fruitbase.protocal.netutil.request.GetRequest;
import com.base.fruitbase.protocal.netutil.request.PostRequest;
import com.base.fruitbase.protocal.netutil.signstrategy.ISignStrategy;
import com.base.fruitbase.util.okhttputils.OkHttpUtils;
import com.base.fruitbase.util.okhttputils.https.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static com.base.fruitbase.util.UIUtils.getContext;

/**
 * @time 2017/9/22 13:00
 */
public class NetUtil {
    private static OkHttpUtils okHttpUtils;
    public static ISignStrategy mSignStrategy;
    public static IExtra extra;
    public static Map<String, String> commenParams = new ArrayMap<>();

    public static GetRequest get() {
        return new GetRequest();
    }

    public static PostRequest post() {
        return new PostRequest();
    }

    public NetUtil(Builder builder) {
        this.mSignStrategy = builder.mSignStrategy;
        this.extra = builder.extra;
        if (builder.commenParams != null && builder.commenParams.size() > 0)
            this.commenParams = builder.commenParams;

        HttpsUtils.SSLParams sslParams = null;
        if (!TextUtils.isEmpty(builder.sslParams)) {
            try {
                InputStream[] inputStreams = {getContext().getAssets().open(builder.sslParams)};
                sslParams = HttpsUtils.getSslSocketFactory(inputStreams, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OkHttpClient.Builder builder1 = new OkHttpClient.Builder()
                .readTimeout(builder.mReadTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(builder.mConnectTimeOut, TimeUnit.MILLISECONDS);

        //配置https
        if (!TextUtils.isEmpty(builder.sslParams) && sslParams != null)
            builder1.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        if (builder.interceptor != null)
            builder1.addInterceptor(builder.interceptor);
        OkHttpClient okHttpClient = builder1
                .build();
        okHttpUtils = OkHttpUtils.initClient(okHttpClient);
    }


    public static Builder config() {
        return new Builder();
    }

    public static void cancel(Object tag) {
        if (okHttpUtils != null)
            okHttpUtils.cancelTag(tag);
    }


    public static class Builder {
        public String mBaseUrl;
        public String mUrl;
        public long mConnectTimeOut;
        public long mReadTimeOut;
        public ISignStrategy mSignStrategy;
        public static IExtra extra;
        public Interceptor interceptor;
        public String sslParams;

        public Map<String, String> commenParams = new ArrayMap<>();
        public Map<String, String> params = new ArrayMap<>();

        public Builder baseUrl(String baseUrl) {
            mBaseUrl = baseUrl;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public Builder url(String url) {
            mUrl = url;
            return this;
        }

        public Builder connectTimeOut(long connectTimeOut) {
            mConnectTimeOut = connectTimeOut;
            return this;
        }

        public Builder readTimeOut(long readTimeOut) {
            mReadTimeOut = readTimeOut;
            return this;
        }

        public Builder signStrategy(ISignStrategy signStrategy) {
            mSignStrategy = signStrategy;
            return this;
        }

        public Builder extra(IExtra extra) {
            this.extra = extra;
            return this;
        }

        public Builder commenParam(String key, String value) {
            commenParams.put(key, value);
            return this;
        }

        public Builder sslParams(String sslParams) {
            this.sslParams = sslParams;
            return this;
        }

        public Builder param(String key, String value) {
            params.put(key, value);
            return this;
        }

        public NetUtil build() {
            return new NetUtil(this);
        }
    }


}
