package com.example.kotlintest.app

import android.annotation.SuppressLint
import android.util.Log
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.base.fruitbase.framework.base.BaseApplication
import com.example.kotlintest.MainActivity
import com.example.kotlintest.R
import com.base.fruitbase.ui.error.CustomErrorActivity
import com.example.kotlintest.base.AppService

/**
 * MyApp App启动的时候需要初始化的东西
 */
class MyApp : BaseApplication() {

    @SuppressLint("RestrictedApi")
    override fun onCreate() {
        super.onCreate()
        //阿里路由
//        ARouter.openLog()    // 打印日志
//        ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this) // 尽可能早，推荐在Application中初始化    //整个配置属性，可以设置一个或多个，也可以一个都不设置
        AppService.startInitService(this)
        //检测异常崩溃的
        CaocConfig.Builder.create()
            //程序在后台时，发生崩溃的三种处理方式
            //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
            //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
            //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true)     //false表示对崩溃的拦截阻止。用它来禁用customactivityoncrash框架
            .showErrorDetails(true) //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪,—》针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。。
            .showRestartButton(false)    //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
            .trackActivities(true)     //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
            .minTimeBetweenCrashesMs(1000)      //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
            .errorDrawable(R.mipmap.enjoylife)     //崩溃页面显示的图标
            .restartActivity(MainActivity::class.java)      //重新启动后的页面
            .errorActivity(CustomErrorActivity::class.java) //程序崩溃后显示的页面
            .eventListener(CustomEventListener())//设置监听
            .apply()

        //如果没有任何配置，程序崩溃显示的是默认的设置
        CustomActivityOnCrash.install(this)

    }

    private class CustomEventListener : CustomActivityOnCrash.EventListener {
        override fun onLaunchErrorActivity() {
            Log.i("ERROR", "onLaunchErrorActivity()")
        }

        override fun onRestartAppFromErrorActivity() {
            Log.i("ERROR", "onRestartAppFromErrorActivity()")
        }

        override fun onCloseAppFromErrorActivity() {
            Log.i("ERROR", "onCloseAppFromErrorActivity()")
        }
    }


}