package com.example.note_rmgs_android.Models;

public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;
    private String description;
    private String title;
    private double latitude;
    private double longitude;
    private long created;
    private int subject_id_fk;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] note_image;
    private String note_audio;
}
