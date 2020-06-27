package com.qmr.wanandroid.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.qmr.wanandroid.model.database.dao.CollectionDao;
import com.qmr.wanandroid.model.database.dao.HistoryDao;
import com.qmr.wanandroid.model.database.dao.ReadLaterDao;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.HistoryBean;
import com.qmr.wanandroid.model.entity.ReadLaterBean;

@Database(entities = {ArticleBean.class, HistoryBean.class, ReadLaterBean.class}, version = 1, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

    public abstract HistoryDao getHistoryDao();

    public abstract CollectionDao getCollectionDao();

    public abstract ReadLaterDao getReadlaterDao();

}
