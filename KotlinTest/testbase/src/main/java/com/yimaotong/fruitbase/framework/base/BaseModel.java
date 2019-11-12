package com.yimaotong.fruitbase.framework.base;

import com.yimaotong.fruitbase.framework.IModel;
import com.yimaotong.fruitbase.protocal.netutil.NetUtil;

/**
 * @time 2017/10/16 10:33
 */
public class BaseModel implements IModel {
    @Override
    public void onDestroy() {
        NetUtil.cancel(this);
    }
}
