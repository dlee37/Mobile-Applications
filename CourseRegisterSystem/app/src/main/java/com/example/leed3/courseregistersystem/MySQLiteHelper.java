package com.example.leed3.courseregistersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leed3 on 4/23/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper implements Serializable {

    private static final String DATABASE_NAME = "students.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_STUDENTS = "Students";
    public static final String STUDENTS_ID = "students_id";
    private static final String STUDENTS_NAME = "Name";
    private static final String STUDENTS_GRADDYEAR = "GradYear";
    private static final String STUDENTS_SCHOOL = "School";
    private static final String TABLE_NAME_COURSES = "Courses";
    public static final String COURSES_DATA_ID = "courses_id";
    private static final String COURSES_NAME = "CourseName";
    private static final String COURSES_ID = "CourseId";
    private static final String COURSES_STUDENT = "Course_student";
    private static String[] STUDENT_COLUMN_NAMES = {STUDENTS_ID,STUDENTS_NAME,
            STUDENTS_GRADDYEAR};
    private static String[] COURSE_COLUMN_NAMES = {COURSES_DATA_ID,COURSES_NAME,
            COURSES_ID,COURSES_STUDENT};
    private Context context;

    private static final String STUDENT_CREATE = "create table " +
            TABLE_NAME_STUDENTS + "(" + STUDENTS_ID + " integer primary key autoincrement, " +
            STUDENTS_NAME + " text not null, "+ STUDENTS_GRADDYEAR + " text not null, "
            + STUDENTS_SCHOOL + " text not null);";

    // Database creation sql statement
    private static final String COURSES_CREATE = "create table "
            + TABLE_NAME_COURSES + "(" + COURSES_DATA_ID
            + " integer primary key autoincrement, " + COURSES_NAME
            + " text not null, " + COURSES_ID + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(STUDENT_CREATE);
        database.execSQL(COURSES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COURSES);
        onCreate(db);
    }

    public void addStudentToDatabase(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENTS_NAME, student.getStudentName());
        cv.put(STUDENTS_GRADDYEAR, student.getGradYear());
        cv.put(STUDENTS_SCHOOL, student.getSchoolName());
        long id = db.insert(TABLE_NAME_STUDENTS, null, cv);
        student.setId(id);
    }

    public void removeStudentFromDatabase(Student s) {
        long sId = s.getId();
        getWritableDatabase().delete(TABLE_NAME_STUDENTS, STUDENTS_ID + "=" + sId, null);
    }

    public void addCourseToDataBase(Course c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COURSES_NAME,c.getCourseName());
        cv.put(COURSES_ID,c.getCourseID());
        long id = db.insert(TABLE_NAME_COURSES,null,cv);
        c.setDatabaseId(id);
    }

    public void removeCourseFromDataBase(Course c, Student s) {
        long cId = c.getDatabaseId();
        getWritableDatabase().delete(TABLE_NAME_COURSES, COURSES_DATA_ID + "=" + cId, null);
    }

    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}