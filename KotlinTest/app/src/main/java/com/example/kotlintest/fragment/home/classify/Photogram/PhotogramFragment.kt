package com.example.bottomnavigation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.kotlintest.R
import com.example.kotlintest.base.LiangBaseFragment
import com.example.kotlintest.bean.Feed
import com.example.kotlintest.fragment.home.classify.Photogram.PhotogramAdapter
import com.example.kotlintest.fragment.home.classify.Photogram.PhotogramFragmentPersenter
import com.example.kotlintest.fragment.home.classify.Photogram.photo_activity.PhotoViewActivity
import com.example.kotlintest.utils.FixedRecyclerView
import com.example.kotlintest.utils.FruitLoadMoreBottomView
import com.hjq.toast.ToastUtils
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView
import com.base.fruitbase.util.DeviceUtils
import kotlinx.android.synthetic.main.healnetwork.*
import java.util.*

/**
 *Time:2019/11/07 14:48
 *Author: LiangWH
 *Description: 首页fragment
 */
class PhotogramFragment : LiangBaseFragment<PhotogramFragmentPersenter>() {
    var page: Int = 1
    var listRefresh: String = "refresh"
    var listLoadMore: String = "loadmore"
    var recyclerView: FixedRecyclerView? = null
    var heal_retry_view: RelativeLayout? = null
    var swipeRefresh: TwinklingRefreshLayout? = null
    var postId: String = ""

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initRefresh()
        initIsConnected()
    }

    /**
     *当打开App的时候判断是否有网如果没有网络直接展示没网页面
     * 如果有网络 的话直接网络请求并赋值
     */
    fun initIsConnected() {
        heal_retry_view =
            view?.findViewById(R.id.heal_retry_view)

        if (!DeviceUtils.netIsConnected(activity)) {
            heal_retry_view?.visibility = View.VISIBLE
            swipeRefresh?.visibility = View.GONE
            setGone()
        } else {
            mPresenter.getHomeData(page, listRefresh, postId)
        }
    }

    /**
     * 初始化刷新控件并指定刷新动画
     */
    fun initRefresh() {
        swipeRefresh =
            view?.findViewById(R.id.swipeRefresh)
        val headerView = SinaRefreshView(activity)
        headerView.setArrowResource(R.drawable.down_arrow)
        headerView.setTextColor(-0x8ba2a4)
        swipeRefresh?.setHeaderView(headerView)

        val loadingView = FruitLoadMoreBottomView(activity)
        swipeRefresh?.setBottomView(loadingView)
        swipeRefresh?.setEnableOverScroll(false)

        swipeRefresh?.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                loadMore()
            }

            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                page = 1
                mPresenter.getHomeData(page, listRefresh, postId)
            }
        })
    }
    /**
     * 隐藏重试页面
     */
    fun dissRetryView() {
        heal_retry_view?.visibility = View.GONE
        swipeRefresh?.visibility = View.VISIBLE
    }

    /**
     * adapter赋值
     */
    fun setAdapter(photoAdapter: PhotogramAdapter) {
        recyclerView =
            view?.findViewById<FixedRecyclerView>(R.id.recyclerview)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.layoutManager = manager
        recyclerView?.adapter = photoAdapter
        //点击跳转页面
        photoAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val data: MutableList<Feed> = adapter.data as MutableList<Feed>
                val images = data[position].images
                //点击的条目是否还有图片
                if (images.isEmpty()) {
                    return@OnItemClickListener
                }
                //如果有的话直接添加到集合
                val imageList = ArrayList<String>()
                for (i in images.indices) {
                    val imageData = images[i]
                    val url =
                        "https://photo.tuchong.com/" + imageData.user_id + "/f/" + imageData.img_id + ".jpg"
                    imageList.add(url)
                }
                //页面跳转传值
                val intent = Intent(activity, PhotoViewActivity::class.java)
                intent.putStringArrayListExtra("image", imageList)
                intent.putExtra("pos", 0)
                //添加动画
                val optionsCompat = activity?.let {
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        it, view, PhotoViewActivity.TRANSIT_PIC
                    )
                }
                try {
                    activity?.let {
                        ActivityCompat.startActivity(
                            it,
                            intent,
                            optionsCompat?.toBundle()
                        )
                    }
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    startActivity(intent)
                }
            }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        page++
        mPresenter.getHomeData(page, listLoadMore, postId)
    }

    /**
     * 结束加载
     */
    fun loadMoreEnd() {
        swipeRefresh?.finishLoadmore()
    }

    /**
     * 没有更多数据失败
     */
    fun loadMoreFail() {
        page--
        swipeRefresh?.finishLoadmore()
    }

    /**
     * 刷新结束
     */
    fun refreshDone() {
        swipeRefresh?.finishRefreshing()
    }

    /**
     * 网络重试
     */
    private fun setGone() {
        retry?.setOnClickListener(View.OnClickListener {
            if (DeviceUtils.netIsConnected(activity)) {
                page = 1
                mPresenter.getHomeData(page, listRefresh, postId)
            } else
                ToastUtils.show("亲,网络似乎逃往外星了哦！")
        })
    }

    /**
     * 记录页面
     */
    override fun fragmentName(): String {
        return "摄影"
    }

    /**
     * 返回Presenter
     */
    override fun getPresenter(): PhotogramFragmentPersenter {
        return PhotogramFragmentPersenter(this)
    }

    /**
     * 返回Xml
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_photo
    }

    companion object {
        val TAG: String = PhotogramFragment::class.java.simpleName
        fun newInstance() = PhotogramFragment()
    }

}