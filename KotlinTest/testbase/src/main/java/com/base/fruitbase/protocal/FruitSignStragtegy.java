package com.base.fruitbase.protocal;

import android.text.TextUtils;

import com.base.fruitbase.framework.base.BaseActivity;
import com.base.fruitbase.protocal.netutil.SignUtil;
import com.base.fruitbase.protocal.netutil.UserBean;
import com.base.fruitbase.protocal.netutil.UserUtil;
import com.base.fruitbase.protocal.netutil.signstrategy.ISignStrategy;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @time 2017/9/23 14:38
 */
public class FruitSignStragtegy implements ISignStrategy {
    @Override
    public Map<String, String> sign(Map<String, String> map) {
        Map<String, String> signParmas = new TreeMap<>();
        signParmas.putAll(map);
        String nonce = SignUtil.getRndStr(6 + new Random().nextInt(8));
        String timeStamp = String.valueOf(System.currentTimeMillis());
        signParmas.put("nonce", nonce);
        signParmas.put("timestamp", timeStamp);
        UserBean userBean;
        if (BaseActivity.activity != null) {
            String response = UserUtil.getCacheUserInfo();
            userBean = Convert.fromJson(response, UserBean.class);
        } else
            userBean = new UserBean();

        if (userBean != null && userBean.userInfo != null && !TextUtils.isEmpty(userBean.userInfo.loginId) && !TextUtils.isEmpty(UserUtil.getToken())) {
            signParmas.put("loginId", userBean.userInfo.loginId);
            signParmas.put("token", UserUtil.getToken());
        }
        String sign = SignUtil.sign(signParmas);
        signParmas.put("sign", sign);


        return signParmas;
    }
}
