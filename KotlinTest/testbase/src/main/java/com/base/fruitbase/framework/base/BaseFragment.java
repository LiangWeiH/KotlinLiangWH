package com.base.fruitbase.framework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.base.fruitbase.framework.IView;
import com.base.fruitbase.framework.delegate.FragmentDelegate;
import com.base.fruitbase.framework.delegate.FragmentDelegateImpl;

import butterknife.Unbinder;

/**
 * @time 2017/9/18 20:00
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {

    public P mPresenter;
    public Unbinder mUnbinder;
    protected FragmentDelegate fragmentDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentDelegate = new FragmentDelegateImpl(this);
        fragmentDelegate.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = creatView(inflater);
        fragmentDelegate.onCreateView(view, savedInstanceState);
        initView(savedInstanceState);
        return view;
    }

    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentDelegate.onActivityCreate(savedInstanceState);
    }

    public abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDelegate.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDelegate.onDestroy();
    }

    /**
     * 是否已添加进activity
     *
     * @return
     */
    public boolean isHasAdd() {
        return isAdded();
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

    public boolean useEventBus() {
        return true;
    }

    public View creatView(LayoutInflater inflater) {
        return inflater.inflate(getLayoutId(), null);
    }

    protected abstract int getLayoutId();

    public void initData(Bundle savedInstanceState) {
    }

    ;

    public void initListener() {
    }

    ;

}
