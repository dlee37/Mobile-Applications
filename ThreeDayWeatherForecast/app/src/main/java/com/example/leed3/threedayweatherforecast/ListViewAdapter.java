package com.example.leed3.threedayweatherforecast;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by leed3 on 4/9/2016.
 */
public class ListViewAdapter extends ArrayAdapter<Weather> {

    private final String DEGREE = "\u00b0";
    Context context;
    ArrayList<Weather> weather;

    public ListViewAdapter(Context context, int resource, ArrayList<Weather> weather) {
        super(context, R.layout.listview_adapter, weather);
        this.context = context;
        this.weather = weather;
    }

    public View getView(int position, View ConvertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        Weather w = weather.get(position);
        View row = inflater.inflate(R.layout.listview_adapter,null); //this is what is returned

        TextView date = (TextView) row.findViewById(R.id.dateTextView);
        TextView pmTextView = (TextView) row.findViewById(R.id.pmTextView);
        TextView amTextView = (TextView) row.findViewById(R.id.amTextView);
        ImageView image = (ImageView) row.findViewById(R.id.image);
        TextView descTextView = (TextView) row.findViewById(R.id.descTextView);
        TextView windTextView = (TextView) row.findViewById(R.id.windTextView);
        TextView rainTextView = (TextView) row.findViewById(R.id.rainTextView);
        TextView snowTextView = (TextView) row.findViewById(R.id.snowTextView);
        date.setText(w.currentCondition.getDate().toString());
        //now assign all of the text views
        pmTextView.setText("PM: " + w.temperature.getNightTemp() + " " + DEGREE + "F");
        amTextView.setText("AM: " + w.temperature.getDayTemp() + " " + DEGREE + "F");
        if (w.iconData != null && w.iconData.getByteCount() > 0) {
            image.setImageBitmap(w.iconData);
        }
        descTextView.setText("Description: " + w.currentCondition.getDescr());
        windTextView.setText("WIND: " + w.wind.getSpeed() + " mph");
        rainTextView.setText("RAIN: " + w.rain.getAmount() + " in.");
        snowTextView.setText("SNOW: " + w.snow.getAmount() + " in.");
        return row;
    }
}
