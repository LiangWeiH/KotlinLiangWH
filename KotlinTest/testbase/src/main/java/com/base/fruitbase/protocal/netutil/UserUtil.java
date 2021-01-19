package com.base.fruitbase.protocal.netutil;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.CookieManager;

import com.base.fruitbase.framework.base.BaseActivity;
import com.base.fruitbase.protocal.Convert;
import com.base.fruitbase.util.SPUtils;

import org.simple.eventbus.EventBus;

/**
 * 用户操作工具类
 * @time 2017/9/25 11:38
 */
public class UserUtil {

    /**
     * 缓存用户信息到本地
     *
     * @param respone
     */
    public static void cacheUserInfo(String respone) {
        SPUtils.putString(Config.CACHEKEY.USERINFO, respone);
        EventBus.getDefault().post("refreshUserInfo", "refreshUserInfo");
    }

    /**
     * 用户是否存在
     *
     * @param userBean
     * @return
     */
    public static boolean isNotNull(UserBean userBean) {
        if (userBean != null && userBean.userInfo != null)
            return true;
        else
            return false;
    }

    /**
     * 获取本地保存的用户信息
     *
     * @return
     */
    public static String getCacheUserInfo() {
        return SPUtils.getString(Config.CACHEKEY.USERINFO);
    }

    /**
     * 用户是否已登录
     *
     * @return
     */
    public static boolean isLogined() {
        UserBean cacheUserInfo = getCacheUserBean();
        if (UserUtil.isNotNull(cacheUserInfo) && !TextUtils.isEmpty(cacheUserInfo.userInfo.id))
            return true;
        else
            return false;
    }

    /**
     * 用户未登录则去登陆
     * 用户已登录则继续执行
     */
    public static boolean isCanNext(Activity activity) {
        if (isLogined())
            return true;
        else {
//            Intent intent = new Intent(activity, LoginActivity2.class);
//            activity.startActivity(intent);
//            UIUtils.startActivity(LoginActivity2.class);//R.anim.slide_bottom_in, R.anim.slide_bottom_out
//            activity.overridePendingTransition(R.anim.act_open_enter_scale_in, 0);//弾框模式
//            activity.overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            return false;
        }
    }

    /**
     * 获取缓存的用户实体类
     *
     * @return
     */
    public static UserBean getCacheUserBean() {
        String cacheUserInfo = getCacheUserInfo();
        UserBean userBean = Convert.fromJson(cacheUserInfo, UserBean.class);
        return userBean;
    }

    /**
     * 退出登录
     */
    public static void loginOut() {
        //清除别名
        UserBean cacheUserBean = UserUtil.getCacheUserBean();
        if (cacheUserBean != null)
//            Push.deleteAlias(UIUtils.getContext(), Integer.valueOf(cacheUserBean.userInfo.loginId), cacheUserBean.userInfo.id);
//        JPushInterface.clearAllNotifications(FruitApp.getContext());//清空通知

        SPUtils.clearPreference(Config.CACHEKEY.USERINFO);
        Config.CHECKTYPE.isRefreshUserInfo = true;
        clearCookieSession();
        clearToken();
        if (BaseActivity.activity != null)
            EventBus.getDefault().post("loginOut", "loginOut");
    }

    /**
     * 是否是第一次进入app
     *
     * @return
     */
    public static boolean isFirst() {
        return SPUtils.getBoolean("isFirst");
    }

    public static void saveToken(String token) {
        SPUtils.putString("token", token);
    }

    public static String getToken() {
        return SPUtils.getString("token");
    }

    public static String getIsShow() {
        return SPUtils.getString("isShow");
    }
    public static String getShareIsShow() {
        return SPUtils.getString("shareIsShow");
    }
    public static String getShareTitle() {
        return SPUtils.getString("shareTitle");
    }

    public static String getShareGoodsId() {
        return SPUtils.getString("shareGoodsId");
    }
    public static String getShareCodew() {
        return SPUtils.getString("shareCode");
    }
    public static String getRegisterMsg() {
        return SPUtils.getString("registerMsg");
    }
    public static String getLoginMsg() {
        return SPUtils.getString("loginMsg");
    }

    public static void clearToken() {
        SPUtils.clearPreference("token");
    }

    /**
     * 修改是否是第一次进入的状态
     */
    public static void editIsNotFirst() {
        SPUtils.putBoolean("isFirst", false);
    }

    /**
     * 清除cookie和session
     */
    private static void clearCookieSession() {
        CookieManager.getInstance().removeSessionCookie();
    }



}
