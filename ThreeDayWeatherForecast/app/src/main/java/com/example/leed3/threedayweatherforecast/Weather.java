package com.example.leed3.threedayweatherforecast;

/**
 * Created by leed3 on 4/9/2016.
 */
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Weather implements Serializable {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();

    public Bitmap iconData;

    public  class CurrentCondition implements Serializable {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;
        private float pressure;
        private float humidity;
        private Date date;

        public int getWeatherId() {
            return weatherId;
        }
        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getDescr() {
            return descr;
        }
        public void setDescr(String descr) {
            this.descr = descr;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public float getPressure() {
            return pressure;
        }
        public void setPressure(float pressure) {
            this.pressure = pressure;
        }
        public float getHumidity() {
            return humidity;
        }
        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(long date) {
            this.date = new Date(date*1000);
        }


    }

    public  class Temperature implements Serializable {
        private float temp;
        private float minTemp;
        private float maxTemp;
        private float dayTemp;
        private float nightTemp;

        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public float getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }
        public float getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }
        public void setDayTemp(float dayTemp) {
            this.dayTemp = dayTemp;
        }
        public void setNightTemp(float nightTemp) {
            this.nightTemp = nightTemp;
        }
        public float getDayTemp() {
            return dayTemp;
        }
        public float getNightTemp() {
            return nightTemp;
        }
    }

    public  class Wind implements Serializable {
        private float speed;
        private float deg;
        public float getSpeed() {
            return speed;
        }
        public void setSpeed(float speed) {
            this.speed = speed;
        }
        public float getDeg() {
            return deg;
        }
        public void setDeg(float deg) {
            this.deg = deg;
        }


    }

    public  class Rain implements Serializable {
        private String time;
        private float amount;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmount() {
            return amount;
        }
        public void setAmount(float ammount) {
            this.amount = ammount;
        }



    }

    public  class Snow implements Serializable {
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmount() {
            return amount;
        }
        public void setAmount(float ammount) {
            this.amount = ammount;
        }


    }

    public  class Clouds implements Serializable {
        private int perc;
        public int getPerc() {
            return perc;
        }
        public void setPerc(int perc) {
            this.perc = perc;
        }
    }

}
