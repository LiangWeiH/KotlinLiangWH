package com.base.fruitbase.framework.base;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fruitbase.R;
import com.gyf.immersionbar.ImmersionBar;
import com.base.fruitbase.framework.IView;
import com.base.fruitbase.framework.delegate.ActivityDelegate;
import com.base.fruitbase.framework.delegate.ActivityDelegateImpl;
import com.base.fruitbase.ui.BGASwipeBack.BGAKeyboardUtil;
import com.base.fruitbase.ui.BGASwipeBack.BGASwipeBackHelper;
import com.base.fruitbase.ui.lec.LoadingAndRetryManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * 所有Activity基类
 *
 * @time 2017/9/18 19:37
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView, BGASwipeBackHelper.Delegate {
    public P mPresenter;
    public Unbinder mUnbinder;
    protected ActivityDelegate activityDelegate;
    // 用来存放所有存活的activity
    public static List<BaseActivity> activities = new ArrayList<>();
    public static BaseActivity activity;//当前的Activity
    public LoadingAndRetryManager mLoadingAndRetryManager;
    private BGASwipeBackHelper mSwipeBackHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        activityDelegate = new ActivityDelegateImpl(this);
        activityDelegate.onCreate(savedInstanceState);
    }

    /**
     * onCreate之前做一些操作
     *
     * @param savedInstanceState
     */
    protected void beforeOnCreate(Bundle savedInstanceState) {
        if (isSwipeBack())
            initSwipeBackFinish();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if (mSwipeBackHelper != null)
            mSwipeBackHelper.swipeBackward();
    }

//    @Override
//    public void onBackPressed() {
//        if (isSwipeBack())
//            finishActivity();
//        else {
//            finishNoSwipeBack();
//        }
//    }

    /**
     * 非滑动返回时
     */
    protected void finishNoSwipeBack() {
        super.onBackPressed();
        BGAKeyboardUtil.closeKeyboard(this);
        overridePendingTransition(R.anim.bga_sbl_activity_backward_enter, R.anim.bga_sbl_activity_backward_exit);
    }

    /**
     * 滑动返回
     */
    public void finishActivity() {
        if (mSwipeBackHelper != null) {
            // 正在滑动返回的时候取消返回按钮事件
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        } else {
            finishNoSwipeBack();
        }
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 是否允许侧滑返回
     * dialog样式的activity不允许滑动返回，有冲突
     *
     * @return
     */
    protected boolean isSwipeBack() {
        return true;
    }

    /**
     * 网络请求错误重试事件
     */
    protected void onRetryEvent() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        beforeOnSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void beforeOnSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityDelegate != null)
            activityDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityDelegate != null){
            activityDelegate.onDestroy();
        }

        // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this);
    }

    /**
     * 是否使用eventBus,默认为使用(true)
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showRetryView() {

    }

    @Override
    public void setRetryEvent() {

    }

    /**
     * 返回布局Id
     *
     * @param savedInstanceState
     * @return
     */
    public abstract int getLayoutId(Bundle savedInstanceState);

    /**
     * 返回presenter
     *
     * @return
     */
    public abstract P getPresenter();

    /**
     * 填充数据
     *
     * @param savedInstanceState
     */
    public void initData(Bundle savedInstanceState) {
        initStatuBar();
    }


    /**
     * ButterKnif可能有问题,使用这个方法查找
     */
    public void initView() {

    }

    /**
     * 设置监听
     */
    public void initListener() {
    }

    /**
     * 退出所有activity
     */
    public void killAll() {
        List<BaseActivity> copy;
        synchronized (activities) {
            copy = new ArrayList<>(activities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        onKillAllActivity();
    }

    protected void onKillAllActivity() {
    }

    /**
     * 状态栏
     */
    protected void initStatuBar() {
        if (isTranspantStatuBar()) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                    .transparentStatusBar()//透明状态栏
                    .init();
        } else {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                    .statusBarColor(statuBarColor())
                    .init();
        }
    }

    /**
     * 状态栏是否透明
     *
     * @return
     */
    protected boolean isTranspantStatuBar() {
        return false;
    }

    /**
     * 状态栏颜色
     *
     * @return
     */
    protected int statuBarColor() {
        return R.color.b_2;
    }
}
