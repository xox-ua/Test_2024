package com.example.mornhouse_test.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "method")
    public String method;

    @ColumnInfo(name = "number")
    public String number;

    @ColumnInfo(name = "description")
    public String description;

}
