package com.qmr.wanandroid.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qmr.wanandroid.model.entity.HistoryBean;

import java.util.List;


@Dao
public interface HistoryDao {

    @Query("SELECT * FROM historybean ORDER BY time DESC")
    List<HistoryBean> getHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryBean... users);

    @Update
    void update(HistoryBean... users);

    @Delete
    void delete(HistoryBean... users);

    @Query("delete from historybean")
    int deleteAll();

}
