package com.sisfo.practicumfinale.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAll();

    @Query("SELECT * FROM bookmark WHERE api_id = :id")
    Bookmark getByApiID(int id);

    @Insert
    void insert(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);
}
