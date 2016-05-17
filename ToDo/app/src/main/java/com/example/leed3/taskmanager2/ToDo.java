package com.example.leed3.taskmanager2;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by leed3 on 2/28/2016.
 */
public class ToDo implements Serializable {

    private int index;
    private String title;
    private String description;
    private String date;
    private long id;

    public ToDo(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        Log.i("getdate",date.toString());
        return date.toString();
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setDescription(String d) {
        description = d;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void decrementId() {
        id--;
    }

    public void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }
}
