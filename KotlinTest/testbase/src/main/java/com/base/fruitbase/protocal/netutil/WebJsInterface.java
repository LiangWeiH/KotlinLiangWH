package com.base.fruitbase.protocal.netutil;

import android.webkit.JavascriptInterface;

/**
 * @time 2017/10/27 11:13
 */
public interface WebJsInterface {

    /**
     * webView能否后退
     * @return
     */
    @JavascriptInterface
    void canJsGoBack();
}
