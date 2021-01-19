package com.example.kotlintest.fragment.home.classify.wallpaper

import com.example.bottomnavigation.ui.WallpaperFragment
import com.example.kotlintest.fragment.home.classify.Photogram.WallpaperFragmentModel
import com.base.fruitbase.framework.base.BasePresenter

/**
 *Time:2019/11/01 16:51
 *Author: LiangWH
 *Description:
 */
class WallpaperFragmentPersenter(rootView: WallpaperFragment?): BasePresenter<WallpaperFragment, WallpaperFragmentModel>(rootView) {
    override fun getModel(): WallpaperFragmentModel {
        return WallpaperFragmentModel()
    }
}