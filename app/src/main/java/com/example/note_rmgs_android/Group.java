package com.example.note_rmgs_android;
import androidx.room.*;
@Entity
public class Group {
    @PrimaryKey(autoGenerate = true)
    private int group_id;
    private String name;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
