package com.example.leed3.sqlitecities;

import java.io.Serializable;
import java.lang.String;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by leed3 on 4/8/2016.
 */

public class City implements Serializable {
    private String name;
    private int pop2k;
    private int pop10;
    private long id;

    public City(String name, int pop2k, int pop10) {
        this.name = name;
        this.pop2k = pop2k;
        this.pop10 = pop10;
    }

    public String toString() {
        return name;
    }

    public String getCity() {
        return name;
    }

    public String get2000() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        String pop = nf.format(pop2k);
        return pop;
    }

    public String get2010() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        String pop = nf.format(pop10);
        return pop;
    }

    public int getDifference() {
        return pop10 - pop2k;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}