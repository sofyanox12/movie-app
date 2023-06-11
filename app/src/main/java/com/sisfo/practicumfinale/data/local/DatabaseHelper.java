package com.sisfo.practicumfinale.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {Bookmark.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends androidx.room.RoomDatabase {
    private static final String DB_NAME = "bookmark_db";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract RoomDao roomDao();



}
