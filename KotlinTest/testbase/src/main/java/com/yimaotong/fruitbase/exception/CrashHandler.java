package com.yimaotong.fruitbase.exception;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.fruitbase.BuildConfig;

/**
 * Created by  on 2017/7/21.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance;
    private Context mcontext;
    private Thread.UncaughtExceptionHandler mDefualtHandller;
    private boolean isPrintLog;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param isPrintLog 是否输出错误信息
     */
    public void init(Context context, boolean isPrintLog) {
        mcontext = context;
        this.isPrintLog = isPrintLog;
        mDefualtHandller = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当触发未捕获异常时，系统将调用此方法
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e)) {
            if (mDefualtHandller != null)
                mDefualtHandller.uncaughtException(t, e);
        } else {
            try {
                // 如果处理了，让程序继续运行1秒再退出
                Thread.sleep(1000);
            } catch (InterruptedException e2) {
                Log.e("未捕获异常", e2.getMessage());
            }
            // 退出程序
            ActivityManager am = (ActivityManager) mcontext.getSystemService(Context.ACTIVITY_SERVICE);
            android.os.Process.killProcess(android.os.Process.myPid());
            am.restartPackage(mcontext.getPackageName());
            System.exit(0);
        }
    }

    /**
     * 是否手动处理未捕获异常
     *
     * @param e
     * @return
     */
    private boolean handleException(Throwable e) {
        if (BuildConfig.DEBUG)
            System.out.println("全局异常信息：" + e.getMessage());
        if (e == null)
            return false;
//        showExceptionMsg(e);
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(mcontext, "即将重启应用", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 提示异常信息  TODO 不行  无法弹出toast
     */
    private void showExceptionMsg(Throwable e) {
        if (!isPrintLog) return;
        if (Looper.getMainLooper() == Looper.myLooper())
            showToast(e, true);
        else
            showToast(e, false);
    }


    private void showToast(Throwable e, boolean isMainThread) {
        if (isMainThread)
            Toast.makeText(mcontext, "全局异常：" + e.getMessage(), Toast.LENGTH_LONG).show();
        else {
            Looper.prepare();
            Toast.makeText(mcontext, "全局异常：" + e.getMessage(), Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }
}
