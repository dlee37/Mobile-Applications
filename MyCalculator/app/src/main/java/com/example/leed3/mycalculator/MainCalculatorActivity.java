package com.example.leed3.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainCalculatorActivity extends AppCompatActivity {

    private float total = 0;
    private List<String> values = new ArrayList<String>();
    private boolean onNumber = false;
    private boolean equal = false;

    public void clickButton(View view) {

        Button b = (Button) view;
        String val = b.getText().toString();
        clicked(val);

    }

    public void clicked(String val) {

        TextView screen = (TextView) findViewById(R.id.calcTextView);
        String scrn = screen.getText().toString();

        if (val.equals("del")) {

            if (values.size() == 0) return;

            if (equal) {
                equal = false;
                values.clear();
                onNumber = false;
                total = 0;
                setContentView(R.layout.activity_main_calculator);
                return;
            }

            if (values.get(values.size()-1).length() == 1) {
                values.remove(values.size() - 1);
                onNumber = false;

                if (values.isEmpty()) {
                    screen.setText("0");
                    return;
                }

                scrn = scrn.substring(0,scrn.length()-1);
                screen.setText(scrn);
                return;
            }

            else {
                String vs = values.get(values.size()-1);
                values.remove(values.size()-1);
                vs = vs.substring(0,vs.length() - 1);
                values.add(vs);
            }

            scrn = scrn.substring(0,scrn.length()-1);
            screen.setText(scrn);
            return;
        }

        else if (val.equals("=")) {

            if (values.size() == 0) return;

            else if (ifOperation(values.get(values.size() - 1)))
                return;

            calculate();
            screen.setText(Float.toString(total));
            total = 0;
            equal = true;
            return;
        }

        else if (val.equals("+") || val.equals("-") || val.equals("*") ||
                val.equals("/")) {

            if (values.size() == 0 || ifOperation(values.get(values.size()-1)))
                return;

            onNumber = false;
            values.add(val);
            screen.setText(screen.getText().toString() + val);
        }

        else if (val.equals(".")) {

            String temp;

            if (values.size() == 0) {
                screen.setText(val);
                values.add(val);
                return;
            }

            else if (values.get(values.size()-1).contains(".")) return;

            else if (ifOperation(values.get(values.size()-1))) {
                values.add(val);
            }

            else {
                temp = values.get(values.size() - 1);
                values.remove(values.size() - 1);
                temp += val;
                values.add(temp);
            }

            screen.setText(scrn + val);
            return;
        }

        else {

            if (equal) return;

            if (values.size() == 0 && val.equals("0")) return;

            if (values.size() == 0) {
                values.add(val);
                screen.setText(val);
                onNumber = true;
                return;
            }

            if (ifOperation(values.get(values.size()-1))) {
                onNumber = true;
                values.add(val);
            }

            else if (onNumber || values.get(values.size()-1).contains(".")) {
                onNumber = true;
                String v = values.get(values.size()-1);
                values.remove(values.size()-1);
                v += val;
                values.add(v);
            }

            screen.setText(scrn + val);
        }
    }

    private Boolean ifOperation(String val) {

        if (val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/")) {
            return true;
        }

        return false;
    }

    public void calculate() {

        String num = values.get(0);
        Float firstNum = Float.parseFloat(num);
        total += firstNum;
        int index = 1;

        while (index < values.size()) {

            String val = values.get(index);

            if (val.equals("+")) {
                index += 1;
                float v = Float.parseFloat(values.get(index));
                total += v;
            }

            else if (val.equals("-")) {
                index += 1;
                float v = Float.parseFloat(values.get(index));
                total -= v;
            }
            else if (val.equals("*")) {
                index += 1;
                float v = Float.parseFloat(values.get(index));
                total *= v;
            }

            else if (val.equals("/")) {
                index += 1;
                float v = Float.parseFloat(values.get(index));
                total /= v;
            }

            index += 1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calculator);
    }
}