package com.qmr.base.mvp;

public interface BaseView<P> {

    void bindPresenter(P presenter);

    void unbindPresenter();

    //初始化view，让view可以正常使用
    void initView();

    //初始化数据，只加载一次！
    void initData();

    void initSuccess();

    //初始化失败
    void initError();

    //加载
    void startLoading();

    void loadSuccess();

    void loadError();

    void shortToast(CharSequence message);
}
