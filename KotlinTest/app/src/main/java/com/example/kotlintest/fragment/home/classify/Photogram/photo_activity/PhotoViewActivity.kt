package com.example.kotlintest.fragment.home.classify.Photogram.photo_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.kotlintest.R
import com.example.kotlintest.base.LiangBaseActivity
import com.example.kotlintest.config.Config
import com.hjq.toast.ToastUtils
import java.util.*


/**
 *Time:2019/11/07 14:48
 *Author: LiangWH
 *Description: 图片查看页面
 */
@Route(path = Config.AppCommon_PhotoView)
class PhotoViewActivity : LiangBaseActivity<PhotoViewActivityPersenter>() {
    var imageList: ArrayList<String>? = null
    var mPos: Int = 0
    private var photoViewpager: ViewPager? = null
    private var mTvIndicator: TextView? = null
    private var toolbar: Toolbar? = null

    /**
     * 初始化控件
     */
    fun inView() {
        photoViewpager = findViewById(R.id.photo_viewpager)
        mTvIndicator = findViewById(R.id.tv_indicator)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setTransitionName(photoViewpager!!, TRANSIT_PIC)
    }

    /**
     * 点击事件监听
     */
    override fun initListener() {
        super.initListener()
        //toolbar返回监听
        toolbar?.setNavigationOnClickListener { onBackPressed() }

        photoViewpager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //改变viewpager数字
                mTvIndicator?.text =
                    ((photoViewpager?.getCurrentItem()!! + 1).toString() + "/" + imageList?.size)
            }
        })
    }

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        inView()
        initGetData()
        setAdapter()
    }

    /**
     * 得到上个页面的值
     */
    fun initGetData(){
        imageList = intent.getStringArrayListExtra("image")
        mPos = intent.getIntExtra("pos", 0)
        mTvIndicator?.text = ((mPos + 1).toString() + "/" + imageList?.size)
    }

    /**
     * 导航颜色
     */
    override fun statuBarColor(): Int {
        return R.color.colorGreen
    }

    /**
     * toolbar赋值
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_more, menu)
        return true
    }

    /**
     * 右上角条目点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                ToastUtils.show("menu_save")
                return true
            }
            R.id.menu_setting_picture -> {
                ToastUtils.show("setWallpaper")
                return true
            }
            R.id.menu_share -> {
                ToastUtils.show("menu_share")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * viewPager赋值
     */
    private fun setAdapter() {
        val adapter = imageList?.let { PhotoViewPagerAdapter(this, it) }
        photoViewpager?.adapter = adapter
        photoViewpager?.currentItem = mPos

        adapter?.setOnClickListener(object : PhotoViewPagerAdapter.onImageLayoutListener {
            override fun setOnImageOnClik() {
                onBackPressed()//返回动画
            }

            override fun setLongClick(url: String) {
                AlertDialog.Builder(this@PhotoViewActivity)
                    .setMessage(getString(R.string.ask_saving_picture))
                    .setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.dismiss() }
                    .setPositiveButton(android.R.string.ok) { dialog, which ->

                        dialog.dismiss()
                    }
                    .show()
            }
        })
    }

    /**
     * 记录页面
     */
    override fun activityName(): String {
        return "查看图片"
    }

    /**
     * XML
     */
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_photo_view
    }

    /**
     * 返回presenter
     */
    override fun getPresenter(): PhotoViewActivityPersenter {
        return PhotoViewActivityPersenter(this)
    }


    companion object {
        val TRANSIT_PIC = "transit_img"
    }
}