package com.yimaotong.fruitbase.protocal;

import com.yimaotong.fruitbase.protocal.netutil.UserBean;
import com.yimaotong.fruitbase.protocal.netutil.UserUtil;
import com.yimaotong.fruitbase.protocal.netutil.extra.IExtra;

/**
 * @time 2017/9/23 16:23
 */
public class LoginExtra implements IExtra {
    @Override
    public boolean isLogined() {
        UserBean userBean = UserUtil.getCacheUserBean();
        if (!UserUtil.isNotNull(userBean)) {
//            UIUtils.startActivity(LoginActivity.class);
            return false;
        } else
            return true;
    }
}
