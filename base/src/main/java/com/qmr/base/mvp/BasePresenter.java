package com.qmr.base.mvp;

public interface BasePresenter<V> {

    void bindView(V view);

    void unBind();

    void initData();

    //初始化失败
    void initError();

    //加载
    void startLoading();

    void endLoading();

    void loadError();

}
