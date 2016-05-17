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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by leed3 on 4/23/2016.
 */
public class StudentAdapter extends ArrayAdapter<Student> {

    Context context;
    ArrayList<Student> students;
    ArrayList<Student> selectedStudents;
    JSONSaver saver;
    int sort = 0;

    public StudentAdapter(Context context, int resource, ArrayList<Student> students) {
        super(context, R.layout.check_list_view, students);
        this.context = context;
        this.students = students;
        selectedStudents = new ArrayList<>();
        saver = new JSONSaver(context);
    }

    public View getView(int position, View ConvertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final int index = position;
        Student s = students.get(position);
        final View row = inflater.inflate(R.layout.check_list_view, null); //this is what is returned
        TextView tv = (TextView) row.findViewById(R.id.names);
        tv.setText(s.getStudentName());
        final CheckBox cb = (CheckBox) row.findViewById(R.id.checkbox);
        cb.setClickable(false);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(cb.isChecked());
                if (!cb.isChecked()) {
                    cb.setChecked(true);
                    selectedStudents.add(students.get(index));
                } else if (cb.isChecked()) {
                    cb.setChecked(false);
                    selectedStudents.remove(students.get(index));
                }
                //Toast.makeText(context,selectedStudents.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }

    public ArrayList<Student> getStudentsList() {
        return students;
    }

    public void addStudent(Student s) {
        students.add(s);
        sortList();
        notifyDataSetChanged();
    }

    public void deleteStudent(Student s) {
        students.remove(s);
        notifyDataSetChanged();
    }

    public void setSort(int i) {
        sort = i;
        sortList();
    }

    public void addCourse(Student s, Course c) {
        if (!students.contains(s)) {
            Toast.makeText(context,"Student does not exist",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            int index = students.indexOf(s);
            s.removeCourse(c);
            students.set(index,s);
        }
    }

    public Student getStudent(int i) {
        return students.get(i);
    }

    public void set(int i, Student s) {
        students.set(i,s);
        sortList();
        notifyDataSetChanged();
    }

    public Student getStudent() {
        return selectedStudents.get(0);
    }

    public Student getSelectedStudent(int i) {
        return selectedStudents.get(i);
    }

    public ArrayList<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public void clearSelected() {
        selectedStudents.clear();
    }

    public int indexOf(Student s) {
        return students.indexOf(s);
    }

    public void save() {
        saver.save(students);
    }

    public int getNumStudents() {
        return students.size();
    }

    private void sortList() {
        if (sort == 0) {
            Collections.sort(students);
            System.out.println("sorted students is: " + students);
        }

        else if (sort == 1) {
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student lhs, Student rhs) {
                    return lhs.getGradYear().compareTo(rhs.getGradYear());
                }
            });
        }

        else if (sort == 2) {
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student lhs, Student rhs) {
                    return lhs.getSchoolName().compareTo(rhs.getSchoolName());
                }
            });
        }
        selectedStudents.clear();
        notifyDataSetChanged();
    }
}
