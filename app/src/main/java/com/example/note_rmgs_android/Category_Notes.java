package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.note_rmgs_android.Database.NoteD;
import com.example.note_rmgs_android.Database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Category_Notes extends AppCompatActivity {

    TextView txt_heading,cancel;
    ImageView img_left,img_icon,img_right;
    EditText search_txt;
    RecyclerView category_note_recycler;
    RelativeLayout no_notes;

    String name="";
    int id;
    Boolean title_sort = false;
    Boolean date_sort = false;
    List<Note> mlist;
    Category_note_Adapter categoryNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_notes);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        img_icon = findViewById(R.id.img_icon);
        txt_heading = findViewById(R.id.txt_heading);
        search_txt = findViewById(R.id.search_txt);
        cancel = findViewById(R.id.cancel);
        category_note_recycler = findViewById(R.id.category_note_recycler);
        no_notes = findViewById(R.id.no_notes);

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

        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort();
            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNote();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackClick();
            }
        });

        if(mlist.size()==0){
            no_notes.setVisibility(View.VISIBLE);
            category_note_recycler.setVisibility(View.GONE);
        }else {
            no_notes.setVisibility(View.GONE);
            category_note_recycler.setVisibility(View.VISIBLE);
        }



        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_note(s.toString());

            }
        });
        categoryNoteAdapter=new Category_note_Adapter(this,mlist,Database.getInstance(getApplicationContext()).groupDeo().getAllSubject());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        category_note_recycler.setLayoutManager(mLayoutManager);
        category_note_recycler.setAdapter(categoryNoteAdapter);

    }
    // sort

//    @OnClick(R.id.img_icon)
    public void sort(){
        PopupMenu popup = new PopupMenu(Category_Notes.this, img_icon);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.sorting_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getTitle().equals("Title")){
                    if(title_sort){
                        Collections.sort(mlist, (a, b) -> b.getTitle().compareToIgnoreCase(a.getTitle()));
                    }
                    else{
                        Collections.sort(mlist, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
                    }
                    title_sort = (title_sort == false)? true : false;
                }else {
                    if(date_sort) {
                        Collections.sort(mlist, (a, b) -> {
                            if (a.getCreated_date() > b.getCreated_date()) return 1;
                            else if (a.getCreated_date() < b.getCreated_date()) return -1;
                            else return 0;
                        });
                    }
                    else{
                        Collections.sort(mlist, (a, b) -> {
                            if (a.getCreated_date() < b.getCreated_date()) return 1;
                            else if (a.getCreated_date() > b.getCreated_date()) return -1;
                            else return 0;
                        });
                    }
                    date_sort = (date_sort == false)? true : false;
                }

                categoryNoteAdapter.notifyDataSetChanged();
                return true;
            }
        });
        popup.show();
    }
//    @OnClick(R.id.img_right)
    public void AddNote(){
        Intent i=new Intent(getApplicationContext(),Add_Edit_Note.class);
        i.putExtra("from","new");
        i.putExtra("name",name);
        i.putExtra("SubjectID",id);
        startActivity(i);
    }
//    @OnClick(R.id.cancel)
    public void cancel(){
        mlist =  Database.getInstance(this).noteDeo().getCatNotes(id);
        search_txt.setText("");
        categoryNoteAdapter.updateList(mlist);
        search_txt.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

//    @OnClick(R.id.img_left)
    public void BackClick(){
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
    private void search_note(String text) {
        mlist =  Database.getInstance(this).noteDeo().getCatNotes(id);
        List<Note> temp = new ArrayList();
        for (Note n :mlist) {
            if(n.getTitle().toLowerCase().contains(text.toLowerCase()) || n.getDescription().toLowerCase().contains(text.toLowerCase())){
                temp.add(n);
            }
        }
       categoryNoteAdapter.updateList(temp);

    }
}