package com.example.kotlintest.fragment.home.classify.Photogram

import android.widget.TextView
import androidx.core.view.ViewCompat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlintest.R
import com.example.kotlintest.app.GlideApp
import com.example.kotlintest.bean.Feed
import com.example.kotlintest.utils.RatioImageView

/**
 *Time:2019/11/06 13:47
 *Author: LiangWH
 *Description:
 */
class PhotogramAdapter(datas: ArrayList<Feed>) : BaseQuickAdapter<Feed, BaseViewHolder>(R.layout.list_item_tuchong) {

    override fun convert(holder: BaseViewHolder?, feedListBean: Feed?) {
        val imageView = holder?.getView(R.id.iv_img) as RatioImageView

        imageView.setOriginalSize(50, 50)
        val limit = 48
        val text = if (feedListBean?.title?.length!! > limit)
            feedListBean.title.substring(0, limit) + "..."
        else
            feedListBean.title
        val textView = holder.getView(R.id.tv_title) as TextView
        textView.text = text

        val images = feedListBean.images
        if (images.isEmpty()) {
            return
        }
        val imagesBean = images.get(0)
        val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"
        GlideApp.with(mContext)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(imageView)
        holder.itemView.tag = url
        ViewCompat.setTransitionName(imageView, url)
    }
}