package com.example.leed3.courseregistersystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Student implements java.io.Serializable, Comparable<Student> {

	public long id;
	public String name;
	public String year;
	public String school;
	public ArrayList<Course> courses;
	
	public Student(String name, String year, String school) {
		this.name = name;
		this.year = year;
		this.school = school;
		courses = new ArrayList<>();
	}
	
	public String getStudentName() {
		return name;
	}
	
	public String getGradYear() {
		return year;
	}
	
	public long getId() {
		return id;
	}
	
	public String toString() {
		return name;
	}
	
	public void setId(long i) {
		id = i;
	}

	public void enrollInCourse(Course c) {
		courses.add(c);
	}

	public void removeCourse(Course c) {
		courses.remove(c);
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setStudentName(String name) {
		this.name = name;
	}

	public void setGradYear(String year) {
		this.year = year;
	}

	public int getNumCourses() {
		return courses.size();
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchoolName() {
		return school;
	}

	@Override
	public int compareTo(Student another) {
		return (this.getStudentName().compareTo(another.getStudentName()));
	}
}