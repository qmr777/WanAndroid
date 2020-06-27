package com.qmr.wanandroid.model.entity;

import com.qmr.base.model.BaseBean;

import java.util.List;

public class CollectionBean extends BaseBean {


    /**
     * curPage : 1
     * datas : [{"author":"小编","chapterId":352,"chapterName":"资讯","courseId":13,"desc":"","envelopePic":"","id":139262,"link":"http://www.wanandroid.com/blog/show/2","niceDate":"刚刚","origin":"","originId":2864,"publishTime":1592387890000,"title":"玩Android API","userId":12677,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 1
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CollectionItemBean> datas;

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

    public List<CollectionItemBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectionItemBean> datas) {
        this.datas = datas;
    }

}
