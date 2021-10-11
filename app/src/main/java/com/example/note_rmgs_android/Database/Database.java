package com.example.note_rmgs_android.Database;

import android.content.Context;

import androidx.room.Room;

public class Database {

    static Db db;

    public static Db getInstance(Context context) {

        if (db != null) {
            return db;
        } else {
            return db = Room.databaseBuilder(context,
                    Db.class,
                    "NotesDatabase")
                    .allowMainThreadQueries().build();
        }


    }
}
