package com.example.leed3.threedayweatherforecast;

/**
 * Created by leed3 on 4/9/2016.
 */
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONWeatherParser {

    public static ArrayList<Weather> getWeather(JSONObject jObj) throws JSONException {
        ArrayList<Weather> forecast = new ArrayList<>();
        int count = getInt("cnt",jObj);
        for (int i = 0; i < count; i++) {
            forecast.add(new Weather());
        } //this creates new weather

        // We start extracting the info
        Location loc = new Location();
        JSONObject cityObj = getObject("city", jObj); //first object on the list
        System.out.println("This works!");
        loc.setCity(getString("name",cityObj));
        loc.setCountry(getString("country",cityObj));

        JSONObject coordObj = getObject("coord", cityObj); //coordinates within city object
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        for (Weather w : forecast) {
            w.location = loc;
        }

        /* JSONObject sysObj = getObject("sys", jObj);
        loc.setCountry(getString("country", sysObj));
        loc.setSunrise(getInt("sunrise", sysObj));
        loc.setSunset(getInt("sunset", sysObj));
        loc.setCity(getString("name", jObj)); */

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("list");
        System.out.println("Count is: " + count);

        // we iterate this 3 times for the 3 days
        for (int i = 0; i < count; i++) {
            Weather weather = forecast.get(i);
            JSONObject JSONWeather = jArr.getJSONObject(i);
            weather.currentCondition.setDate(getLong("dt",JSONWeather));
            JSONObject temp = JSONWeather.getJSONObject("temp");
            weather.temperature.setDayTemp(getFloat("day",temp));
            weather.temperature.setNightTemp(getFloat("night",temp));
            JSONArray jWeatherArr = JSONWeather.getJSONArray("weather");
            JSONObject w = jWeatherArr.getJSONObject(0);
            weather.currentCondition.setWeatherId(getInt("id",w));
            weather.currentCondition.setCondition(getString("main",w));
            weather.currentCondition.setDescr(getString("description",w));
            weather.currentCondition.setIcon(getString("icon",w));
            //System.out.println(weather.currentCondition.getIcon());
            weather.wind.setSpeed(getFloat("speed",JSONWeather));
            //to determine the rain
            try {
                weather.rain.setAmount(getFloat("rain",JSONWeather));
            }

            catch (NullPointerException e) {
                weather.rain.setAmount(0);
                Log.i("From WeatherParser", "There is no rain string");
            }

            catch (Exception ex) {
                weather.rain.setAmount(0);
                Log.i("From not NullException", "There is no rain");
            }
            //now do the same for snow
            try {
                weather.snow.setAmount(getFloat("snow",JSONWeather));
            }

            catch (NullPointerException e) {
                weather.snow.setAmount(0);
                Log.i("From WeatherParser", "There is no snow");
            }

            catch (Exception ex) {
                weather.snow.setAmount(0);
                Log.i("From not NullException", "There is no snow");
            }
            forecast.set(i,weather);
        }

        return forecast;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        System.out.println(jObj.getString(tagName));
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }
}
