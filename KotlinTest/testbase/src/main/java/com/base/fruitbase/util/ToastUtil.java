package com.base.fruitbase.util;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruitbase.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Toast工具类
 */
public class ToastUtil {


    private static Toast toast;

    /**
     * 静态toast
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        // toast消失了  toast 会自动为null
        if (toast == null) {// 消失了
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(13);
        }
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(text);
        toast.show();
    }

    /**
     * 时间长静态toast
     *
     * @param context
     * @param text
     */
    public static void showLongToast(Context context, String text) {
        if (toast == null) {// 消失了
            toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 自定义时长toast
     * @param toast
     * @param cnt
     */
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, cnt);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, 3000);
    }

    public static void showLongToast(String text){
        showLongToast(UIUtils.getContext(),text);
    }

    public static void showToast(String text) {
        showToast(UIUtils.getContext(), text);
    }

    public static void cancelToast() {
        if (toast != null)
            toast.cancel();
    }

    public static void showErrToast() {
        showToast(UIUtils.getContext().getResources().getString(R.string.netError));
    }

    public static void showErrToast(String text) {
        showToast(text);
    }


}
