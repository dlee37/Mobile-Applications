package com.example.leed3.pizzaorder;

import android.os.*;
import android.support.v7.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

/**
 * Created by leed3 on 2/14/2016.
 */
public class ReceiptActivity extends AppCompatActivity {

    private Pizza pizza;
    private String size;
    private int veggieToppings;
    private int meatToppings;
    private String name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        Intent i = getIntent();
        size = i.getStringExtra("size");
        veggieToppings = i.getIntExtra("numVeggieToppings", 0);
        meatToppings = i.getIntExtra("numMeatToppings", 0);
        name = i.getStringExtra("name");
        setTextView();
        setButtons();
    }

    public void setTextView() {
        TextView tv = (TextView) findViewById(R.id.totalView);
        pizza = new Pizza(size, veggieToppings, meatToppings);
        tv.setText(name + ", the total is: $" + Integer.toString(pizza.getPrice()));
    }

    public void setButtons() {
        Button button = (Button) findViewById(R.id.newOrderButton);
        Button toast = (Button) findViewById(R.id.toastButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptActivity.this, PizzaSizeActivity.class);
                startActivity(intent);
            }
        });

        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getApplicationContext(),
                        "Your Order is on the Way!",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}