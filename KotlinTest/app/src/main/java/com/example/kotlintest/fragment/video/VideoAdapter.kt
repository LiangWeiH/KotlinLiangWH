package com.example.kotlintest.fragment.video

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlintest.R
import com.example.kotlintest.app.GlideApp
import com.example.kotlintest.bean.Feed
import com.example.kotlintest.bean.MultiVideo
import com.example.kotlintest.fragment.home.classify.Photogram.PhotogramAdapter
import com.example.kotlintest.utils.RatioImageView

/**
 *Time:2020/05/09 11:28
 *Author: LiangWH
 *Description:
 */
class VideoAdapter(datas: ArrayList<MultiVideo>) :
    BaseMultiItemQuickAdapter<MultiVideo, BaseViewHolder>(datas) {

    init {
        addItemType(MultiVideo.VIDEO, R.layout.list_home_video_item)
        addItemType(MultiVideo.TEXT, R.layout.list_home_text_item)
        addItemType(MultiVideo.BANNER, R.layout.list_home_item_banner)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiVideo?) {
        when (item?.getItemType()) {
            MultiVideo.VIDEO -> {
                GlideApp.with(mContext)
                    .load(item.feed)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(helper!!.getView(R.id.iv) as ImageView)
            }
            MultiVideo.TEXT -> {
                helper?.setText(R.id.tv_home_text,item.text)
            }
            MultiVideo.BANNER -> {
                GlideApp.with(mContext)
                    .load(item.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(helper!!.getView(R.id.iv_banner) as ImageView)
            }
        }
    }


}