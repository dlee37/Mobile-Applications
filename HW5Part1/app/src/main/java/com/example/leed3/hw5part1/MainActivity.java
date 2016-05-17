package com.example.leed3.hw5part1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String url;
    private ArrayList<City> cities;
    private Scrapper scrapper;
    private City currentSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://malegislature.gov/District/CensusData";
        scrapper = new Scrapper();
        scrapper.execute(url);
    }

    private void setSpinner() {
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(this,
                android.R.layout.simple_spinner_dropdown_item,cities);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView currentPop = (TextView) findViewById(R.id.currentPopulation);
            TextView prevPop = (TextView) findViewById(R.id.previousPopulation);

            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                currentSelection = cities.get(index);
                currentPop.setText(currentSelection.get2010());
                prevPop.setText(currentSelection.get2000());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void onStop() {
        super.onStop();
    }

    protected void setArray(ArrayList<City> array) {
        cities = array;
        setSpinner();
    }

    public class Scrapper extends AsyncTask<String, String, ArrayList> {
        HttpURLConnection massUrl;
        String regex, regex2;
        Pattern p1, p2;
        Matcher m1, m2;
        ProgressDialog progress = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Cities are loading, please wait...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();

            regex = "(<td scope=\"row\">)(\\w{2,})(</td>)";
            regex2 = "( <td class=\"number\">)(.*)(</td>)";
            if (p1 == null)
                p1 = Pattern.compile(regex);
            if (p2 == null)
                p2 = Pattern.compile(regex2);
            cities = new ArrayList();
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
                ArrayList<String> temp = new ArrayList();
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
                                temp.add(population);
                                i++;
                            }
                        }
                        if (temp.size() == 2) {
                            City newCity = new City(name, temp.get(0), temp.get(1));
                            cities.add(newCity);
                            temp.clear();
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
            return cities;
        }

        public void onPostExecute(ArrayList towns){
            if (progress.isShowing()) {
                progress.dismiss();
            }
            setArray(cities);
        }
    }
}
