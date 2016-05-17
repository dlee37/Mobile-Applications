package com.example.leed3.threedayweatherforecast;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText search;
    private Button find;
    private ArrayList<Weather> weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText) findViewById(R.id.weather_search);
        setButton();
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, ArrayList> {

        ProgressDialog progress = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage("Weather is loading, please wait...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            ArrayList<Weather> weather = new ArrayList<>();
            JSONObject jObj = ((new WeatherHttpClient()).getWeatherData(params[0]));
            try {
                //call JSON parser to extract data from JSONobject into weather array
                weather = JSONWeatherParser.getWeather(jObj);
                for (Weather w : weather) {
                    w.iconData = ((new WeatherHttpClient()).getImage(w.currentCondition.getIcon()));
                }
            } catch (JSONException e) {
                Log.e("From search", "Cannot find city");
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onPostExecute(ArrayList weather) {
            super.onPostExecute(weather);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            setListView(weather);
        }
    }

    private void setButton() {
        find = (Button) findViewById(R.id.findButton);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getCity = search.getText().toString();
                JSONWeatherTask task = new JSONWeatherTask();
                //Toast.makeText(MainActivity.this,getCity, Toast.LENGTH_LONG).show();
                task.execute(getCity);
            }
        });
    }

    private void setListView(ArrayList weather) {
        this.weather = weather;
        setTitleView();
        if (weather.size() == 0) {
            TextView cannotFindTextView = (TextView) findViewById(R.id.cannotFindTextView);
            cannotFindTextView.setText("Cannot find city!");
            return;
        }
        ListView lv = (ListView) findViewById(R.id.listview);
        ListViewAdapter adapter = new ListViewAdapter(this,R.layout.listview_adapter,
                weather);
        lv.setAdapter(adapter);
    }

    private void setTitleView() {
        TextView tv = (TextView) findViewById(R.id.titleTextView);
        String city = weather.get(0).location.getCity();
        tv.setText("10 Day Forecast of " + city);
    }
}
