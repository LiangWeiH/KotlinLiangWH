package com.example.bottomnavigation.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.kotlintest.R
import com.example.kotlintest.base.LiangBaseFragment
import com.example.kotlintest.fragment.home.HomeFragmentPersenter
import com.example.kotlintest.ui.ViewPagerAdapter
import com.example.kotlintest.utils.ScaleTransitionPagerTitleView
import kotlinx.android.synthetic.main.fragment_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * 此页面是为了为
 * 摄影 壁纸 页面赋值
 */
class HomeFragment : LiangBaseFragment<HomeFragmentPersenter>() {
    var mDataList = arrayListOf("摄影", "壁纸")
    //在这里添加fragment是因为为了避免重复创建
    var fragments: MutableList<Fragment> = mutableListOf(PhotogramFragment(), WallpaperFragment())
    internal var pagerAdapter: ViewPagerAdapter? = null

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViewTab()
    }

    /**
     * 初始化控件
     */
    private fun initViewTab() {
        pagerAdapter = ViewPagerAdapter(childFragmentManager, fragments)
        //viewPager赋值
        view_pager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = fragments.size
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {}
            })
        }

        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int { return mDataList.size }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return ScaleTransitionPagerTitleView(context).apply {
                    text = mDataList[index]
                    textSize = 18f
                    normalColor = Color.WHITE
                    selectedColor = Color.WHITE
                    setOnClickListener {
                        view_pager.setCurrentItem(index, false)
                    }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
                    lineWidth = UIUtil.dip2px(context, 30.0).toFloat()
                    roundRadius = UIUtil.dip2px(context, 6.0).toFloat()
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    setColors(Color.WHITE)
                }
            }
        }
        magic_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(magic_indicator, view_pager)
    }

    /**
     * 返回xml
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    /**
     * 记录页面
     */
    override fun fragmentName(): String {
        return "首页"
    }

    /**
     * 返回Presenter
     */
    override fun getPresenter(): HomeFragmentPersenter {
        return HomeFragmentPersenter(this)
    }


}