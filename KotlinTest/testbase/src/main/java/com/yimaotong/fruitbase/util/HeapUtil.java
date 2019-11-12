package com.yimaotong.fruitbase.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * 内存工具类
 * @time 2017/10/25 11:54
 */
public class HeapUtil {
    /**
     * 释放因为InputMethodManager造成的内存泄漏
     *
     * @param destContext
     */
    public static void releaseInputMethodManagerLeak(Context destContext) {
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
}
