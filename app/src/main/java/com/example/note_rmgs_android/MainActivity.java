package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.no_notes)
    RelativeLayout no_notes;
    @BindView(R.id.txt_heading)
    TextView txt_heading;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_icon)
    ImageView img_icon;
    @BindView(R.id.img_right)
    ImageView img_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }
    @OnClick(R.id.img_right)
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