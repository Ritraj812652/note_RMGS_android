package com.example.note_rmgs_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_rmgs_android.Database.Database;
import com.example.note_rmgs_android.Models.Note;
import com.example.note_rmgs_android.Models.Subject;
import com.example.note_rmgs_android.Utils.DataConverter;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    String from="",name="",pathAudio;
    Location userlocation;
    LocationManager locationManager;
    LocationListener listener;
    Bitmap image;
    int id;
    Subject subjectdata;
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        ButterKnife.bind(this);
        Intent i=getIntent();
        from=i.getStringExtra("from");
        name=i.getStringExtra("name");
        id=i.getIntExtra("SubjectID",-1);

        img_left.setVisibility(View.VISIBLE);
        img_icon.setVisibility(View.GONE);
        img_left.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        txt_heading.setText(""+name);

        if(from.equalsIgnoreCase("new")){
            img_right.setVisibility(View.GONE);

            save.setText("Save");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    userlocation = location;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (!hasLocationPermission())
                requestLocationPermission();
            else
                UpdateLocation();

        }else {
            img_right.setVisibility(View.VISIBLE);
            img_right.setImageDrawable(getResources().getDrawable(R.drawable.delete_white));
            save.setText("Update");
            UpdateNotes();


        }
    }

    @OnClick(R.id.save)
    public void Save_editNote(){

        if(from.equalsIgnoreCase("new")) {
            if (CheckFields()) {
                Note note;
                if (image != null) {

                    note = new Note(new_title.getText().toString(),note_description.getText().toString(), userlocation.getLatitude(), userlocation.getLongitude(),DataConverter.convertImage2ByteArray(image), pathAudio, new Date().getTime(), id);
                } else {
                    note = new Note( new_title.getText().toString(),note_description.getText().toString(), userlocation.getLatitude(), userlocation.getLongitude(), null, pathAudio,new Date().getTime(),id );
                }
                Database.getInstance(this).noteDeo().insertNote(note);

            }
        }else {
            if(CheckFields()) {
                List<Note> notes = Database.getInstance(this).noteDeo().getAllNotes();
                int id = getIntent().getIntExtra("ID",-1);
                if (id != -1){
                    Note note = notes.get(id);
                    note.setTitle(new_title.getText().toString());
                    note.setDescription(note_description.getText().toString());
                    note.setAudio(pathAudio);
                    note.setSubject_fk(subjectdata.getSubject_id());
                    if(image != null){
                        note.setImage(DataConverter.convertImage2ByteArray(image));
                    }
                    Database.getInstance(this).noteDeo().updateNote(note);
                }

            }
        }
    }

    private void UpdateNotes() {
        List<Note> notes = Database.getInstance(this).noteDeo().getAllNotes();
        int id = getIntent().getIntExtra("ID",-1);
        if (id != -1){
            Note note = notes.get(id);
            new_title.setText(note.getTitle());
            note_description.setText(note.getDescription());
            byte[] data = note.getImage();
            if(data != null){
                image = DataConverter.convertByteArray2Bitmap(data);
                _pic.setImageBitmap(image);
                _pic.setVisibility(View.VISIBLE);
            }
            Subject sub = Database.getInstance(this).subjectDeo().getSubject(note.getSubject_fk()).get(0);
             subjectdata=sub;

        }
    }

    private void UpdateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (REQUEST_CODE == requestCode) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);
            }
        }
    }

    public boolean CheckFields(){

        if (new_title.getText().toString().trim().length() == 0) {
            new_title.setError("Please enter title");
            new_title.requestFocus();
            return false;

        }else if(note_description.getText().toString().trim().length() == 0) {

            note_description.setError("Please enter description");
            note_description.requestFocus();
            return false;
        }
        return true;


    }
}