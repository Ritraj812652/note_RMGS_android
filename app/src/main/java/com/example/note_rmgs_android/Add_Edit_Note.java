package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Add_Edit_Note extends AppCompatActivity {

    @BindView(R.id.txt_heading)
    TextView txt_heading;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_icon)
    ImageView img_icon;
    @BindView(R.id.img_right)
    ImageView img_right;
    @BindView(R.id.new_title)
    EditText new_title;
    @BindView(R.id.note_description)
    EditText note_description;
    @BindView(R.id.image_picker)
    ImageView image_picker;
    @BindView(R.id.voice_picker)
    ImageView voice_picker;
    @BindView(R.id.view_layout)
    LinearLayout view_layout;
    @BindView(R.id._pic)
    ImageView _pic;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.select_subject)
    TextView select_subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        ButterKnife.bind(this);
    }
}