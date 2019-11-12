package com.example.kotlintest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;


public class ShowLodingUtils {
    private static CutscenesProgress cutscenesProgress = null;


    /**
     * @param title      提示框的标题，如果不想要标题，传递null
     * @param content    提示框的提示内容，可传递null
     * @param cancelable 该dialog是否可以cancel
     */
    public static CutscenesProgress init(Context context, String title, String content, boolean cancelable, DialogInterface.OnCancelListener listener) {
        CutscenesProgress dialog = CutscenesProgress.createDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            dialog.setMessage(content);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(listener);
        return dialog;
    }

    public static void showCutscenes(String title, String content, Context context) {
        showCutscenes(title, content, context, true, null);
    }

    public static void showCutscenes(String content, Context context) {
        showCutscenes(null, content, context, true, null);
    }

    /**
     * 显示加载dialog
     *
     * @param title      标题
     * @param content    内容
     * @param cancelable 是否可以取消显示
     * @param listener   取消显示后的监听
     */
    public static void showCutscenes(String title, String content, Context context, boolean cancelable, DialogInterface.OnCancelListener listener) {
        try {
            if (null != context) {
                if (null == cutscenesProgress) {
                    cutscenesProgress = init(context, title, content, cancelable, listener);
                    cutscenesProgress.show();
                } else {
                    if (cutscenesProgress.isShowing()) {
                        return;
                    } else {
                        cutscenesProgress.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 显示加载dialog
//     *
//     * @param listener
//     *         取消显示后的监听
//     */
//    public static void showCutscenes(Context context, DialogInterface.OnCancelListener listener) {
//        if (null != context) {
//            if (null == cutscenesProgress) {
//                cutscenesProgress = init(context, "", "", true, listener);
//                cutscenesProgress.show();
//            } else {
//                if (cutscenesProgress.isShowing()) {
//                    return;
//                } else {
//                    cutscenesProgress.show();
//                }
//            }
//        }
//    }

    /**
     * 隐藏加载dialog
     */
    public static void hideCutscenes() {
        if (null != cutscenesProgress && cutscenesProgress.isShowing()) {
            cutscenesProgress.hide();
        }
    }

    /**
     * 释放加载dialog
     */
    public static void dismissCutscenes() {
        Log.d("dismissCutscenes", "dismissCutscenes");
        if (null != cutscenesProgress) {
            Log.d("automatic", "dismissCutscenes");
            cutscenesProgress.dismiss();
            cutscenesProgress = null;
        }
    }

}