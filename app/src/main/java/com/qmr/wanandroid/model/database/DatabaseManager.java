package com.qmr.wanandroid.model.database;

import android.content.Context;

import androidx.room.Room;

import com.qmr.wanandroid.model.database.dao.HistoryDao;
import com.qmr.wanandroid.model.database.dao.ReadLaterDao;

public class DatabaseManager {

    public static final String DB_NAME = "wanandroid.db";

    private static DatabaseManager databaseManager;

    private MainDatabase mainDatabase;

    private DatabaseManager(Context context) {
        mainDatabase = Room.
                databaseBuilder(context, MainDatabase.class, "history.db").build();
    }

    public static DatabaseManager getInstance() {
/*        if (databaseManager == null)
            databaseManager = new DatabaseManager();*/
        return databaseManager;
    }

    public static void init(Context context) {
        databaseManager = new DatabaseManager(context);
    }

    public MainDatabase historyDatabase() {
        return mainDatabase;
    }

    public HistoryDao getHistoryDao() {
        return mainDatabase.getHistoryDao();
    }

    public ReadLaterDao getReadLaterDao() {
        return mainDatabase.getReadlaterDao();
    }

/*    public CollectionDao getCollectionDao() {
        return mainDatabase.getCollectionDao();
    }*/


}
