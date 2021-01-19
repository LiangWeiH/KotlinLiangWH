package com.example.kotlintest

import com.base.fruitbase.framework.base.BasePresenter

class MainPersenter(rootView: MainActivity?) : BasePresenter<MainActivity, MainModel>(rootView) {
    override fun getModel(): MainModel {
        return MainModel()
    }


}