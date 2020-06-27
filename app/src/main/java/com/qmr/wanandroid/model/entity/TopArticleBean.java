package com.qmr.wanandroid.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.qmr.base.model.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopArticleBean extends BaseBean implements Parcelable, Serializable {

    /**
     * apkLink :
     * audit : 1
     * author : xiaoyang
     * canEdit : false
     * chapterId : 440
     * chapterName : 官方
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 13775
     * link : https://wanandroid.com/wenda/show/13775
     * niceDate : 2020-06-09 23:16
     * niceShareDate : 2020-06-05 14:26
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1591715815000
     * selfVisible : 0
     * shareDate : 1591338370000
     * shareUser :
     * superChapterId : 440
     * superChapterName : 问答
     * tags : [{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}]
     * title : 每日一问 | Activity与Fragment的那些事，&ldquo;用起来没问题，我都要走了，你崩溃了？&rdquo;
     * type : 1
     * userId : 2
     * visible : 1
     * zan : 13
     */

    private String apkLink;
    private int audit;
    private String author;
    private boolean canEdit;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String descMd;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int selfVisible;
    private long shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private List<TagsBean> tags;

    public String getDate() {
        String d = String.valueOf(getPublishTime() / 100000);
        return d.substring(0, 3) +
                "年" +
                d.substring(4, 5) +
                "月" +
                d.substring(6, 7);
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescMd() {
        return descMd;
    }

    public void setDescMd(String descMd) {
        this.descMd = descMd;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }

    public long getShareDate() {
        return shareDate;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static final Parcelable.Creator<TopArticleBean> CREATOR = new Parcelable.Creator<TopArticleBean>() {
        @Override
        public TopArticleBean createFromParcel(Parcel source) {
            return new TopArticleBean(source);
        }

        @Override
        public TopArticleBean[] newArray(int size) {
            return new TopArticleBean[size];
        }
    };

    /*

    public static class TagsBean {
        */
    public TopArticleBean() {
    }

    protected TopArticleBean(Parcel in) {
        this.apkLink = in.readString();
        this.audit = in.readInt();
        this.author = in.readString();
        this.canEdit = in.readByte() != 0;
        this.chapterId = in.readInt();
        this.chapterName = in.readString();
        this.collect = in.readByte() != 0;
        this.courseId = in.readInt();
        this.desc = in.readString();
        this.descMd = in.readString();
        this.envelopePic = in.readString();
        this.fresh = in.readByte() != 0;
        this.id = in.readInt();
        this.link = in.readString();
        this.niceDate = in.readString();
        this.niceShareDate = in.readString();
        this.origin = in.readString();
        this.prefix = in.readString();
        this.projectLink = in.readString();
        this.publishTime = in.readLong();
        this.selfVisible = in.readInt();
        this.shareDate = in.readLong();
        this.shareUser = in.readString();
        this.superChapterId = in.readInt();
        this.superChapterName = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.userId = in.readInt();
        this.visible = in.readInt();
        this.zan = in.readInt();
        this.tags = new ArrayList<TagsBean>();
        in.readList(this.tags, TagsBean.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "TopArticleBean{" +
                "author='" + author + '\'' +
                ", collect=" + collect +
                ", desc='" + desc + '\'' +
                ", fresh=" + fresh +
                ", title='" + title + '\'' +
                '}';
    }

    /**
     * name : 本站发布
     * url : /article/list/0?cid=440
     *//*


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
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apkLink);
        dest.writeInt(this.audit);
        dest.writeString(this.author);
        dest.writeByte(this.canEdit ? (byte) 1 : (byte) 0);
        dest.writeInt(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeByte(this.collect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.courseId);
        dest.writeString(this.desc);
        dest.writeString(this.descMd);
        dest.writeString(this.envelopePic);
        dest.writeByte(this.fresh ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.link);
        dest.writeString(this.niceDate);
        dest.writeString(this.niceShareDate);
        dest.writeString(this.origin);
        dest.writeString(this.prefix);
        dest.writeString(this.projectLink);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.selfVisible);
        dest.writeLong(this.shareDate);
        dest.writeString(this.shareUser);
        dest.writeInt(this.superChapterId);
        dest.writeString(this.superChapterName);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.userId);
        dest.writeInt(this.visible);
        dest.writeInt(this.zan);
        dest.writeList(this.tags);
    }
}
