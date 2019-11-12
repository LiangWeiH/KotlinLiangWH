package com.example.kotlintest.base;

import com.yimaotong.fruitbase.framework.base.BaseActivity;
import com.yimaotong.fruitbase.framework.base.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author liangWH
 * @time 2019/10/19 15:58
 */
public abstract class LiangBaseActivity<P extends BasePresenter> extends BaseActivity<P> {
    CompositeSubscription mCompositeSubscription = null;

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        StatistUtil.actLifcycle.onResume(this, activityName(), isHasFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
//        StatistUtil.actLifcycle.onPause(this, activityName(), isHasFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        HeapUtil.releaseInputMethodManagerLeak(this);
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    protected void onKillAllActivity() {

//        StatistUtil.statist.onKillProcess(this);

    }

    /**
     * 是否包含有fragment
     *
     * @return 默认没有
     */
    protected boolean isHasFragment() {
        return false;
    }

    /**
     * 返回统计使用的页面名称
     *
     * @return
     */
    protected abstract String activityName();
}
