package com.qmr.wanandroid.ui.home;

import android.util.Log;

import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.ui.home.mvp.IHomePresenter;
import com.qmr.wanandroid.ui.home.mvp.IHomeView;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomePresenter implements IHomePresenter {

    private static final String TAG = "HomePresenter";

    private IHomeView view;
    private CompositeDisposable cd;
    private HomeModel model;

    @Override
    public void bindView(IHomeView view) {
        this.view = view;
        this.model = new HomeModel(this);
        view.bindPresenter(this);
        cd = new CompositeDisposable();
    }

    @Override
    public void unBind() {
        cd.dispose();
        this.view = null;
    }

    @Override
    public void initData() {
        model.initBanner();
        model.initArticle();
        model.initTop();
    }

    @Override
    public void initError() {
        view.loadError();
        view.shortToast("网络错误");
    }

    @Override // 加载更多
    public void startLoading() {
        Log.i(TAG, "startLoadingMore");
        model.loadMore();
    }

    @Override//成功
    public void endLoading() {
        view.loadSuccess();
    }

    @Override//失败
    public void loadError() {
        view.loadError();
    }

    @Override
    public void loadMoreSuccess(List<ArticleBean> bannerBeans) {
        view.addContent(bannerBeans);
        view.loadSuccess();
    }

    @Override
    public void setBanner(List<BannerBean> beans) {
        view.setBannerData(beans);
    }

    @Override
    public void setTop(List<TopArticleBean> beans) {
        view.setTopData(beans);
    }

    @Override
    public void setContent(List<ArticleBean> beans) {
        view.setContentBean(beans);
    }

    @Override
    public void refresh() {
        view.refresh();
        initData();
    }
}
