package com.yimaotong.fruitbase.protocal.netutil;

import android.text.TextUtils;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.framework.base.BaseActivity;
import com.yimaotong.fruitbase.protocal.interceptor.PrintUtil;
import com.yimaotong.fruitbase.protocal.netutil.callback.ICallBack;
import com.yimaotong.fruitbase.util.DeviceUtils;
import com.yimaotong.fruitbase.util.ToastUtil;
import com.yimaotong.fruitbase.util.UIUtils;


/**
 * @time 2019/9/23 14:00
 */
public abstract class BaseCallBack implements ICallBack {

    /**
     * 是否是主页，主页不需要关闭当前页面再跳往登录页，直接跳过去
     */
    private boolean isHomePage = false;

    /**
     * 是否提示onError信息
     */
    private boolean isAlertErrorMsg = true;
    private String successCode;
    private String successKey;
    private String url;
    private String stringUrl;

    /**
     * 是否提示网络错误
     *
     * @param isHomePage
     */
    public BaseCallBack(boolean isHomePage, boolean isAlertErrorMsg) {
        this.isHomePage = isHomePage;
        this.isAlertErrorMsg = isAlertErrorMsg;
    }

    public BaseCallBack(boolean isHomePage) {
        this.isHomePage = isHomePage;
    }

    public BaseCallBack() {
    }

    public BaseCallBack(String successKey, String successCode) {
        this.successCode = successCode;
        this.successKey = successKey;
    }

    /**
     * 网络请求开始前做一些操作，例如loading
     */
    @Override
    public void onStart() {
        if (BaseActivity.activity != null)
            BaseActivity.activity.showLoading();
    }

    @Override
    public void onSuccess(String response) {
        PrintUtil.printRespones(url + ">>原始数据", response);
        stringUrl = url;
        boolean isTrue = true;
        if (TextUtils.isEmpty(successKey)) {
            isTrue = JsonUtil.isSuccess(response);
        }
//        else {
//            isTrue = JsonUtil.isSuccess2(response, successKey, successCode);
//        }
        if (!isTrue) {
            if (!JsonUtil.isAgainLogin(response, isHomePage, stringUrl)) {
                if (!isHomePage) {
                    if (TextUtils.isEmpty(response) || TextUtils.equals(response, "{}")) {
                        ToastUtil.showToast("返回数据为空");
                    } else {
                        ToastUtil.showToast(JsonUtil.errMsg2(response));
                    }
                }
                onFail();
            }
            return;
        }
        if (BaseActivity.activity != null)
            BaseActivity.activity.dissLoading();

        onSuccessed(response);
        onFinish();
    }

    public void onFail() {
        if (BaseActivity.activity != null)
            BaseActivity.activity.dissLoading();
    }

    /**
     * 请求结束且数据处理完成后的一些操作，如取消loading
     */
    @Override
    public void onFinish() {
        if (BaseActivity.activity != null)
            BaseActivity.activity.dissLoading();
    }

    @Override
    public void onError(String errMsg) {
        if (!DeviceUtils.netIsConnected(UIUtils.getContext()) && isAlertErrorMsg)
            ToastUtil.showToast(UIUtils.getString(R.string.netError));
        else if (!TextUtils.isEmpty(errMsg) && (errMsg.startsWith("Failed to") || errMsg.startsWith("failed to")))
            ToastUtil.showToast("无法连接服务器，请稍后重试");
        else if (!TextUtils.isEmpty(errMsg) && !errMsg.startsWith("socket") && !errMsg.startsWith("Socket") && !errMsg.startsWith("timeout")
                && !errMsg.startsWith("canceled") && !errMsg.startsWith("Canceled") && !errMsg.startsWith("Unable to resolve"))//取消网络请求情况
            ToastUtil.showToast(errMsg);

        if (BaseActivity.activity != null) {
            BaseActivity.activity.dissLoading();
        }
    }

    @Override
    public void onUrl(String url) {
        this.url = url;
    }

    public abstract void onSuccessed(String response);
}
