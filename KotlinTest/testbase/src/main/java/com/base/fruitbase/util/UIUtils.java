package com.base.fruitbase.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.base.fruitbase.framework.base.BaseActivity;
import com.base.fruitbase.framework.base.BaseApplication;
import com.base.fruitbase.ui.BGASwipeBack.BGASwipeBackHelper;

import java.lang.reflect.Field;


public class UIUtils {

    public static final String intentKey = "className";
    public static final String intentData = "data";
    public static String intentCode = "code";
    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转sp
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    /**
     * 根据资源id 获取字符串数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取Resources对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    public static Context getContext() {
        if (mContext == null)
            mContext = BaseApplication.getContext();
        return mContext;
    }

    /**
     * xml --->View对象
     *
     * @param id
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(UIUtils.getContext(), id, null);
    }

    /**
     * 获取屏幕密度
     */
    public static int getDenisty(Activity activity) {
        //        获取屏幕密度
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;
    }

    /**
     * 获取dimens
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    /**
     * 启动新的Activity，不传递数据
     *
     * @param clazz
     */
    public static void startActivity(Class clazz, boolean isSwipeBack) {
        Intent intent = new Intent(UIUtils.getContext(), clazz);
        if (BaseActivity.activity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//指定任务栈
            getContext().startActivity(intent);
        } else {
            //不需要指定任务栈
            BaseActivity.activity.startActivity(intent);
            if (isSwipeBack)
                BGASwipeBackHelper.executeForwardAnim(BaseActivity.activity);
        }
    }

    /**
     * 启动新的Activity，不传递数据
     *
     * @param clazz
     */
    public static void startActivity(Class clazz) {
        startActivity(clazz, true);
    }

    /**
     * 根据Intent启动新的Activity
     *
     * @param intent
     */
    public static void startActivity(Intent intent) {
        if (BaseActivity.activity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//指定任务栈
            getContext().startActivity(intent);
        } else {
            //不需要指定任务栈
            BaseActivity.activity.startActivity(intent);
            BGASwipeBackHelper.executeForwardAnim(BaseActivity.activity);
        }

    }

    /**
     * 启动新的Activity，传递数据
     *
     * @param className
     */
    public static void startActivity(Class clazz, String key, String className) {
        Intent intent = new Intent(UIUtils.getContext(), clazz);
        intent.putExtra(key, className);
        if (BaseActivity.activity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//指定任务栈
            getContext().startActivity(intent);
        } else {
            //不需要指定任务栈
            BaseActivity.activity.startActivity(intent);
        }
    }

    /**
     * 启动新的Activity，传递数据
     *
     * @param className
     */
    public static void startActivity(Class clazz, String key, boolean className) {
        Intent intent = new Intent(UIUtils.getContext(), clazz);
        intent.putExtra(key, className);
        if (BaseActivity.activity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//指定任务栈
            getContext().startActivity(intent);
        } else {
            //不需要指定任务栈
            BaseActivity.activity.startActivity(intent);
        }
    }

    /**
     * 启动新的Activity，传递数据
     *
     * @param className
     */
    public static void startActivity(Class clazz, String key, int className) {
        Intent intent = new Intent(UIUtils.getContext(), clazz);
        intent.putExtra(key, className);
        if (BaseActivity.activity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//指定任务栈
            getContext().startActivity(intent);
        } else {
            //不需要指定任务栈
            BaseActivity.activity.startActivity(intent);
        }
    }

    /**
     * id --- >string
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * id_---->Drawable
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 限制小数点后个数
     *
     * @param num 小数点后位个数
     */
    public static void setDeciNum(final EditText editText, final int num) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > num) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + num + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 为viewPager构造指示点
     */
    public static ImageView[] initIndicators(int count, LinearLayout ovels, int unSelect, int select) {
        if (count < 2)
            return null;
        ImageView[] indicators = new ImageView[count]; // 定义指示器数组大小
        for (int i = 0; i < count; i++) {
            // 循环加入指示器
            indicators[i] = new ImageView(UIUtils.getContext());
            indicators[i].setBackgroundResource(unSelect);
            if (i == 0) {
                indicators[i].setBackgroundResource(select);
            }
            //指示点之间到间距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = UIUtils.dip2px(10);
            params.rightMargin = UIUtils.dip2px(10);
            ovels.addView(indicators[i], params);
        }
        return indicators;
    }

    /**
     * 释放因为InputMethodManager造成的内存泄漏
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 获取key3
     *
     * @param key3
     * @return
     */
    public static String getkey(int key3) {
        return getString(key3);
    }

    /**
     * 获取Cookie值
     *
     * @param url
     * @return
     */
    public static String getCookie(String url) {
        CookieManager instance = CookieManager.getInstance();
        return instance.getCookie(url);
    }

    /**
     * 将view偏移到statuBar下面
     *
     * @param view
     */
    public static void fitStatusBar(View view) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = StatusBarCompat.getStatusBarHeight(getContext());
        view.setLayoutParams(layoutParams);
    }

    /**
     * 将根view顶部toppadding设置为statuBar高度
     *
     * @param view
     */
    public static void paddingStatusBar(ViewGroup view) {
        view.setPadding(view.getPaddingLeft(), StatusBarCompat.getStatusBarHeight(getContext()), view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 将view顶部偏移增加statuBar高度
     *
     * @param view
     */
    public static void addStatusBarHeight(View view) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin += StatusBarCompat.getStatusBarHeight(getContext());
        view.setLayoutParams(layoutParams);
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }
}
