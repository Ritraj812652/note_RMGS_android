package com.example.note_rmgs_android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_rmgs_android.Models.Note;

import java.util.List;

@Dao
public interface NoteD {
    @Query("SELECT * FROM Note ")
    List<Note> getAllNotes();
    @Insert
    void insertNote(Note note);
    @Update
    void updateNote(Note note);
    @Delete
    void deleteNote(Note note);
}
