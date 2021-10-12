package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_rmgs_android.Adapter.Category_note_Adapter;
import com.example.note_rmgs_android.Dao.NoteD;
import com.example.note_rmgs_android.Dao.SubjectD;
import com.example.note_rmgs_android.Database.Database;
import com.example.note_rmgs_android.Models.Note;
import com.example.note_rmgs_android.Models.Subject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    int id;
    List<Note> mlist;
    Category_note_Adapter categoryNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_notes);
        ButterKnife.bind(this);
        img_left.setVisibility(View.VISIBLE);
        img_right.setVisibility(View.VISIBLE);
        img_icon.setVisibility(View.VISIBLE);
        img_left.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        img_right.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));
        img_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_sort_by_alpha_24));

        Intent i=getIntent();
        name=i.getStringExtra("name");
        id=i.getIntExtra("SubjectID",-1);
        txt_heading.setText(""+name);
        NoteD dao = Database.getInstance(getApplicationContext()).noteDeo();
        mlist = dao.getCatNotes(id);
        if(mlist.size()==0){
            no_notes.setVisibility(View.VISIBLE);
            category_note_recycler.setVisibility(View.GONE);
        }else {
            no_notes.setVisibility(View.GONE);
            category_note_recycler.setVisibility(View.VISIBLE);
        }

        categoryNoteAdapter=new Category_note_Adapter(this,mlist,Database.getInstance(getApplicationContext()).subjectDeo().getAllSubject());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        category_note_recycler.setLayoutManager(mLayoutManager);
        category_note_recycler.setAdapter(categoryNoteAdapter);


    }
    @OnClick(R.id.img_right)
    public void AddNote(){
        Intent i=new Intent(getApplicationContext(),Add_Edit_Note.class);
        i.putExtra("from","new");
        i.putExtra("name",name);
        i.putExtra("SubjectID",id);
        startActivity(i);
    }


    @OnClick(R.id.img_left)
    public void BackClick(){
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}