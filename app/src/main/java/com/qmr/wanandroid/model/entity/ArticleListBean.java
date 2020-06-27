package com.qmr.wanandroid.model.entity;


import com.qmr.base.model.BaseBean;

import java.util.List;

public class ArticleListBean extends BaseBean {
    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"",
     * "envelopePic":"","fresh":false,"id":8367,"link":"https://mp.weixin.qq.com/s/uI7Fej1_qSJOJnzQ6offpw","niceDate":"2019-05-06","origin":"",
     * "prefix":"","projectLink":"","publishTime":1557072000000,"superChapterId":408,"superChapterName":"公众号",
     * "tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"深扒 EventBus：register","type":0,"userId":-1,"visible":1,"zan":0},
     * {"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":8371,
     * offset : 20
     * <p>
     * over : false
     * pageCount : 323
     * size : 20
     * total : 6456
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getArticles() {
        return datas;
    }

/*
    public List<MultiItemEntity> getDatas() {
        List<MultiItemEntity> list = new ArrayList<>(datas.size());
        list.addAll(datas);
        return list;
    }
*/

    public void setDatas(List<ArticleBean> datas) {
        this.datas = datas;
    }
}
