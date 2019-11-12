package com.example.kotlintest.config

class Config {
    companion object {
        val isDebug = true
        private const val SCHEME = "/wolverine/"
        /**
         * 启动页
         */
        const val AppCommon_Splash = SCHEME + "appCommon/splash"
        /**
         * 首页
         */
        const val AppCommon_Main = SCHEME + "appCommon/main"
        /**
         * 查看图片页面
         */
        const val AppCommon_PhotoView = SCHEME + "appCommon/photo_view"

    }
}