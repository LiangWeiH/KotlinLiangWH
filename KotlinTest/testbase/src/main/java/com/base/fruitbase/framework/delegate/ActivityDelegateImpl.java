package com.base.fruitbase.framework.delegate;
import android.os.Bundle;
import com.base.fruitbase.framework.base.BaseActivity;
import org.simple.eventbus.EventBus;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity生命周期代理类
 * @time 2017/9/19 13:59
 */
public class ActivityDelegateImpl implements ActivityDelegate {

    BaseActivity activity;

    public ActivityDelegateImpl(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            int layoutResID = activity.getLayoutId(savedInstanceState);
            if (layoutResID != 0) {
                activity.setContentView(layoutResID);
                activity.mUnbinder = ButterKnife.bind(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //添加到集合中统一维护
        synchronized (BaseActivity.activities) {
            BaseActivity.activities.add(activity);
        }

        if (activity.useEventBus())
            EventBus.getDefault().register(activity);
        activity.mPresenter = activity.getPresenter();
        activity.initView();
        activity.initData(savedInstanceState);
        activity.initListener();
        activity.setRetryEvent();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        BaseActivity.activity = activity;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (activity.useEventBus())
            EventBus.getDefault().unregister(activity);
        if (activity!=null&&activity.mUnbinder != null && activity.mUnbinder != Unbinder.EMPTY)
            activity.mUnbinder.unbind();
        activity.mUnbinder = null;
        if (activity.mPresenter != null)
            activity.mPresenter.onDestroy();//释放资源
        activity.mPresenter = null;
        BaseActivity.activities.remove(activity);
        if (BaseActivity.activities.size() == 0) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }
}
