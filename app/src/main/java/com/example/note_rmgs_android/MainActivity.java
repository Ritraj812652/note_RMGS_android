package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_rmgs_android.Database.GroupDao;
import com.example.note_rmgs_android.Database.Database;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    RecyclerView category_recycler;

    RelativeLayout no_categories;

    TextView txt_heading;

    ImageView img_left;

    ImageView img_icon;

    ImageView img_right;

    List<Group> listViews;
    Boolean title_sort = false;
    CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_recycler = findViewById(R.id.category_recycler);
        no_categories = findViewById(R.id.no_categories);
        txt_heading = findViewById(R.id.txt_heading);
        img_left = findViewById(R.id.img_left);
        img_icon = findViewById(R.id.img_icon);
        img_right = findViewById(R.id.img_right);

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubject();
            }
        });

        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort();
            }
        });

        ButterKnife.bind(this);
        int id = getIntent().getIntExtra("noteID",-1);
        if (id != -1) {
            txt_heading.setText("Move Note");
            img_left.setVisibility(View.GONE);
            img_right.setVisibility(View.GONE);
        }
        else{
            txt_heading.setText("Categories");
            img_left.setVisibility(View.VISIBLE);
            img_right.setVisibility(View.VISIBLE);
            img_left.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_sort_by_alpha_24));
            img_right.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));
        }


        img_icon.setVisibility(View.GONE);



        GroupDao dao = Database.getInstance(getApplicationContext()).groupDeo();
        listViews = dao.getAllSubject();
        if(listViews.size()==0){
            no_categories.setVisibility(View.VISIBLE);
            category_recycler.setVisibility(View.GONE);
        }else {
            no_categories.setVisibility(View.GONE);
            category_recycler.setVisibility(View.VISIBLE);
        }
        categoryAdapter= new CategoryAdapter(this, listViews) {


            @Override
            public void deleteTheItem(int pos) {
                final AlertDialog.Builder mainDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater)getApplicationContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                mainDialog.setView(dialogView);

                final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                final Button save = (Button) dialogView.findViewById(R.id.save);
                final ImageView cross=(ImageView) dialogView.findViewById(R.id.cross);
                final TextView cat_name=(TextView)dialogView.findViewById(R.id.cat_name);
                final AlertDialog alertDialog = mainDialog.create();
                alertDialog.show();
                cat_name.setText("You want to delete this Category?");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete item
                        GroupDao dao = Database.getInstance(getApplicationContext()).groupDeo();
                       dao.deleteSubject( list.get(pos));
                        list.remove(pos);
                        categoryAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void selectTheItem(int pos) {
                int id = getIntent().getIntExtra("noteID",-1);
                if (id != -1) {
                    Note n = Database.getInstance(getApplicationContext()).noteDeo().getSpecficNote(id);
                    n.setGroup_fk(list.get(pos).getGroup_id());
                    Database.getInstance(getApplicationContext()).noteDeo().updateNote(n);
                    Toast.makeText(getApplicationContext(),"Note moved successfully.",Toast.LENGTH_SHORT).show();
                }
                    Intent i=new Intent(context, Category_Notes.class);
                    i.putExtra("name",list.get(pos).getName());
                    i.putExtra("ID",pos);
                    i.putExtra("SubjectID",list.get(pos).getGroup_id());
                    startActivity(i);


            }
        };
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        category_recycler.setLayoutManager(mLayoutManager);
        category_recycler.setAdapter(categoryAdapter);


    }

//    @OnClick(R.id.img_left)
    public void sort(){

        if(title_sort){
            Collections.sort(listViews, (a, b) -> b.getName().compareToIgnoreCase(a.getName()));
            Toast.makeText(getApplicationContext(),"Category sort in descending order!",Toast.LENGTH_SHORT).show();
        }
        else{
            Collections.sort(listViews, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            Toast.makeText(getApplicationContext(),"Category sort in ascending order!",Toast.LENGTH_SHORT).show();
        }
        title_sort = (title_sort == false)? true : false;
        categoryAdapter.notifyDataSetChanged();
    }

//    @OnClick(R.id.img_right)
    public void AddSubject() {
        final AlertDialog.Builder mainDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.subject_dialog, null);
        mainDialog.setView(dialogView);

        final Button save = (Button) dialogView.findViewById(R.id.save);
        final ImageView cross=(ImageView) dialogView.findViewById(R.id.cross);
        final EditText sub = (EditText) dialogView.findViewById(R.id.sub_txt);
        final AlertDialog alertDialog = mainDialog.create();
        alertDialog.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save item
                Group group = new Group();
                group.setName(sub.getText().toString());
                GroupDao dao = Database.getInstance(getApplicationContext()).groupDeo();
                dao.insertSubject(group);
                Toast.makeText(getApplicationContext(),"Data Added",Toast.LENGTH_LONG).show();
                categoryAdapter.list.clear();
                categoryAdapter.list.addAll(dao.getAllSubject());
                no_categories.setVisibility(View.GONE);
                category_recycler.setVisibility(View.VISIBLE);
                categoryAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}