package com.example.note_rmgs_android.Models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(foreignKeys = @ForeignKey(entity = Subject.class,parentColumns = "subject_id",childColumns = "subject_fk",onDelete = CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
    private String audio;
    private long created_date;
    private int subject_fk;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(long created_date) {
        this.created_date = created_date;
    }

    public int getSubject_fk() {
        return subject_fk;
    }

    public void setSubject_fk(int subject_fk) {
        this.subject_fk = subject_fk;
    }

    public Note(int note_id, String title, String description, double latitude, double longitude, byte[] image, String audio, long created_date, int subject_fk) {
        this.note_id = note_id;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.audio = audio;
        this.created_date = created_date;
        this.subject_fk = subject_fk;
    }
}
