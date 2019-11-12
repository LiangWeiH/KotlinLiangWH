package com.yimaotong.fruitbase.protocal.netutil;

import android.text.TextUtils;

import com.yimaotong.fruitbase.protocal.interceptor.PrintUtil;
import com.yimaotong.fruitbase.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @time 2017/9/15 15:58
 */
public class JsonUtil {
    public static boolean isSuccess(String json) {
//        try {
            if (TextUtils.isEmpty(json) || TextUtils.equals(json, "{}")) {
                return false;//数据为空时不往下执行
            }
//            JSONObject jsonObject = new JSONObject(json);
//            JSONObject result1 = jsonObject.optJSONObject("result");
//            if (TextUtils.equals(result1.optString("state"), "1"))
                return true;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    /**
     * 仅适用钱包页接口模型
     * @param json
     * @param key
     * @param code
     * @return
     */
    public static boolean isSuccess2(String json,String key,String code) {
        try {
            if (TextUtils.isEmpty(json) || TextUtils.equals(json, "{}")) {

                return false;//数据为空时不往下执行
            }
            JSONObject jsonObject = new JSONObject(json);
            String result1 = jsonObject.optString(key);
            if (TextUtils.equals(result1, code))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 仅适用钱包页接口模型
     * @param json
     * @return
     */
    public static String errMsg2(String json) {
        PrintUtil.printRespones("错误json数据",json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String result1 = jsonObject.optString("message");
            return result1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "系统繁忙,请稍后重试!";
    }
    public static String errMsg(String json) {
        PrintUtil.printRespones("错误json数据",json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result1 = jsonObject.optJSONObject("result");
            return result1.optString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "系统繁忙,请稍后重试!";
    }

    public static boolean isAgainLogin(String json, boolean isHomePage,String url) {
        try {
            if (TextUtils.isEmpty(json) || TextUtils.equals(json, "{}")) {
                return false;//数据为空时不往下执行
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result1 = jsonObject.optJSONObject("result");
            if (TextUtils.equals(result1.optString("isAgainLogin"), "01")) {
                if (!TextUtils.isEmpty(url)){
                    if (!url.contains("/sysmsg/getUnReadCnt")){
                        ToastUtil.showLongToast("您的账号已在其他设备登录，请重新登录!");
//                if (isHomePage)
                    }
                }
                return true;
            }else if (TextUtils.equals(result1.optString("isAgainLogin"), "02")){
                if (!TextUtils.isEmpty(url)) {
                    if (!url.contains("/sysmsg/getUnReadCnt")) {
                        ToastUtil.showLongToast("您的登录已过期，请重新登录！");
//                if (isHomePage)
                    }
                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 解析验证码
     *
     * @param response
     * @return
     */
    public static String getCode(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.optString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取邀请二维码
     *
     * @param response
     * @return
     */
    public static String getHref(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.optString("href");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
