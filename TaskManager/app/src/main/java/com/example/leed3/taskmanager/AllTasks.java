package com.example.leed3.taskmanager;

import java.io.*;
import java.util.*;

/**
 * Created by leed3 on 2/28/2016.
 */
public class AllTasks implements Serializable {

    private static List<ToDo> tasks;
    private static List<ToDo> selectedTasks;

    public AllTasks() {
        tasks = new ArrayList<ToDo>();
        selectedTasks = new ArrayList<ToDo>();
    }

    protected void addTask(ToDo td) {
        tasks.add(td);
    }

    protected void deleteTask(ToDo item) {
        tasks.remove(item);
    }

    protected ToDo getToDoItem(int index) {
        return tasks.get(index);
    }

    protected void set(int i, ToDo task) {
        tasks.set(i,task);
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
}
