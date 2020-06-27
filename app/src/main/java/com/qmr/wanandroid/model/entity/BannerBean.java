package com.qmr.wanandroid.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.qmr.base.model.BaseBean;

public class BannerBean extends BaseBean implements Parcelable {

    /**
     * desc : 一起来做个App吧
     * id : 10
     * imagePath : https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
     * isVisible : 1
     * order : 1
     * title : 一起来做个App吧
     * type : 0
     * url : http://www.wanandroid.com/blog/show/2
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }


    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };

    public BannerBean() {
    }

    protected BannerBean(Parcel in) {
        this.desc = in.readString();
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.isVisible = in.readInt();
        this.order = in.readInt();
        this.title = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeInt(this.isVisible);
        dest.writeInt(this.order);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }
}
