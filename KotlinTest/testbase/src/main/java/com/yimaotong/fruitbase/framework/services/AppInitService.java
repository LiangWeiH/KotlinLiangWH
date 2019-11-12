package com.yimaotong.fruitbase.framework.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.framework.base.BaseApplication;
import com.yimaotong.fruitbase.ui.BGASwipeBack.BGASwipeBackManager;
import com.yimaotong.fruitbase.ui.lec.LoadingAndRetryManager;

/**
 * @time 2017/6/12 14:49
 */
public class AppInitService extends IntentService {

    public AppInitService() {
        super("appInitService");
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
        initLoadingAndRetry();
        BGASwipeBackManager.getInstance().init((BaseApplication) BaseApplication.getContext());
      /*  try {
            HttpsUtils.getSslSocketFactory(new InputStream[]{BaseApplication.getContext().getAssets().open("214396624070574.cer")}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//            CrashHandler.getInstance().init(BaseApplication.getContext(), true);
    }

    /**
     * 初始化LEC
     */
    private void initLoadingAndRetry() {
//        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
//        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }

    public static void startInitService(Context context) {
        Intent intent = new Intent(context, AppInitService.class);
        intent.putExtra("appInit", true);
        context.startService(intent);
    }
}
