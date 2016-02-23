package com.example.leed3.calculator1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class CalculatorActivity extends AppCompatActivity {

    public void clickButton(View view) {

        Button b = (Button) view;
        String val = b.getText().toString();
        clicked(val);
    }

    public void clicked(String val) {

        TextView screen = (TextView) findViewById(R.id.calcTextView);
        String scrn = screen.getText().toString();

        if (val.equals("del")) {

            if (scrn.length() == 1)
                screen.setText("0");

            else {
                scrn = scrn.substring(0, scrn.length() - 1);
                screen.setText(scrn);
            }
        }

        else if (val.equals("=")) {

            screen.setText("0");
        }

        else if (scrn.equals("0")) {

            screen.setText(val);
        }

        else {
            screen.setText(scrn + val);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }
}
