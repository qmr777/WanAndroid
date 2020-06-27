package com.qmr.wanandroid.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.qmr.base.model.BaseBean;

import java.io.Serializable;

public class TagsBean extends BaseBean implements Parcelable, Serializable {

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


    public static final Parcelable.Creator<TagsBean> CREATOR = new Parcelable.Creator<TagsBean>() {
        @Override
        public TagsBean createFromParcel(Parcel source) {
            return new TagsBean(source);
        }

        @Override
        public TagsBean[] newArray(int size) {
            return new TagsBean[size];
        }
    };

    public TagsBean() {
    }

    protected TagsBean(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
    }
}
