//Daniel Lee
//Assignment 9

package com.example.leed3.courseregistersystem;

public class Course implements java.io.Serializable, Comparable<Course> {

	private String id;
	private String name;
	private long databaseId;
	
	public Course(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getCourseID() {
		return id;
	}
	
	public String getCourseName() {
		return name;
	}
	
	public String toString() {
		return name;
	}

	public void setDatabaseId(long id) {
		databaseId = id;
	}

	public long getDatabaseId() {
		return databaseId;
	}

	@Override
	public int compareTo(Course another) {
		return this.getCourseName().compareTo(another.getCourseName());
	}
}
