package com.example.leed3.taskmanager;

import android.util.*;
import java.io.*;
import java.util.*;

/**
 * Created by leed3 on 2/28/2016.
 */
public class ToDo implements Serializable {

    private int index;
    private String title;
    private String description;
    private Date date;
    private AllTasks at;

    public ToDo(String title, String description, AllTasks at) {
        this.title = title;
        this.at = at;
        this.description = description;
        date = new Date();
        at.addTask(this);
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
}
