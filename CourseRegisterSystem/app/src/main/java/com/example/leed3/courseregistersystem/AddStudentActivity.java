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
public class AddStudentActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnMarkerClickListener {

    GoogleMap map;
    LatLng lat;
    ArrayList<String> gradYears;
    EditText studentName;
    Spinner studentGradYear;
    EditText school;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_students_activity);
        gradYears = new ArrayList<>();
        studentName = (EditText) findViewById(R.id.student_name);
        school = (EditText) findViewById(R.id.school_name);
        setSpinner();
        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);
        setButtons();
    }

    @Override
    public void onMapReady(GoogleMap map) {    // map is loaded but not laid out yet
        this.map = map;
        map.setOnMapLoadedCallback(this);      // calls onMapLoaded when layout done
        UiSettings mapSettings;
        mapSettings = map.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
    }

    private void setButtons() {
        Button add = (Button) findViewById(R.id.add_student_button);
        Button back = (Button) findViewById(R.id.add_student_back_button);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddStudentActivity.this, MainActivity.class);
                String name = studentName.getText().toString();
                String gradYear = studentGradYear.getSelectedItem().toString();
                String schoolName = school.getText().toString();
                String sn = schoolName.toLowerCase();
                if (!sn.contains("university") && !sn.contains("school") &&
                        !sn.contains("college") && !sn.contains("institution")
                        && !sn.contains("institute")) {
                    Toast.makeText(getApplicationContext(), "Please type school, college, university, " +
                            "or institution" + "in the school name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Student s = new Student(name, gradYear, schoolName);
                i.putExtra("student", s);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddStudentActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });
    }

    public void onSearch(View view) throws IOException {
        map.clear();
        EditText loc = (EditText) findViewById(R.id.school_name);
        String school = loc.getText().toString();
        school = school.toLowerCase();
        List<Address> addresses = null;
        if (!school.contains("university") && !school.contains("school") &&
                !school.contains("college") && !school.contains("institution")
                && !school.contains("institute")) {
            Toast.makeText(this, "Please type school, college, university, or institution" +
                    "in the school name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (school != null || !school.equals("")) {
            Geocoder gc = new Geocoder(getApplicationContext());
            addresses = gc.getFromLocationName(school, 1);
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
        studentGradYear = (Spinner) findViewById(R.id.grad_year);
        int val = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(val);
        val--;
        for (int i = 0; i < 6; i++) {
            String year = String.valueOf(val);
            gradYears.add(year);
            val++;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,gradYears);
        System.out.println(gradYears);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        System.out.println("The first item is: " + adapter.getItem(0));
        studentGradYear.setAdapter(adapter);
    }
}
