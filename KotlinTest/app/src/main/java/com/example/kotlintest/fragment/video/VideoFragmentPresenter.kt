package com.example.kotlintest.fragment.video

import android.net.Uri
import com.example.bottomnavigation.ui.VideoFragment
import com.example.kotlintest.bean.MultiVideo
import com.example.kotlintest.bean.VideoBean
import com.example.kotlintest.utils.ShowLodingUtils
import com.base.fruitbase.framework.base.BasePresenter
import com.base.fruitbase.protocal.Convert
import com.base.fruitbase.protocal.netutil.BaseCallBack
import com.base.fruitbase.protocal.netutil.PrintUtil

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
    fun getVideoData(page: Int, type: String, postId: String,timeStamp:String) {
        ShowLodingUtils.showCutscenes("加载中...", mRootView?.context)
        mModel?.getVideoData(page, type, postId,timeStamp, object : BaseCallBack() {
            override fun onSuccessed(response: String) {
                ShowLodingUtils.dismissCutscenes()
                val homePhotoBean = Convert.fromJson(response, VideoBean::class.java)
                //加载更多需要的参数
                val nextUrl = homePhotoBean.nextPageUrl
                mRootView.timeStamp = Uri.parse(nextUrl).getQueryParameter("date")!!

                PrintUtil.printRespones("getVideoData", homePhotoBean.toString())
                if (homePhotoBean.issueList.isEmpty()) {
                    mRootView?.loadMoreFail()
                    if (page == 1) {
                        mRootView.showEmptyView()
                    }
                } else {
                    mRootView?.loadMoreEnd()
                    mRootView?.dissRetryView()
                    if (page == 1) datas?.clear()
                    dealData(homePhotoBean, page)
                }
                if (page == 1) {
                    datas?.clear()
                    dealData(homePhotoBean, page)
                    mRootView.refreshDone()
                }
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
                    head.category=feedListBean.itemList[j].data.category
                    head.duration=feedListBean.itemList[j].data.duration
                    head.title=feedListBean.itemList[j].data.title
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