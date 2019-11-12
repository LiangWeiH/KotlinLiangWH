package com.yimaotong.fruitbase.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.fruitbase.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @time 2017/9/21 10:11
 */
public class StatusBarCompat {
    private static final int INVALID_VAL = -10;
    private static final int COLOR_DEFAULT = Color.parseColor("#00000000");
    //如果不是小米或者魅族高版本手机，或者android APi23以上， 将无法改变状态栏字体颜色，所以不能设置状态栏为白色，将被重置为此默认颜色
    private static final int DEFUALTCOLOR = R.color.black;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (statusColor != INVALID_VAL) {
//                activity.getWindow().setStatusBarColor(statusColor);
//                if (statusColor == UIUtils.getColor(R.color.white))
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
//                        setLollipopTextDark(activity, true);
//                    else setTextDark(activity);
//            }
//            return;
//        }

        if (statusColor == UIUtils.getColor(R.color.white)) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                setLollipopTextDark(activity, true);
            else if (setTextDark(activity))
                return;
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = contentView.getChildAt(0);
            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity)) {
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView,lp);
//        }
    }


    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }

    public static boolean setTextDark(Activity activity) {
        try {
            setMIUIBarDark(activity, true);
        } catch (Exception e) {
            try {
                setMEIZUBarDark(activity, true);
            } catch (Exception e1) {
                compat(activity, UIUtils.getColor(DEFUALTCOLOR));
                return true;
            }
        }
        return false;
    }


    /**
     * 小米修改状态栏字体颜色
     *
     * @param darkmode
     * @param activity
     */
    public static void setMIUIBarDark(Activity activity, boolean darkmode) throws Exception {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        int darkModeFlag = 0;
        Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
    }

    /**
     * 魅族修改状态栏字体颜色
     *
     * @param activity
     * @param dark
     * @return
     */
    public static boolean setMEIZUBarDark(Activity activity, boolean dark) throws Exception {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        }
        return result;
    }

    /**
     * 处理Lollipop以上
     * <p>
     * Lollipop可以设置为沉浸，不能设置字体颜色(所以白色背景会很丑)
     * <p>
     * M(API23)可以设定
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setLollipopTextDark(Activity activity, boolean lightStatusBar) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        int flag = activity.getWindow().getDecorView().getSystemUiVisibility();

        if (lightStatusBar) {
            flag |= (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS

                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);

        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
