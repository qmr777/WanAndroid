package com.qmr.wanandroid.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qmr.wanandroid.model.entity.ReadLaterBean;

import java.util.List;

@Dao
public interface ReadLaterDao {

    @Query("SELECT * FROM ReadLaterBean ORDER BY time")
    List<ReadLaterBean> getHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReadLaterBean... users);

    @Update
    void update(ReadLaterBean... users);

    @Delete
    void delete(ReadLaterBean... users);

    @Query("delete from ReadLaterBean")
    int deleteAll();

}
