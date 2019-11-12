package com.yimaotong.fruitbase.framework.base;

import android.app.Application;
import android.content.Context;

import com.hjq.toast.ToastUtils;
import com.yimaotong.fruitbase.framework.services.AppInitService;

/**
 * @time 2017/9/29 10:03
 */
public class BaseApplication extends Application {
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppInitService.startInitService(getContext());
        // 在 Application 中初始化
        ToastUtils.init(this);

    }

    public static Context getContext() {
        return context;
    }

}
