package com.qmr.wanandroid.ui.home.mvp;

import com.qmr.base.mvp.BasePresenter;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;

import java.util.List;

public interface IHomePresenter extends BasePresenter<IHomeView> {

    void loadMoreSuccess(List<ArticleBean> bannerBeans);

    void setBanner(List<BannerBean> beans);

    void setTop(List<TopArticleBean> beans);

    void setContent(List<ArticleBean> beans);

    void refresh();

}
