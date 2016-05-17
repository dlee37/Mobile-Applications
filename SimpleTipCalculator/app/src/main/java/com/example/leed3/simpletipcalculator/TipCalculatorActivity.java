package com.example.leed3.simpletipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.text.*;

public class TipCalculatorActivity extends AppCompatActivity {

    private Float total = 0.00f;
    private Float tipAmt = 0.00f;
    private Integer perPerson = 0;
    private Double tipPerPerson = 0.00;
    private Integer people = 1;
    private Float tipPercent = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        reset();
        setButtons();
        setSeekBar();
        setSpinner();
        setRadioButtons();
    }

    public void reset() {
        tipPercent = 0.0f;
        EditText billText = (EditText) findViewById(R.id.billAmount);
        billText.setText("");
        TextView perPersonView = (TextView) findViewById(R.id.totalPerPerson);
        perPersonView.setText("$0.00");
        TextView tipPerPersonView = (TextView) findViewById(R.id.tipPerPerson);
        tipPerPersonView.setText("$0.00");
        SeekBar pctBar = (SeekBar) findViewById(R.id.percentBar);
        pctBar.setProgress(0);
        total = 0.00f;
        tipAmt = 0.00f;
        perPerson = 0;
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.clearCheck();
        Spinner menu = (Spinner) findViewById(R.id.peopleSpinner);
        menu.setSelection(0);
    }

    public void calculate() {
        //
        EditText billText = (EditText) findViewById(R.id.billAmount);
        String billString = billText.getText().toString();
        TextView pctView = (TextView) findViewById(R.id.tipPercent);

        if (billString.equals("")) {
            display();
            return;
        }

        Float tempTotal = Float.parseFloat(billString);

        tipAmt = tempTotal * tipPercent;
        total = tempTotal + tipAmt;
        perPerson = Math.round((total/people) + 1);
        tipPerPerson = (double) tipAmt/people;
        display();
    }

    private void display() {
        TextView totalPerPerson = (TextView) findViewById(R.id.totalPerPerson);
        TextView tipPerPersonView = (TextView) findViewById(R.id.tipPerPerson);
        totalPerPerson.setText("$" + perPerson.toString() + ".00");
        DecimalFormat df = new DecimalFormat("#.##");
        String tip = df.format(tipPerPerson);
        tipPerPersonView.setText("$" + tip);
    }

    public void setSeekBar() {
        SeekBar pctBar = (SeekBar) findViewById(R.id.percentBar);

        pctBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tip = (TextView) findViewById(R.id.tipPercent);
                Integer pct = progress;
                tip.setText(pct.toString() + "%");
                tipPercent = (float) pct / 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                RadioGroup rd = (RadioGroup) findViewById(R.id.radioGroup);
                rd.clearCheck();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setButtons() {

        Button round = (Button) findViewById(R.id.round);
        Button reset = (Button) findViewById(R.id.reset);

        /*round.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate();
            }
        }); */

        round.setOnClickListener((v) -> {
                    calculate();
                });

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });
    }

    public void setSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.peopleSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.NumberOfPeople, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                String p = parent.getItemAtPosition(index).toString();
                people = Integer.parseInt(p);
                //Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)",
                        //Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void setRadioButtons() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) {
                    return;
                }

                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                String tempPct = rb.getText().toString().substring(0,2);
                //Toast.makeText(getApplicationContext(), tempPct, Toast.LENGTH_SHORT).show();
                int pct = Integer.parseInt(tempPct);
                tipPercent = Float.parseFloat(tempPct)/100;
                TextView percent = (TextView) findViewById(R.id.tipPercent);
                percent.setText(tempPct + "%");
                SeekBar sb = (SeekBar) findViewById(R.id.percentBar);
                sb.setProgress(pct);
            }
        });
    }
}