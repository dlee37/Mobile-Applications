package com.example.leed3.courseregistersystem;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by leed3 on 5/1/2016.
 */
public class ViewActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private String name;
    private String gradYear;
    private String school;
    private TextView nameText;
    private TextView gradText;
    private TextView schoolText;
    private Button back;
    GoogleMap map;
    LatLng lat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        name = getIntent().getStringExtra("name");
        gradYear = getIntent().getStringExtra("gradYear");
        school = getIntent().getStringExtra("school");
        nameText = (TextView) findViewById(R.id.view_student_name);
        gradText = (TextView) findViewById(R.id.view_grad_year);
        schoolText = (TextView) findViewById(R.id.view_school_name);
        back = (Button) findViewById(R.id.view_student_back_button);
        setTextViews();
        setButton();
        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);
    }

    private void setTextViews() {
        nameText.setText(name);
        gradText.setText(gradYear);
        schoolText.setText(school);
    }

    private void setButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewActivity.this,MainActivity.class);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {    // map is loaded but not laid out yet
        this.map = map;
        map.setOnMapLoadedCallback(this);      // calls onMapLoaded when layout done
        UiSettings mapSettings;
        mapSettings = map.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        String schoolName = school;
        schoolName = schoolName.toLowerCase();
        List<Address> addresses = null;
        if (!schoolName.contains("university") && !schoolName.contains("school") &&
                !schoolName.contains("college") && !schoolName.contains("institution")) {
            Toast.makeText(this, "Please type school, college, university, or institution" +
                    "in the school name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (school != null || !school.equals("")) {
            Geocoder gc = new Geocoder(getApplicationContext());
            try {
                addresses = gc.getFromLocationName(schoolName, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Address address = addresses.get(0); //gets the first relevant known school
        lat = new LatLng(address.getLatitude(),address.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(lat)
                .title(address.getFeatureName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 17), 3000, null);
        map.setMyLocationEnabled(true);
    }

    public void onSearch(View view) throws IOException {
        map.clear();
        EditText loc = (EditText) findViewById(R.id.school_name);
        String schoolName = loc.getText().toString();
        schoolName = schoolName.toLowerCase();
        List<Address> addresses = null;
        if (!schoolName.contains("university") && !schoolName.contains("school") &&
                !schoolName.contains("college") && !schoolName.contains("institution")) {
            Toast.makeText(this,"Please type school, college, university, or institution" +
                    "in the school name!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (schoolName != null || !schoolName.equals("")) {
            Geocoder gc = new Geocoder(getApplicationContext());
            addresses = gc.getFromLocationName(schoolName, 1);
        }

        Address address = addresses.get(0); //gets the first relevant known school
        lat = new LatLng(address.getLatitude(),address.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(lat)
                .title(address.getFeatureName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 17), 3000, null);
    }

    @Override
    public void onMapLoaded() {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                EditText et = (EditText) findViewById(R.id.school_name);
                et.setText(marker.getTitle());
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (lat != null) {
            LatLng markerLatLng = marker.getPosition();
            map.addPolyline(new PolylineOptions()
                            .add(lat)
                            .add(markerLatLng)
            );
            return true;
        } else {
            return false;
        }
    }
}
