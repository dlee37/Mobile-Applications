package com.example.leed3.courseregistersystem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by leed3 on 4/23/2016.
 */
public class CourseAdapter extends ArrayAdapter<Course> {
    Context context;
    ArrayList<Course> courses;
    ArrayList<Course> selected;
    MySQLiteHelper helper;
    int sort = 0;

    public CourseAdapter(Context context, int resource, ArrayList<Course> courses) {
        super(context,resource,courses);
        this.context = context;
        this.courses = courses;
        selected = new ArrayList<>();
        helper = new MySQLiteHelper(context);
    }

    public View getView(int position, View ConvertView, ViewGroup parent) {
        final int index = position;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        Course c = courses.get(position);
        final View row = inflater.inflate(R.layout.check_list_view, null); //this is what is returned
        TextView tv = (TextView) row.findViewById(R.id.names);
        tv.setText(c.getCourseName());
        row.setClickable(true);
        final CheckBox cb = (CheckBox) row.findViewById(R.id.checkbox);
        cb.setClickable(false);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cb.isChecked()) {
                    cb.setChecked(true);
                    selected.add(courses.get(index));
                } else if (cb.isChecked()) {
                    cb.setChecked(false);
                    selected.remove(courses.get(index));
                }
            }
        });
        return row;
    }

    public void setSort(int i) {
        sort = i;
        sortList();
    }

    public ArrayList<Course> getCourseList() {
        return courses;
    }

    public void addCourse(Course c) {
        courses.add(c);
        helper.addCourseToDataBase(c);
        selected.clear();
        notifyDataSetChanged();
    }

    public void removeCourse(Course c)
    {
        courses.remove(c);
        notifyDataSetChanged();
    }
    public boolean isMultipleCoursesSelected() {
        if (selected.size() > 1) {
            return true;
        }

        return false;
    }

    public void clearAll() {
        courses.clear();
        selected.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Course> getSelectedCourseList() {
        return selected;
    }

    public Course getSelectedCourse() {
        return selected.get(0);
    }

    public void clearSelectedCoursesList() {
        selected.clear();
    }

    public ArrayList<Course> getCoursesList() {
        return courses;
    }

    private void sortList() {
        if (sort == 0) {
            Collections.sort(courses);
        }

        else if (sort == 1) {
            Collections.sort(courses, new Comparator<Course>() {
                @Override
                public int compare(Course lhs, Course rhs) {
                    return lhs.getCourseID().compareTo(rhs.getCourseID());
                }
            });
        }
        selected.clear();
        notifyDataSetChanged();
    }
}
