package com.yimaotong.fruitbase.framework.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yimaotong.fruitbase.framework.base.BaseFragment;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @time 2017/9/19 14:03
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    BaseFragment fragment;

    public FragmentDelegateImpl(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (fragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(fragment);//注册到事件主线
    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        if (view != null)
            fragment.mUnbinder = ButterKnife.bind(fragment, view);
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        fragment.mPresenter = fragment.getPresenter();
        fragment.initData(savedInstanceState);
        fragment.initListener();
        fragment.setRetryEvent();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

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
    public void onDestroyView() {
//        if (fragment!=null&&fragment.mUnbinder != null && fragment.mUnbinder != fragment.mUnbinder.EMPTY) {
//            try {
//                fragment.mUnbinder.unbind();
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onDestroy() {
        if (fragment.mPresenter != null)
            fragment.mPresenter.onDestroy();//释放资源
        fragment.mPresenter = null;
        fragment.mUnbinder = null;
        if (fragment.useEventBus())
            EventBus.getDefault().unregister(fragment);
    }

    @Override
    public void onDetach() {

    }
}
