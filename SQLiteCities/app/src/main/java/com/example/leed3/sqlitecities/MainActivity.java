package com.example.leed3.sqlitecities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String url;
    private ArrayList<City> allCities;
    private Scrapper scrapper;
    private City currentSelection;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://malegislature.gov/District/CensusData";
        db = new SQLiteHelper(getApplicationContext());
        scrapper = new Scrapper();
        scrapper.execute(url);
        setButtons();
    }

    private void setSpinner() {
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(this,
                android.R.layout.simple_spinner_dropdown_item,allCities);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView currentPop = (TextView) findViewById(R.id.currentPopulation);
            TextView prevPop = (TextView) findViewById(R.id.previousPopulation);

            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                currentSelection = allCities.get(index);
                currentPop.setText(currentSelection.get2010());
                prevPop.setText(currentSelection.get2000());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void setArrays(ArrayList<City> array) {
        allCities = array;
        setSpinner();
    }

    public class Scrapper extends AsyncTask<String, String, ArrayList> {
        HttpURLConnection massUrl;
        String r1, r2;
        Pattern p1, p2;
        Matcher m1, m2;
        ProgressDialog progress = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            r1 = "(<td scope=\"row\">)(\\w{2,})(</td>)";
            r2 = "( <td class=\"number\">)(.*)(</td>)";
            if (p1 == null)
                p1 = Pattern.compile(r1);
            if (p2 == null)
                p2 = Pattern.compile(r2);
            allCities = new ArrayList();
            progress.setMessage("Cities are loading, please wait...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ArrayList doInBackground(String... params) {
            String urlString = params[0];
            Log.i("do in background", urlString);

            try {
                URL url = new URL(urlString);
                massUrl = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(massUrl.getInputStream());
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);

                String line = br.readLine();
                ArrayList<Integer> vals = new ArrayList();
                while ((line = br.readLine()) != null) {
                    m1 = p1.matcher(line);
                    if (m1.find()) {
                        String name = m1.group(2);
                        int i = 0;
                        while (i < 2){
                            line = br.readLine();
                            m2 = p2.matcher(line);
                            if (m2.find()){
                                String population = m2.group(2);
                                if (population.contains(","))
                                    population = population.replace(",","");
                                int pop = Integer.valueOf(population);
                                vals.add(pop);
                                i++;
                            }
                        }
                        if (vals.size() == 2) {
                            City newCity = new City(name, vals.get(0), vals.get(1));
                            allCities.add(newCity);
                            db.addCity(newCity);
                            vals.clear();
                        }
                        else{
                            Log.wtf("From Loading cities", "Please go easy on me, I'm " +
                                    "just a sad boy.");
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                massUrl.disconnect();
            }
            return allCities;
        }

        public void onPostExecute(ArrayList towns){
            if (progress.isShowing()) {
                progress.dismiss();
            }
            setArrays(allCities);
        }
    }

    public void setButtons() {
        Button most = (Button) findViewById(R.id.biggestIncreaseButton);
        Button least = (Button) findViewById(R.id.biggestDecreaseButton);
        Button list5k = (Button) findViewById(R.id.lessThan5kButton);

        most.setOnClickListener(new View.OnClickListener() {
            TextView tv = (TextView) findViewById(R.id.buttonPop);
            @Override
            public void onClick(View v) {
                String getCity = db.getLargest();
                String getPop = db.getLargestPop();
                tv.setText("The city/town with the largest population increase is: " + getCity +
                        ", with an increase of " + getPop + " people.");
            }
        });

        least.setOnClickListener(new View.OnClickListener() {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            TextView tv = (TextView) findViewById(R.id.buttonPop);
            @Override
            public void onClick(View v) {
                String getCity = db.getSmallest();
                String getPop = db.getSmallestPop();
                tv.setText("The city/town with the largest population decrease is: " + getCity +
                        ", with a decrease of " + getPop + " people.");
            }
        });

        list5k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,listOf5kActivity.class);
                startActivityForResult(i,100);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.deleteDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            TextView tv = (TextView) findViewById(R.id.buttonPop);
            tv.setText("");
        }
    }
}
