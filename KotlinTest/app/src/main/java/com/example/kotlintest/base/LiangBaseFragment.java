package com.example.kotlintest.base;

import com.base.fruitbase.framework.base.BaseFragment;
import com.base.fruitbase.framework.base.BasePresenter;

/**
 * @author LiangWH
 * @time 2019/10/19 16:00
 */
public abstract class LiangBaseFragment<P extends BasePresenter> extends BaseFragment<P> {
    @Override
    public void onResume() {
        super.onResume();
//        StatistUtil.fragLifcycle.onResume(fragmentName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        StatistUtil.fragLifcycle.onPause(getClass().getName());
    }




    /**
     * 用于统计的fragment名称
     * @return
     */
    protected abstract String fragmentName();
}
