package com.example.leed3.courseregistersystem;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by leed3 on 5/1/2016.
 */
public class JSONSaver {

    //private final String SAVE_URL = "http://www.cs.bc.edu/~signoril/students.json";
    private final String LOCAL_FILE = "students.json";
    private Context context;

    public JSONSaver(Context context) {
        this.context = context;
    }

    public void save(ArrayList<Student> students) {
        try {
            JSONObject save = new JSONObject();
            save.put("cnt",students.size());
            JSONArray studentList = new JSONArray();
            if (students.size() > 0) {
                for (Student s : students) {
                    System.out.println("saving: " + s);
                    JSONObject student = new JSONObject();
                    student.put("name", s.getStudentName());
                    student.put("gradyear", s.getGradYear());
                    student.put("school", s.getSchoolName());
                    student.put("id", s.getId());
                    student.put("courseAmt",s.getNumCourses());
                    JSONArray courses = new JSONArray();
                    if (s.getCourses().size() > 0) {
                        for (Course c : s.getCourses()) {
                            JSONObject course = new JSONObject();
                            course.put("name", c.getCourseName());
                            course.put("id", c.getCourseID());
                            course.put("dataId", c.getDatabaseId());
                            courses.put(course);
                        }
                    }
                    student.put("courses", courses);
                    studentList.put(student);
                }
                save.put("students", studentList);
                FileOutputStream os = context.openFileOutput(LOCAL_FILE,
                        Context.MODE_PRIVATE);
                ObjectOutputStream output = new ObjectOutputStream(os);
                output.writeObject(save.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
