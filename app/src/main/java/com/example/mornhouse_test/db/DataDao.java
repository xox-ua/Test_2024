package com.example.mornhouse_test.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM Data")
    List<Data> getAllData();

    @Query("DELETE FROM Data WHERE id = :id")
    void deleteById(long id);

    @Insert
    void insertData(Data... data);

    @Delete
    void delete(Data data);

}
