package com.qmr.wanandroid.model.entity;

import com.qmr.base.model.BaseBean;

import java.util.List;

/**
 * 导航数据
 */
public class NaviBean extends BaseBean {

    private int cid;
    private String name;
    private List<ArticleBean> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }

}
