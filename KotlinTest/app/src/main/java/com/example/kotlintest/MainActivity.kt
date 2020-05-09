package com.example.kotlintest

import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.bottomnavigation.ui.HomeFragment
import com.example.bottomnavigation.ui.MyFragment
import com.example.bottomnavigation.ui.VideoFragment
import com.example.bottomnavigation.ui.ZhiHuNewsFragment
import com.example.kotlintest.config.Config
import com.hjq.toast.ToastUtils
import com.yimaotong.fruitbase.framework.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = Config.AppCommon_Main)
class MainActivity : BaseActivity<MainPersenter>() {
    var exitTime: Long = 0
    //当前显示的fragment
    private val CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW"
    private var fragmentManager: FragmentManager? = null
    private var currentFragment = Fragment()
    private val fragments: MutableList<Fragment> =
        mutableListOf(HomeFragment(), VideoFragment(), ZhiHuNewsFragment(), MyFragment())
    private var currentIndex = 0
    /**
     * 初始化控件
     */
    override fun initView() {
        super.initView()
        fragmentManager = supportFragmentManager
    }

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViewFragment(savedInstanceState)
    }

    /**
     * 初始化fragment
     */
    fun initViewFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) { // “内存重启”时调用
            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0)
            fragments.removeAll(fragments)
            fragmentManager?.findFragmentByTag(0.toString() + "")?.let { fragments.add(it) }
            fragmentManager?.findFragmentByTag(1.toString() + "")?.let { fragments.add(it) }
            fragmentManager?.findFragmentByTag(2.toString() + "")?.let { fragments.add(it) }
            //恢复fragment页面
            restoreFragment()
        } else {      //正常启动时调用
            showFragment()
        }
    }

    /**
     * 点击事件
     */
    override fun initListener() {
        super.initListener()
        //设置导航栏菜单项Item选中监听
        bottom_navigation.setOnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.home -> currentIndex = 0
                R.id.dashboard -> currentIndex = 1
                R.id.notifications -> currentIndex = 2
                R.id.profile -> currentIndex = 3
            }
            showFragment()
            true
        }
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private fun showFragment() {
        val transaction = fragmentManager?.beginTransaction()
        //如果之前没有添加过
        if (!fragments[currentIndex].isAdded) {
            transaction?.hide(currentFragment)
                ?.add(
                    R.id.container,
                    fragments[currentIndex],
                    "" + currentIndex
                )  //第三个参数为添加当前的fragment时绑定一个tag
        } else {
            transaction
                ?.hide(currentFragment)
                ?.show(fragments[currentIndex])
        }
        currentFragment = fragments[currentIndex]
        transaction?.commit()
    }

    /**
     * 恢复fragment
     */
    private fun restoreFragment() {
        val mBeginTreansaction = fragmentManager?.beginTransaction()
        for (i in fragments.indices) {
            if (i == currentIndex) {
                mBeginTreansaction?.show(fragments[i])
            } else {
                mBeginTreansaction?.hide(fragments[i])
            }
        }
        mBeginTreansaction?.commit()
        //把当前显示的fragment记录下来
        currentFragment = fragments[currentIndex]
    }

    /**
     * 内存重启 时保存当前的fragment名字
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex)
        super.onSaveInstanceState(outState)
    }

    /**
     * 点击返回监听
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.setView(R.layout.toast_custom_view)
                ToastUtils.setGravity(Gravity.BOTTOM, 20, 200)
                ToastUtils.show("再按一次退出仿乐享!")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 导航颜色
     */
    override fun statuBarColor(): Int {
        return R.color.colorGreen
    }

    /**
     * 返回presenter
     */
    override fun getPresenter(): MainPersenter {
        return MainPersenter(this)
    }

    /**
     * 布局xml
     */
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }
}
