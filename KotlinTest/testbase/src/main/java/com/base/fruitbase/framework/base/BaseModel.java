package com.base.fruitbase.framework.base;

import com.base.fruitbase.framework.IModel;
import com.base.fruitbase.protocal.netutil.NetUtil;

/**
 * @time 2017/10/16 10:33
 */
public class BaseModel implements IModel {
    @Override
    public void onDestroy() {
        NetUtil.cancel(this);
    }
}
