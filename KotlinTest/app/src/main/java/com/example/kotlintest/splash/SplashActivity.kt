package com.example.kotlintest.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.kotlintest.R
import com.example.kotlintest.config.Config
import com.base.fruitbase.framework.base.BaseActivity

@Route(path =  Config.AppCommon_Splash)
class SplashActivity : BaseActivity<SplashPersenter>() {
    var timeAll: Long = 3000
    var timeInteral: Long = 1000
    var countDownTimer: CountDownTimer? = null
    override fun initView() {
        super.initView()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)  //隐藏状态栏
        startCountDowm()
    }

    /**
     * 倒计时
     */
    private fun startCountDowm() {
        countDownTimer = object : CountDownTimer(timeAll, timeInteral) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                ARouter.getInstance()
                    .build(Config.AppCommon_Main)
                    .navigation()
                finish()
            }
        }.start()
    }


    /**
     *  xml
     */
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_splash
    }

    /**
     * 返回 Persenter
     */
    override fun getPresenter(): SplashPersenter {
        return SplashPersenter(this)
    }
}
