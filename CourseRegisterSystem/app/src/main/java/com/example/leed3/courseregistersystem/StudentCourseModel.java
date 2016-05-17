//Daniel Lee
//Assignment 9
package com.example.leed3.courseregistersystem;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentCourseModel {

	private Context context;
	private StudentAdapter students;
	private CourseAdapter courses;
	private MySQLiteHelper helper;
	private JSONStudentParser parser;
	private JSONSaver saver;

	public StudentCourseModel(Context context) {
		this.context = context;
		students = new StudentAdapter(context,R.layout.check_list_view,
				new ArrayList<Student>());
		courses = new CourseAdapter(context,R.layout.check_list_view,
				new ArrayList<Course>());
		helper = new MySQLiteHelper(context);
		parser = new JSONStudentParser();
		saver = new JSONSaver(context);
	}

	public void addStudent(Student s) {
		students.addStudent(s);
		helper.addStudentToDatabase(s);
		// System.out.println(students);
	}

	public StudentAdapter getStudentAdapter() {
		return students;
	}

	public void clearSelectedStudents() {
		students.clearSelected();
	}

	public Student getStudent() {
		return students.getSelectedStudent(0);
	}

	public boolean isMultipleStudentsSelected() {
		return students.getSelectedStudents().
				size() > 1;
	}

	public Student getStudent(int i) {
		return students.getStudent(i);
	}

	public List<Student> getSelectedStudents() {
		return students.getSelectedStudents();
	}

	public void deleteDatabase() {
		helper.deleteDatabase();
	}

	public void deleteStudent(Student s) {
		students.deleteStudent(s);
		helper.removeStudentFromDatabase(s);
	}

	public void load() throws IOException, JSONException, ClassNotFoundException {
		JSONObject jsonObject = new StudentsHttpClient().getStudentData();
		ArrayList<Student> list = JSONStudentParser.getStudents(jsonObject);
		for (Student s : list) {
			this.addStudent(s);
		}
		FileInputStream is = context.openFileInput("students.json");
		ObjectInputStream input = new ObjectInputStream(is);
		String json = (String) input.readObject();
		System.out.println(json);
		JSONObject jObj = new JSONObject(json);
		ArrayList<Student> listOfStudents = JSONStudentParser.getStudents(jObj);
		for (Student s : listOfStudents) {
			if (s.getStudentName().equals("Daniel Lee") && s.getGradYear().equals("2017")
					&& s.getSchoolName().equals("Boston College")) {
				continue;
			}

			else {
				this.addStudent(s);
			}
		}
	}

	public void save() {
		saver.save(students.getStudentsList());
	}

	public int indexOf(Student s) {
		return students.indexOf(s);
	}

	public void setStudent(int i, Student s) {
		students.set(i,s);
	}

	public void setSort(int i) {
		students.setSort(i);
	}

}