package com.base.fruitbase.framework;

import com.base.fruitbase.framework.base.BasePresenter;

/**
 * @time 2017/9/18 19:14
 */
public interface IModel {

    /**
     * 释放资源 {@link BasePresenter#onDestroy()} 会默认调用{@link IModel#onDestroy()}
     */
    void onDestroy();
}
