package com.example.leed3.pizzaorder;

import android.content.*;
import android.app.*;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PizzaSizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_of_pizza);
        setSizeListView();
    }

    public void setSizeListView() {

        ListView list = (ListView) findViewById(R.id.sizeListView);
        String[] sizes = {"Small", "Medium", "Large"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                sizes);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
        list.setClickable(true);
        //list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String size = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(PizzaSizeActivity.this, VeggieToppingsActivity.class);
                intent.putExtra("size", size);
                startActivity(intent);
            }
        });
    }
}