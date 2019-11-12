package com.example.kotlintest.fragment.home.classify.Photogram.photo_activity

import com.example.kotlintest.fragment.home.HomeFragmentModel
import com.yimaotong.fruitbase.framework.base.BasePresenter


/**
 *Time:2019/11/01 16:51
 *Author: LiangWH
 *Description:
 */
class PhotoViewActivityPersenter(rootView: PhotoViewActivity?) :
    BasePresenter<PhotoViewActivity, PhotoViewActivityModel>(rootView) {


    override fun getModel(): PhotoViewActivityModel {
        return PhotoViewActivityModel()
    }
}