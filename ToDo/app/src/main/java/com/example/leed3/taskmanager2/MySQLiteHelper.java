package com.example.leed3.taskmanager2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.*;
import android.util.Log;
import java.io.Serializable;

/**
 * Created by leed3 on 3/21/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper implements Serializable {

    private static final String TABLE_NAME = "ToDo";
    public static final String COLUMN_ID = "_id";
    private static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 2;
    private static final String COLUMN_TASK = "Tasks";
    private final static String COLUMN_DESCRIPTION = "Description";
    private final static String COLUMN_DATE = "Date";
    private Context context;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_TASK
            + " text not null, " + COLUMN_DESCRIPTION + " text not null, " +
            COLUMN_DATE + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
