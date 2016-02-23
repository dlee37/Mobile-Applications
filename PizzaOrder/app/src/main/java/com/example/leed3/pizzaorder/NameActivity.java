package com.example.leed3.pizzaorder;

import android.content.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by leed3 on 2/18/2016.
 */
public class NameActivity extends AppCompatActivity {

    private String size;
    private int numVeggieToppings;
    private int numMeatToppings;
    private String name;

    public void onCreate(Bundle savedInstacedState) {
        super.onCreate(savedInstacedState);
        setContentView(R.layout.yourname);
        Intent i = getIntent();
        size = i.getStringExtra("size");
        numVeggieToppings= i.getIntExtra("numVeggieToppings", 0);
        numMeatToppings = i.getIntExtra("numMeatToppings",0);
        setButton();
    }

    public void setButton() {
        Button b = (Button) findViewById(R.id.nextButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.name);
                name = et.getText().toString();
                Intent intent = new Intent(NameActivity.this, ReceiptActivity.class);
                intent.putExtra("size", size);
                intent.putExtra("numVeggieToppings", numVeggieToppings);
                intent.putExtra("numMeatToppings", numMeatToppings);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}
