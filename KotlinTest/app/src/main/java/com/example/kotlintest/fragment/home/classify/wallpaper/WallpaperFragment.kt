package com.example.bottomnavigation.ui

import android.os.Bundle
import com.example.kotlintest.R
import com.example.kotlintest.base.LiangBaseFragment
import com.example.kotlintest.fragment.home.classify.wallpaper.WallpaperFragmentPersenter


class WallpaperFragment : LiangBaseFragment<WallpaperFragmentPersenter>() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    /**
     * XMl
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_wall
    }

    /**
     * 记录页面
     */
    override fun fragmentName(): String {
        return "壁纸页面"
    }

    /**
     * 返回Presenter
     */
    override fun getPresenter(): WallpaperFragmentPersenter {
        return WallpaperFragmentPersenter(this)
    }

    companion object {
        fun newInstance() = WallpaperFragment()
    }

}