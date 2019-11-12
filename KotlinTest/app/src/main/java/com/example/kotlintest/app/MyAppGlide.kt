package com.example.kotlintest.app

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions



/**
 *Time:2019/11/07 16:09
 *Author: LiangWH
 *Description: Glide 4.8.0需要添加的，添加后 Make project
 */
@GlideModule
class MyAppGlide: AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}