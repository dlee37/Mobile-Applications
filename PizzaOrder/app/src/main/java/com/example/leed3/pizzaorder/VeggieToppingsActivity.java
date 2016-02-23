package com.example.leed3.pizzaorder;

import android.os.*;
import android.support.v7.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

/**
 * Created by leed3 on 2/14/2016.
 */
public class VeggieToppingsActivity extends AppCompatActivity {

    public String size;
    public int numVeggieToppings = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veggie_toppings);
        Intent i = getIntent();
        size = i.getStringExtra("size");
        setVeggieToppingsView();
        setNextButton();
    }

    public void setVeggieToppingsView() {

        ListView list = (ListView) findViewById(R.id.veggieToppingsView);
        String[] veggies = {"Onion", "Peppers", "Spinach", "Broccoli", "Olives", "Zucchini"};
        List<String> veggiesList = Arrays.asList(veggies);
        list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,
                veggiesList);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;
                if (item.isChecked()) {
                    numVeggieToppings++;
                }
                else {
                    numVeggieToppings--;
                }
            }
        });
    }

    public void setNextButton() {
        Button veggieButton = (Button) findViewById(R.id.veggieButton);
        veggieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeggieToppingsActivity.this, MeatToppingsActivity.class);
                intent.putExtra("size",size);
                intent.putExtra("numVeggieToppings", numVeggieToppings);
                startActivity(intent);
            }
        });
    }
}