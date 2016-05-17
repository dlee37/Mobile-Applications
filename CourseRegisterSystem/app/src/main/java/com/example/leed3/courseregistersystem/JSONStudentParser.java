package com.example.leed3.courseregistersystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leed3 on 4/24/2016.
 */
public class JSONStudentParser {

    public static ArrayList<Student> getStudents(JSONObject jObj) throws JSONException {
        ArrayList<Student> students = new ArrayList<>();
        int count = getInt("cnt",jObj);
        JSONArray studentArray = jObj.getJSONArray("students");
        System.out.println("count is: " + count);
        for (int i = 0; i < count; i++) {
            System.out.println("i is: " + i);
            JSONObject JSONStudent = studentArray.getJSONObject(i);
            String name = getString("name", JSONStudent);
            String gradyear = getString("gradyear", JSONStudent);
            String school = getString("school",JSONStudent);
            long id = getLong("id", JSONStudent);
            Student s = new Student(name,gradyear,school);
            System.out.println("Student is: " + s);
            s.setId(id);
            JSONArray courses = JSONStudent.getJSONArray("courses");
            int numCourses = getInt("courseAmt",JSONStudent);
            if (numCourses > 0) {
                for (int j = 0; j < numCourses; j++) {
                    JSONObject course = courses.getJSONObject(j);
                    String courseName = getString("name", course);
                    String courseId = getString("id", course);
                    System.out.println("Course name is: " + courseName);
                    Course c = new Course(courseName, courseId);
                    long cId = getLong("dataId", course);
                    c.setDatabaseId(cId);
                    s.enrollInCourse(c);
                }
            }
            students.add(s);
        }
        System.out.println("Students list is: " + students);
        return students;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }
}
