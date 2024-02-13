package com.example.mornhouse_test.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract DataDao userDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "DB_Name")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }

}
