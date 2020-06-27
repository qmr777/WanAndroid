package com.qmr.wanandroid.model.entity;

import com.qmr.base.model.BaseBean;

public class TagsBean extends BaseBean {

    /**
     * name : 公众号
     * url : /wxarticle/list/408/1
     */

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
