package com.example.note_rmgs_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_rmgs_android.Database.Database;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Add_Edit_Note extends AppCompatActivity {


    TextView txt_heading,select_subject;
    EditText new_title,note_description;
    ImageView image_picker,voice_picker,img_left;
    LinearLayout view_layout;
    ImageView img_icon,img_right,_pic;
    Button save;

    // Initiating properties

    String from="",name="",pathAudio;
    Location userlocation;
    LocationManager locationManager;
    LocationListener listener;
    Category_note_Adapter categoryNoteAdapter;
    Bitmap image;
    int id;
    Group subjectdata;
    MediaRecorder rec = new MediaRecorder();
    MediaPlayer mp = new MediaPlayer();


    // static variables defined
    private static final int REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST = 102;
    private static final int GALLERY_REQUEST = 101;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        txt_heading = findViewById(R.id.txt_heading);
        new_title = findViewById(R.id.new_title);
        note_description = findViewById(R.id.note_description);
        image_picker = findViewById(R.id.image_picker);
        voice_picker = findViewById(R.id.voice_picker);
        view_layout = findViewById(R.id.view_layout);
        img_left = findViewById(R.id.img_left);
        img_icon = findViewById(R.id.img_icon);
        img_right = findViewById(R.id.img_right);
        _pic = findViewById(R.id._pic);
        save = findViewById(R.id.save);
        select_subject = findViewById(R.id.select_subject);



        //Intent to get the values

        Intent i=getIntent();
        from=i.getStringExtra("from");
        name=i.getStringExtra("name");
        id=i.getIntExtra("SubjectID",-1);


        // Setting icons visibility of screen menu

        img_left.setVisibility(View.VISIBLE);
        img_icon.setVisibility(View.GONE);
        img_left.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        txt_heading.setText(""+name);
        voice_picker.setVisibility(View.GONE);


        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Note> notes = Database.getInstance(getApplicationContext()).noteDeo().getAllNotes();
                int id = getIntent().getIntExtra("noteID",-1);
                if (id != -1) {
                    Database.getInstance(getApplicationContext()).noteDeo().deleteNote(Database.getInstance(getApplicationContext()).noteDeo().getSpecficNote(id));
                    categoryNoteAdapter.notifyDataSetChanged();
                    finish();
                }
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_editNote();
            }
        });

        image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermissionImage())
                    permissionAskedForImageFnc();
                getIamge();
            }
        });

        select_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                int id = getIntent().getIntExtra("noteID", -1);
                if (id != -1) {
                    i.putExtra("noteID", id);
                    startActivity(i);
                }
            }
        });

        voice_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AudioActivity.class);
                i.putExtra("from","update");
                i.putExtra("path",pathAudio);
                startActivityForResult(i,112);
            }
        });
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

            if (!isPermission())
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , REQUEST_CODE);
            else
                fetchLocationUpdtFnc();

        }else {
            img_right.setVisibility(View.VISIBLE);
            img_right.setImageDrawable(getResources().getDrawable(R.drawable.delete_white));
            save.setText("Update");

            int id = getIntent().getIntExtra("noteID",-1);
            if (id != -1){
                Note note = Database.getInstance(this).noteDeo().getSpecficNote(id);
                new_title.setText(note.getTitle());
                note_description.setText(note.getDescription());
                byte[] data = note.getImage();
                pathAudio=note.getAudio();
                if(data != null){
                    image = DataConverter.convertByteArray2Bitmap(data);
                    _pic.setImageBitmap(image);
                    _pic.setVisibility(View.VISIBLE);
                }
                if(pathAudio!=null){
                    voice_picker.setVisibility(View.VISIBLE);
                }
                Group sub = Database.getInstance(this).groupDeo().getSubject(note.getGroup_fk()).get(0);
                subjectdata=sub;

            }


        }
    }



    public void Save_editNote(){
        boolean temp = false;

        if (new_title.getText().toString().trim().length() == 0) {

            new_title.setError("Please enter title");
            new_title.requestFocus();
            temp = false;

        }else if(note_description.getText().toString().trim().length() == 0) {

            note_description.setError("Please enter description");
            note_description.requestFocus();
            temp = false;
        }
        temp = true;

        if(from.equalsIgnoreCase("new")) {
            if (temp) {
                Note note;
                if (image != null) {
                    if (userlocation == null) {
                        note = new Note(new_title.getText().toString(), note_description.getText().toString(), 0, 0, DataConverter.convertImage2ByteArray(image), pathAudio, new Date().getTime(), id);

                    } else {
                        note = new Note(new_title.getText().toString(), note_description.getText().toString(), userlocation.getLatitude(), userlocation.getLongitude(), DataConverter.convertImage2ByteArray(image), pathAudio, new Date().getTime(), id);
                    }
                }

                else {
                    if (userlocation == null) {
                        note = new Note(new_title.getText().toString(), note_description.getText().toString(), 0, 0, null, pathAudio, new Date().getTime(), id);

                    } else {
                        note = new Note(new_title.getText().toString(), note_description.getText().toString(), userlocation.getLatitude(), userlocation.getLongitude(), null, pathAudio, new Date().getTime(), id);
                    }
                }
                Database.getInstance(this).noteDeo().insertNote(note);
                Intent i=new Intent(getApplicationContext(),Category_Notes.class);
                i.putExtra("from",from);
                i.putExtra("name",name);
                i.putExtra("SubjectID",id);
                startActivity(i);

            }
        }else {
            if(temp) {
                int id = getIntent().getIntExtra("noteID",-1);
                if (id != -1) {
                    Note note = Database.getInstance(this).noteDeo().getSpecficNote(id);
                    note.setTitle(new_title.getText().toString());
                    note.setDescription(note_description.getText().toString());
                    note.setAudio(pathAudio);
                    note.setGroup_fk(subjectdata.getGroup_id());
                    if(image != null){
                        note.setImage(DataConverter.convertImage2ByteArray(image));
                    }
                    Database.getInstance(this).noteDeo().updateNote(note);
                    Intent i=new Intent(getApplicationContext(),Category_Notes.class);
                    i.putExtra("from",from);
                    i.putExtra("name",name);
                    i.putExtra("SubjectID",note.getGroup_fk());
                    startActivity(i);
                }

            }
        }
    }



    private void fetchLocationUpdtFnc() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 0,
                listener);
    }



    private boolean isPermission() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private boolean isPermissionImage() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionAskedForImageFnc() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}
                , CAMERA_REQUEST);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (REQUEST_CODE == requestCode) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        5000,
                        0,
                        listener);
            }
        }

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {

            image = (Bitmap) data.getExtras().get("data");
         _pic.setImageBitmap(image);
           _pic.setVisibility(View.VISIBLE);
        }
        else if(reqCode == 112 && resultCode == RESULT_OK){
            pathAudio = data.getStringExtra("audio");
            voice_picker.setVisibility(View.VISIBLE);
        }
        else if(reqCode == GALLERY_REQUEST && resultCode == RESULT_OK){




            Uri uri = data.getData();
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
               _pic.setImageBitmap(image);
               _pic.setVisibility(View.VISIBLE);
            }
            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }

        }
        else if (reqCode == 11){
            if(resultCode == RESULT_OK) {

                int subjectID = data.getIntExtra("SubjectID",-1);
                id=subjectID;
                if(subjectID != -1){

                    for (Group sub: Database.getInstance(this).groupDeo().getAllSubject()) {
                        if(sub.getGroup_id() == subjectID){
                            subjectdata = sub;
                        }
                    }

                }
            }
        }
        else{

        }
    }


    public void getIamge()
    {

        final CharSequence[] items = { "Take Photo", "Choose from Library","Record Audio","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Your Option");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //  boolean result= Utility.checkPermission(ShareDeal.this);

                if (items[item].equals("Take Photo")) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (items[item].equals("Choose from Library")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                } else if(items[item].equals("Record Audio")){
                    Intent i=new Intent(getApplicationContext(),AudioActivity.class);
                    i.putExtra("from","new");
                    i.putExtra("path","");
                    startActivityForResult(i,112);
                }
                else if(items[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }




}