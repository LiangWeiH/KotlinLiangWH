package com.base.fruitbase.protocal.netutil;

import android.net.Uri;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.base.fruitbase.util.SPUtils;

/**
 * webView 自定义MyWebChomeClient 用于实现4.4版本选择文件并上传
 */
public class MyWebChomeClient extends WebChromeClient {

    private final TitleBar title_body;
    private final ProgressBar progressBar;
    private final boolean isShowTitle;
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public MyWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack, TitleBar title_body, ProgressBar progressBar, boolean isShowTitle) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
        this.title_body = title_body;
        this.progressBar = progressBar;
        this.isShowTitle =isShowTitle;
    }



    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams);
    }

    public interface OpenFileChooserCallBack {
        // for API - Version below 5.0.
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        // for API - Version above 5.0 (contais 5.0).
        boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                FileChooserParams fileChooserParams);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (progressBar != null) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100)
                progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);


        if (title_body != null && !isShowTitle)
            title_body.setTitle(title);
        if(title.equals("商品主页")) {
            if(SPUtils.getBoolean("isFirstJin")) {
//                ToastUtil.showToast(title);
                view.loadUrl("javascript:clearStorageTips()");
                SPUtils.putBoolean("isFirstJin", false);
            }
        }
    }



}
