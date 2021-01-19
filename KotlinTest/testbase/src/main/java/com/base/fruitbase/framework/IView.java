package com.base.fruitbase.framework;

/**
 * @time 2017/9/18 19:13
 */
public interface IView {
    /**
     * 显示正在加载
     */
    void showLoading();

    /**
     * 隐藏正在加载
     */
    void dissLoading();

    /**
     * 显示加载后数据
     */
    void showContent();

    /**
     * 显示错误页面
     */
    void showError();

    /**
     * 显示重试页面
     */
    void showRetryView();

    /**
     * 重试事件
     */
    void setRetryEvent();
}
