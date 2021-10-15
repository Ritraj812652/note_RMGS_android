package com.example.note_rmgs_android.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_rmgs_android.Note;

import java.util.List;

@Dao
public interface NoteD {
    @Query("SELECT * FROM Note WHERE note_id = :id")
    Note getSpecficNote(int id);


    @Query("SELECT * FROM Note WHERE group_fk = :id")
    List<Note> getCatNotes(int id);


    @Query("SELECT * FROM Note ")
    List<Note> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);


    @Delete
    void deleteNote(Note note);
}
