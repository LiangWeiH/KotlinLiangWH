package com.example.kotlintest.splash

import com.base.fruitbase.framework.base.BasePresenter

class SplashPersenter(rootView: SplashActivity) :
    BasePresenter<SplashActivity, SplashModel>(rootView) {

    override fun getModel(): SplashModel {
        return SplashModel()
    }
}