package com.forthelight.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;

@SuppressWarnings("serial")
public class Student implements Serializable {

	private Integer id;
	private String studentName;
	private String gender;
	private String nickName;
	private String password;
	private String studentNumber;
	private int grade;
	private int privilege;
	private int deleteStatus;
	private String studentPortrait;

	private transient College college;
	private transient Major major;
	private transient List<Course> courses = new ArrayList<Course>();
	private transient List<File> files = new ArrayList<File>();

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public String getStudentPortrait() {
		return studentPortrait;
	}

	public void setStudentPortrait(String studentPortrait) {
		this.studentPortrait = studentPortrait;
	}

}
