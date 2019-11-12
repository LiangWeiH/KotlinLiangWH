package com.example.kotlintest.fragment.home.classify.Photogram

import com.example.bottomnavigation.ui.PhotogramFragment
import com.example.kotlintest.bean.Feed
import com.example.kotlintest.bean.HomePhotoBean
import com.example.kotlintest.utils.ShowLodingUtils
import com.yimaotong.fruitbase.framework.base.BasePresenter
import com.yimaotong.fruitbase.protocal.Convert
import com.yimaotong.fruitbase.protocal.netutil.BaseCallBack
import com.yimaotong.fruitbase.protocal.netutil.PrintUtil


/**
 *Time:2019/11/01 16:51
 *Author: LiangWH
 *Description:
 */
class PhotogramFragmentPersenter(rootView: PhotogramFragment?) :
    BasePresenter<PhotogramFragment, PhotogramFragmentModel>(rootView) {
    var datas: MutableList<Feed>? = null
    var adapter: PhotogramAdapter? = null

    init {
        datas = ArrayList()
        adapter = PhotogramAdapter(datas as ArrayList<Feed>)
        mRootView.setAdapter(adapter!!)
    }

    /**
     * 得到网络请求后的操作
     */
    fun getHomeData(page: Int, type: String, postId: String) {
        ShowLodingUtils.showCutscenes("加载中...", mRootView?.context)
        mModel?.getHomeData(page, type, postId, object : BaseCallBack() {
            override fun onSuccessed(response: String) {
                PrintUtil.printRespones("getHomeData", response)
                ShowLodingUtils.dismissCutscenes()
                val homePhotoBean = Convert.fromJson(response, HomePhotoBean::class.java)
                if (homePhotoBean.feedList.isEmpty()) {
                    mRootView?.loadMoreFail()
                } else {
                    mRootView?.loadMoreEnd()
                    mRootView?.dissRetryView()
                    val goodsList = homePhotoBean.feedList
                    mRootView.postId = goodsList[goodsList.size - 1].post_id.toString()
                    if (page == 1) datas?.clear()
                    datas?.addAll(goodsList)
                    adapter?.setNewData(datas)
                }
                if (page == 1) {
                    datas?.clear()
                    datas?.addAll(homePhotoBean.feedList)
                    adapter?.setNewData(datas)
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

    override fun getModel(): PhotogramFragmentModel {
        return PhotogramFragmentModel()
    }
}