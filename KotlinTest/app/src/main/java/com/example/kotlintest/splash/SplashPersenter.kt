package com.example.kotlintest.splash

import com.yimaotong.fruitbase.framework.base.BasePresenter

class SplashPersenter(rootView: SplashActivity) :
    BasePresenter<SplashActivity, SplashModel>(rootView) {

    override fun getModel(): SplashModel {
        return SplashModel()
    }
}