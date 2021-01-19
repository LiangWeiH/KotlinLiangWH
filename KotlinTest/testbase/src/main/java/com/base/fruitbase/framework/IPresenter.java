package com.base.fruitbase.framework;

/**
 * @time 2017/9/18 19:15
 */
public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * {@link com.base.fruitbase.framework.base.BaseActivity onDestroy()} 会默认调用{@link IPresenter#onDestroy()}
     */
    void onDestroy();

}
