package com.example.note_rmgs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    String from="",name="";
    Location userlocation;
    LocationManager locationManager;
    LocationListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        ButterKnife.bind(this);
        Intent i=getIntent();
        from=i.getStringExtra("from");
        name=i.getStringExtra("name");
        img_left.setVisibility(View.VISIBLE);
        img_right.setVisibility(View.GONE);
        img_icon.setVisibility(View.GONE);
        img_left.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        txt_heading.setText(""+name);

        if(from.equalsIgnoreCase("new")){
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

            save.setText("Update");
            getAndSetNotes();


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
}