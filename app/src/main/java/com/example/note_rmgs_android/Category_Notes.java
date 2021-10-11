package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.note_rmgs_android.Dao.NoteD;
import com.example.note_rmgs_android.Dao.SubjectD;
import com.example.note_rmgs_android.Database.Database;
import com.example.note_rmgs_android.Models.Note;
import com.example.note_rmgs_android.Models.Subject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Category_Notes extends AppCompatActivity {
    @BindView(R.id.txt_heading)
    TextView txt_heading;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_icon)
    ImageView img_icon;
    @BindView(R.id.img_right)
    ImageView img_right;
    @BindView(R.id.search_txt)
    EditText search_txt;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.category_note_recycler)
    RecyclerView category_note_recycler;
    @BindView(R.id.no_notes)
    RelativeLayout no_notes;
    String name="";
    List<Note> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_notes);
        ButterKnife.bind(this);
        img_left.setVisibility(View.VISIBLE);
        img_right.setVisibility(View.VISIBLE);
        img_icon.setVisibility(View.VISIBLE);
        Intent i=getIntent();
        name=i.getStringExtra("name");
        txt_heading.setText(""+name);
        NoteD dao = Database.getInstance(getApplicationContext()).noteDeo();
        mlist = dao.getAllNotes();
        if(mlist.size()==0){
            no_notes.setVisibility(View.VISIBLE);
            category_note_recycler.setVisibility(View.GONE);
        }else {
            no_notes.setVisibility(View.GONE);
            category_note_recycler.setVisibility(View.VISIBLE);
        }


    }
}