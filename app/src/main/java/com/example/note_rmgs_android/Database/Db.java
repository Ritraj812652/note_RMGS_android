package com.example.note_rmgs_android.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_rmgs_android.Dao.*;
import com.example.note_rmgs_android.Models.*;

@Database(entities = { Note.class, Subject.class }, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract NoteD noteDeo();
    public abstract SubjectD subjectDeo();
}
