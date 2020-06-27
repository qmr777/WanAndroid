package com.qmr.wanandroid.ui.home.mvp;

import com.qmr.base.mvp.BaseView;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;

import java.util.List;

public interface IHomeView extends BaseView<IHomePresenter> {

    void setBannerData(List<BannerBean> bean);

    void setTopData(List<TopArticleBean> bean);

    void setContentBean(List<ArticleBean> bean);

    void addContent(List<ArticleBean> beans);

    void refresh();

}
