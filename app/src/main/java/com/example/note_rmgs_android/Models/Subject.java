package com.example.note_rmgs_android.Models;
import androidx.room.*;
@Entity
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int subject_id;
    private String name;

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
