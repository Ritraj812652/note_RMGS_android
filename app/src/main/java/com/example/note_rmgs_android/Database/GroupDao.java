package com.example.note_rmgs_android.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_rmgs_android.Group;import com.example.note_rmgs_android.Group;

import java.util.List;

@Dao
public interface GroupDao {
    @Query("SELECT * FROM `Group`")
    List<Group> getAllSubject();



    @Insert
    void insertSubject(Group subject);


    @Delete
    void deleteSubject(Group subject);

    @Query("SELECT * FROM `Group` WHERE group_id = :id")
    List<Group> getSubject(int id);
}
