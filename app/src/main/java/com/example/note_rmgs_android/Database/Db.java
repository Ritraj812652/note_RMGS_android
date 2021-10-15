package com.example.note_rmgs_android.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.note_rmgs_android.Note;
import com.example.note_rmgs_android.Group;

@Database(entities = { Note.class, Group.class }, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract NoteD noteDeo();
    public abstract GroupDao groupDeo();
}
