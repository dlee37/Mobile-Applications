package com.example.leed3.courseregistersystem;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leed3 on 5/1/2016.
 */
public class ModifyActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private Student s;
    private EditText name;
    private Spinner year;
    private EditText school;
    private Button back;
    private Button finish;
    private int index;
    ArrayList<String> gradYears;
    GoogleMap map;
    LatLng lat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_activity);
        gradYears = new ArrayList<>();
        s = (Student) getIntent().getSerializableExtra("student");
        index = getIntent().getIntExtra("index",0);
        name = (EditText) findViewById(R.id.edit_student_name);
        year = (Spinner) findViewById(R.id.edit_grad_year);
        school = (EditText) findViewById(R.id.school_name);
        back = (Button) findViewById(R.id.modify_student_back_button);
        finish = (Button) findViewById(R.id.finish_edit_button);
        setSpinner();
        setListViews();
        setButtons();
        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);
    }

    private void setListViews() {
        name.setText(s.getStudentName());
        school.setText(s.getSchoolName());
    }

    private void setButtons() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModifyActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModifyActivity.this, MainActivity.class);
                s.setStudentName(name.getText().toString());
                s.setGradYear(year.getSelectedItem().toString());
                s.setSchool(school.getText().toString());
                i.putExtra("student", s);
                i.putExtra("index", index);
                setResult(RESULT_OK, i);
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
        String schoolName = s.getSchoolName();
        schoolName = schoolName.toLowerCase();
        List<Address> addresses = null;
        if (!schoolName.contains("university") && !schoolName.contains("school") &&
                !schoolName.contains("college") && !schoolName.contains("institution") &&
                !schoolName.contains("institute")) {
            Toast.makeText(this,"Please type school, college, university, or institution" +
                    "in the school name!",Toast.LENGTH_SHORT).show();
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
                !schoolName.contains("college") && !schoolName.contains("institution")
                && !schoolName.contains("institute")) {
            Toast.makeText(this,"Please type school, college, university, or institution" +
                    "in the school name!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (schoolName != null || !schoolName.equals("")) {
            Geocoder gc = new Geocoder(getApplicationContext());
            addresses = gc.getFromLocationName(schoolName, 1);
        }

        Address address = addresses.get(0); //gets the first relevant known school
        System.out.println(address);
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

    private void setSpinner() {
        int val = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(val);
        val--;
        for (int i = 0; i < 6; i++) {
            String y = String.valueOf(val);
            gradYears.add(y);
            val++;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,gradYears);
        System.out.println(gradYears);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        System.out.println("The first item is: " + adapter.getItem(0));
        year.setAdapter(adapter);
        year.setSelection(gradYears.indexOf(s.getGradYear()));
    }
}
