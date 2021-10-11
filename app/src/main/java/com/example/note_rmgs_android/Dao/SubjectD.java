package com.example.note_rmgs_android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_rmgs_android.Models.Subject;

import java.util.List;

@Dao
public interface SubjectD {
    @Query("SELECT * FROM Subject")
    List<Subject> getAllSubject();
    @Insert
    void insertSubject(Subject subject);
    @Update
    void updateSubject(Subject subject);
    @Delete
    void deleteSubject(Subject subject);
}
