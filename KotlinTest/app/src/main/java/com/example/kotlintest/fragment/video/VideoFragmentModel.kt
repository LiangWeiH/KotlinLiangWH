package com.example.kotlintest.fragment.video

import com.example.kotlintest.config.Contstants
import com.base.fruitbase.framework.IModel
import com.base.fruitbase.protocal.netutil.BaseCallBack
import com.base.fruitbase.protocal.netutil.NetUtil

/**
 *Time:2020/05/09 10:42
 *Author: LiangWH
 *Description:
 */
class VideoFragmentModel:IModel{
    /**
     * 首页网络请求
     */
    fun getVideoData(page: Int, type: String,postId: String,timeStamp:String, callBack: BaseCallBack) {
        NetUtil.get()
            .url(Contstants.VIDEO_API)
            .param("page", page.toString())
            .param("type", type)
            .param("post_id",postId)
            .param("num","2")
            .param("date",timeStamp)
            .tag(this)
            .excute(callBack)
    }

    override fun onDestroy() {
        NetUtil.cancel(this)
    }
}