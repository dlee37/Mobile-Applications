package com.example.leed3.sqlitecities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by leed3 on 4/8/2016.
 */
public class listOf5kActivity extends AppCompatActivity {

    SQLiteHelper helper;
    ArrayList<String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.less_than_5k);
        helper = new SQLiteHelper(getApplicationContext());
        cities = helper.getLessThan5k();
        setListView();
        setButton();
    }

    protected void setListView() {
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,cities);
        lv.setAdapter(adapter);
    }

    protected void setButton() {
        Button button = (Button) findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listOf5kActivity.this,MainActivity.class);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
