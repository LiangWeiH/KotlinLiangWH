package com.yimaotong.fruitbase.framework.base;

import com.yimaotong.fruitbase.framework.IModel;
import com.yimaotong.fruitbase.framework.IPresenter;
import com.yimaotong.fruitbase.framework.IView;

import org.simple.eventbus.EventBus;

/**
 * @time 2017/9/18 19:20
 */
public abstract class BasePresenter<V extends IView,M extends IModel> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();

    protected M mModel;
    protected V mRootView;

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    @Override
    public void onStart() {
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().register(this);//注册 Eventbus
        if (mModel == null)
            mModel = getModel();
    }


    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().unregister(this);//解除注册 Eventbus
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
    }

    /**
     * 是否使用 {@link EventBus},默认为使用(true)，
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }


    /**
     * 返回对应的model
     *
     * @return
     */
    protected abstract M getModel();


}
