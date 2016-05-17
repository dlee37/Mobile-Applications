package com.example.leed3.taskmanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leed3 on 2/28/2016.
 */
public class AllTasks implements Serializable {

    private MySQLiteHelper helper;
    private static List<ToDo> tasks;
    private static List<ToDo> selectedTasks;
    private SQLiteDatabase database;
    private int id = 1;
    private static int index = 0;
    private static final String TABLE_NAME = "ToDo";
    private static final String COLUMN_TASK = "Tasks";
    private final static String COLUMN_DESCRIPTION = "Description";
    private final static String COLUMN_DATE = "Date";
    private final static String[] COLUMNS = {COLUMN_TASK,COLUMN_DESCRIPTION,COLUMN_DATE};
    private Context context;

    public AllTasks(Context context) {
        helper = new MySQLiteHelper(context);
        tasks = new ArrayList<ToDo>();
        this.context = context;
        selectedTasks = new ArrayList<ToDo>();
        try {
            database = helper.getWritableDatabase();
            performQuery();
        }
        catch (Exception ex)
        {
            Toast.makeText(context, "failed to load data", Toast.LENGTH_SHORT).show();
        }
    }

    public void close() {
        helper.close();
    }


    protected void addTask(ToDo td) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK,td.getTitle());
        values.put(COLUMN_DESCRIPTION, td.getDescription());
        values.put(COLUMN_DATE, td.getDate());
        long taskId = database.insert(TABLE_NAME, null, values);
        System.out.println("Id is: " + Long.toString(taskId));
        td.setId(taskId);
        td.setIndex(index);
        tasks.add(td);
        incrementIndex();
        updateTasks();
    }

    /* protected void saveContents(ToDo td) {
        database.beginTransaction();
        try {
            String submit = " insert into todolist(task,description,date) " +
                    "values ( '" + td.getTitle() + "','" + td.getDescription() +
                    "','" + td.getDate() + "');";
            database.execSQL(submit);
            database.setTransactionSuccessful();
        }

        catch (Exception e) {
            Toast.makeText(context, "failed to enter data", Toast.LENGTH_LONG).show();
        }

        finally {
            database.endTransaction();
        }
    } */

    protected void deleteTask(ToDo item) {
        long idnum = item.getId();
        System.out.println("Comment deleted with id: " + idnum);
        tasks.remove(item);
        decrementIndex();
        database.delete(TABLE_NAME, helper.COLUMN_ID + " = " + idnum, null);
        updateTasks();
    }

    protected ToDo getToDoItem(int index) {
        return tasks.get(index);
    }

    protected void set(int i, ToDo task) {
        tasks.set(i,task);
        ContentValues cv = new ContentValues();
        Toast.makeText(context,task.getTitle(),Toast.LENGTH_SHORT).show();
        cv.put(COLUMN_TASK,task.getTitle());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_DATE,task.getDate());
        long idnum = task.getId();
        database.update(TABLE_NAME, cv, helper.COLUMN_ID + "=" + idnum, null);
        updateTasks();
    }

    protected List<ToDo> getList() {
        return tasks;
    }

    protected List<ToDo> getSelectedList() {
        return selectedTasks;
    }

    protected void addSelectedTask(ToDo item) {
        selectedTasks.add(item);
    }

    protected void deleteSelectedTask(ToDo item) {
        selectedTasks.remove(item);
    }

    protected void clearSelectedTasks() {
        selectedTasks.clear();
    }

    protected String getPrintedList() {
        return tasks.toString();
    }

    protected static void setToDoList(List<ToDo> t) {
        tasks = t;
    }

    private void performQuery() {
        Cursor cs = database.query(TABLE_NAME,COLUMNS,null,null,null,null,COLUMN_DATE + " ASC");
        int entryId = cs.getColumnIndex("Tasks");
        int descriptionId = cs.getColumnIndex("Description");
        int dateId = cs.getColumnIndex("Date");
        cs.moveToFirst();
        while(cs.isAfterLast() == false) {
            String title = cs.getString(entryId);
            String description = cs.getString(descriptionId);
            String date = cs.getString(dateId);
            ToDo task = new ToDo(title,description,date);
            task.setId(id);
            task.setIndex(index);
            incrementIndex();
            id++;
            tasks.add(task);
            cs.moveToNext();
        }
        cs.close();
        updateTasks();
    }

    public void incrementIndex() {
        index++;
    }

    public void decrementIndex() {
        index--;
    }

    private void updateTasks() {
        int indexcount = 0;
        for (ToDo td : tasks) {
            td.setIndex(indexcount);
            indexcount++;
        }
    }
}
