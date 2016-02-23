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
public class MeatToppingsActivity extends AppCompatActivity {

    private String size;
    private int numVeggieToppings = 0;
    private int numMeatToppings = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meat_toppings);
        Intent i = getIntent();
        size = i.getStringExtra("size");
        numVeggieToppings = i.getIntExtra("numVeggieToppings", 0);
        setMeatToppingsView();
        setNextButton();
    }

    public void setMeatToppingsView() {

        ListView list = (ListView) findViewById(R.id.meatToppingsView);
        String[] meat = {"Pepperoni", "Chicken", "Ground Beef", "Anchovies", "Bacon Bits"};
        List<String> meatList = Arrays.asList(meat);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,
                meatList);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;
                if (item.isChecked()) {
                    numMeatToppings++;
                }
                else {
                    numMeatToppings--;
                }
            }
        });
    }

    public void setNextButton() {
        Button meatButton = (Button) findViewById(R.id.meatToppingsButton);
        meatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeatToppingsActivity.this, NameActivity.class);
                intent.putExtra("size", size);
                intent.putExtra("numVeggieToppings", numVeggieToppings);
                intent.putExtra("numMeatToppings", numMeatToppings);
                startActivity(intent);
            }
        });
    }
}
