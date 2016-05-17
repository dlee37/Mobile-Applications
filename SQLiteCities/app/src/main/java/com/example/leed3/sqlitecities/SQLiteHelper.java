package com.example.leed3.sqlitecities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by leed3 on 4/8/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String TABLE_NAME = "CityList";
    private static final String COLUMN_ID = "_id";
    private static final String DATABASE_NAME = "cities.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CITYLIST_NAME  = "Name";
    private static final String CITYLIST_2K = "Population2000";
    private static final String CITYLIST_2010 = "Population2010";
    private static final String CITYLIST_SMALLEST = "Difference";
    private static final String[] COLUMNS = {CITYLIST_NAME,CITYLIST_2K,CITYLIST_2010,
            CITYLIST_SMALLEST};

    private static final String DB_CREATE = "create table " + TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " + CITYLIST_NAME + " text not null, " +
            CITYLIST_2K + " integer not null, " + CITYLIST_2010 + " integer not null, " +
            CITYLIST_SMALLEST + " integer not null);";

    public SQLiteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }

    public void addCity(City city) {
        ContentValues cv = new ContentValues();
        cv.put(CITYLIST_NAME,city.getCity());
        cv.put(CITYLIST_2K,city.get2000());
        cv.put(CITYLIST_2010,city.get2010());
        cv.put(CITYLIST_SMALLEST, city.getDifference());
        long taskId = getWritableDatabase().insert(TABLE_NAME,null,cv);
        city.setId(taskId);
    }

    public ArrayList<String> getLessThan5k() {
        ArrayList<String> less = new ArrayList<>();
        Cursor cs = getWritableDatabase().query(TABLE_NAME,COLUMNS,null,null,null,null,
                CITYLIST_2010 + " ASC");
        int pop2010Entry = cs.getColumnIndex(CITYLIST_2010);
        int cityName = cs.getColumnIndex(CITYLIST_NAME);
        cs.moveToFirst();
        while (cs.getInt(pop2010Entry) < 5000) {
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            int pop = cs.getInt(pop2010Entry);
            String strPop = nf.format(pop);
            String line = cs.getString(cityName) + ", Population: " +
                    strPop;
            if (less.contains(line)) {
                cs.moveToNext();
            }
            else {
                less.add(line);
                cs.moveToNext();
            }
        }
        cs.close();
        return less;
    }

    public String getSmallest() {
        Cursor cs = getWritableDatabase().query(TABLE_NAME, COLUMNS, null, null, null, null,
                CITYLIST_SMALLEST + " ASC");
        int cityName = cs.getColumnIndex(CITYLIST_NAME);
        cs.moveToFirst();
        String smallest = cs.getString(cityName);
        return smallest;
    }

    public String getSmallestPop() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        Cursor cs = getWritableDatabase().query(TABLE_NAME,COLUMNS,null,null,null,null,
                CITYLIST_SMALLEST + " ASC");
        int cityPop = cs.getColumnIndex(CITYLIST_SMALLEST);
        cs.moveToFirst();
        int smallest = cs.getInt(cityPop);
        return nf.format(Math.abs(smallest));
    }

    public String getLargest() {
        Cursor cs = getWritableDatabase().query(TABLE_NAME,COLUMNS,null,null,null,null,
                CITYLIST_SMALLEST + " DESC");
        int cityName = cs.getColumnIndex(CITYLIST_NAME);
        cs.moveToFirst();
        String largest = cs.getString(cityName);
        return largest;
    }

    public String getLargestPop() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        Cursor cs = getWritableDatabase().query(TABLE_NAME,COLUMNS,null,null,null,null,
                CITYLIST_SMALLEST + " DESC");
        int cityPop = cs.getColumnIndex(CITYLIST_SMALLEST);
        cs.moveToFirst();
        int largest = cs.getInt(cityPop);
        return nf.format(largest);
    }
}
