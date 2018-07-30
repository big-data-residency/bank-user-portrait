package com.forthelight.domain;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

@SuppressWarnings("serial")
public class Major implements Serializable {
	private Integer id;
	private String majorName;
	private Integer deleteStatus;

	private transient College college;

	private transient List<Student> students = new ArrayList<Student>();
	private transient List<Course> courses = new ArrayList<Course>();

	public Major() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
