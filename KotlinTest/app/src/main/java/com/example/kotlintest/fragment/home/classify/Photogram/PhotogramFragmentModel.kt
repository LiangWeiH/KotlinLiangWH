package com.example.kotlintest.fragment.home.classify.Photogram

import com.example.kotlintest.config.Contstants
import com.base.fruitbase.framework.IModel
import com.base.fruitbase.protocal.netutil.BaseCallBack
import com.base.fruitbase.protocal.netutil.NetUtil

/**
 *Time:2019/11/01 16:50
 *Author: LiangWH
 *Description:
 */
class PhotogramFragmentModel : IModel {
    /**
     * 首页网络请求
     */
   fun getHomeData(page: Int, type: String,postId: String, callBack: BaseCallBack) {
        NetUtil.get()
            .url(Contstants.GETHOMEPHOTOAPI)
            .param("page", page.toString())
            .param("type", type)
            .param("post_id",postId)
            .tag(this)
            .excute(callBack)
    }

    override fun onDestroy() {
        NetUtil.cancel(this)
    }
}