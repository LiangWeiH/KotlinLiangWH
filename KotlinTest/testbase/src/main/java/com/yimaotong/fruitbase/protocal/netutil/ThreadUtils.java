package com.yimaotong.fruitbase.protocal.netutil;

import android.os.Handler;


/**
 * 线程调度工具类
 */
public class ThreadUtils {

    /**
     * 在子线程执行
     *
     * @param runnable
     */
    public static void runOnBackThread(Runnable runnable) {
//        new Thread(runnable).start();   // 线程池
        ThreadPoolManager.getInstance().createThreadPool().execture(runnable);
    }

    private static Handler handler = new Handler();

    /**
     * 在主线程执行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
