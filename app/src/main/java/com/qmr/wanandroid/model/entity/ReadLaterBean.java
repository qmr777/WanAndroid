package com.qmr.wanandroid.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.qmr.base.model.BaseBean;

@Entity
public class ReadLaterBean extends BaseBean {

    @PrimaryKey(autoGenerate = false)
    private int _id;
    private String title;
    private String link;//YYYY-MM-DD HH:mm:ss
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
