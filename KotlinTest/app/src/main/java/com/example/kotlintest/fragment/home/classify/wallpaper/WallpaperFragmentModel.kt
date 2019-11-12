package com.example.kotlintest.fragment.home.classify.Photogram

import com.yimaotong.fruitbase.framework.IModel
import com.yimaotong.fruitbase.protocal.netutil.NetUtil

/**
 *Time:2019/11/01 16:50
 *Author: LiangWH
 *Description:
 */
class WallpaperFragmentModel :IModel{

    override fun onDestroy() {
        NetUtil.cancel(this)
    }
}