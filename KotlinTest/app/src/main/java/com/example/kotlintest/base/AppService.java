package com.example.kotlintest.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.collection.ArrayMap;

import com.base.fruitbase.protocal.FruitSignStragtegy;
import com.base.fruitbase.protocal.LoginExtra;
import com.base.fruitbase.protocal.netutil.NetUtil;

import java.util.Map;

/**
 * @time 2017/6/12 14:49
 */
public class AppService extends IntentService {

    public AppService() {
        super("appService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            boolean appInit = intent.getBooleanExtra("appInit", false);
            if (appInit)
                performInit();
        }
    }

    /**
     * 初始化全局配置
     */
    private void performInit() {
        initProtocal();
    }
    /**
     * 初始化网络请求
     */
    private void initProtocal() {
        Map<String, String> heads = new ArrayMap<>();
        heads.put("Connection", "close");
        NetUtil.config()
                .readTimeOut(10000L)
                .connectTimeOut(10000L)
//                .interceptor(new HeadersInterceptor(heads))
                .signStrategy(new FruitSignStragtegy())//签名规则
                .extra(new LoginExtra())//请求接口时判断是否需要登录
                .build();
    }


    public static void startInitService(Context context) {
        Intent intent = new Intent(context, AppService.class);
        intent.putExtra("appInit", true);
        context.startService(intent);
    }
}
