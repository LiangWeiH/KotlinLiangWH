package com.example.kotlintest.fragment.video

import com.example.bottomnavigation.ui.VideoFragment
import com.example.kotlintest.bean.MultiVideo
import com.example.kotlintest.bean.VideoBean
import com.example.kotlintest.utils.ShowLodingUtils
import com.yimaotong.fruitbase.framework.base.BasePresenter
import com.yimaotong.fruitbase.protocal.Convert
import com.yimaotong.fruitbase.protocal.netutil.BaseCallBack
import com.yimaotong.fruitbase.protocal.netutil.PrintUtil

/**
 *Time:2020/05/09 10:43
 *Author: LiangWH
 *Description:
 */
class VideoFragmentPresenter(rootView: VideoFragment?) :
    BasePresenter<VideoFragment, VideoFragmentModel>(rootView) {
    var datas: MutableList<MultiVideo>? = null
    var adapter: VideoAdapter? = null

    init {
        datas = ArrayList()
        adapter = VideoAdapter(datas as ArrayList<MultiVideo>)
        mRootView.setAdapter(adapter!!)
    }

    /**
     * 得到网络请求后的操作
     */
    fun getVideoData(page: Int, type: String, postId: String) {
        ShowLodingUtils.showCutscenes("加载中...", mRootView?.context)
        mModel?.getVideoData(page, type, postId, object : BaseCallBack() {
            override fun onSuccessed(response: String) {
                ShowLodingUtils.dismissCutscenes()
                val homePhotoBean = Convert.fromJson(response, VideoBean::class.java)
                PrintUtil.printRespones("getVideoData", homePhotoBean.toString())
                dealData(homePhotoBean, page)
//                if (homePhotoBean.issueList.isEmpty()) {
//                    mRootView?.loadMoreFail()
//                } else {
//                    mRootView?.loadMoreEnd()
//                    mRootView?.dissRetryView()
////                    var goodsList: MutableList<VideoBean.IssueListBean> = homePhotoBean.issueList
////                    if (page == 1) datas?.clear()
////                    datas?.addAll(goodsList)
////                    adapter?.setNewData(datas)
//                }
//                if (page == 1) {
////                    datas?.clear()
////                    datas?.addAll(homePhotoBean.issueList)
////                    adapter?.setNewData(datas)
////                    mRootView.refreshDone()
//                }
            }

            override fun onError(errMsg: String) {
                super.onError(errMsg)
                ShowLodingUtils.dismissCutscenes()
                mRootView.loadMoreFail()
                if (page == 1) {
                    mRootView.refreshDone()
                }
            }

            override fun onFail() {
                super.onFail()
                ShowLodingUtils.dismissCutscenes()
                if (page == 1) {
                    mRootView.refreshDone()
                } else
                    mRootView.loadMoreFail()
            }
        })
    }

    fun dealData(videoBean: VideoBean, page: Int) {
        if (videoBean?.issueList == null) {
            if (page == 1) {
                mRootView.showEmptyView()
            }
            return
        }
        val issueList = videoBean.issueList
        for (i in issueList.indices) {
            val feedListBean = issueList[i]
            for (j in feedListBean.itemList.indices){
                if (feedListBean.itemList[j].type=="banner2"){
                    val head = MultiVideo(MultiVideo.BANNER)
                    head.image =feedListBean.itemList[j].data.image
                    datas!!.addAll(listOf(head))
                }else if (feedListBean.itemList[j].type=="video"){
                    val head = MultiVideo(MultiVideo.VIDEO)
                    head.feed =feedListBean.itemList[j].data.cover.feed
                    datas!!.addAll(listOf(head))
                }
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun getModel(): VideoFragmentModel {
        return VideoFragmentModel()
    }
}