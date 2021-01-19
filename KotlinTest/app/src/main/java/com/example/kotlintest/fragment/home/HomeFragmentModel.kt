package com.example.kotlintest.fragment.home

import com.base.fruitbase.framework.IModel
import com.base.fruitbase.protocal.netutil.NetUtil

/**
 *Time:2019/11/01 16:50
 *Author: LiangWH
 *Description:
 */
class HomeFragmentModel : IModel {

    override fun onDestroy() {
        NetUtil.cancel(this)
    }
}