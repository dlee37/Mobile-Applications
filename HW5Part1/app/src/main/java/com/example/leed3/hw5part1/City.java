package com.example.leed3.hw5part1;

import java.lang.String;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by leed3 on 4/3/2016.
 */
public class City {
    private String name;
    private String pop2k;
    private String pop10;

    public City(String name, String pop2k, String pop10) {
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
        return pop2k;
    }

    public String get2010() {
        return pop10;
    }
}
