package com.example.kotlintest.fragment.home

import com.example.bottomnavigation.ui.HomeFragment
import com.yimaotong.fruitbase.framework.base.BasePresenter


/**
 *Time:2019/11/01 16:51
 *Author: LiangWH
 *Description:
 */
class HomeFragmentPersenter(rootView: HomeFragment?) :
    BasePresenter<HomeFragment, HomeFragmentModel>(rootView) {

    override fun getModel(): HomeFragmentModel {
        return HomeFragmentModel()
    }
}