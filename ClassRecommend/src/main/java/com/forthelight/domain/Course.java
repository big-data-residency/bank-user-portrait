package com.forthelight.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Course implements Serializable {
	private Integer id;
	private String courseCode;
	private String courseName;
	private Integer studentNumber;
	private Integer startWeek;
	private Integer endWeek;
	private Integer isSingleWeek;
	private Integer credit;
	private Integer examiningForm;
	private String level;
	private Integer deleteStatus;
	private int hasMidExam;
	private int finalExamWeight;
	private int midExamWeight;
	private int passingCourse;

	private College college;
	private Major major;
	private Teacher teacher;
	private Set<CourseTime> courseTimes = new HashSet<CourseTime>();

	public Course() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Integer getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	public Integer getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	public Integer getIsSingleWeek() {
		return isSingleWeek;
	}

	public void setIsSingleWeek(Integer isSingleWeek) {
		this.isSingleWeek = isSingleWeek;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getExaminingForm() {
		return examiningForm;
	}

	public void setExaminingForm(Integer examiningForm) {
		this.examiningForm = examiningForm;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getHasMidExam() {
		return hasMidExam;
	}

	public void setHasMidExam(int hasMidExam) {
		this.hasMidExam = hasMidExam;
	}

	public int getFinalExamWeight() {
		return finalExamWeight;
	}

	public void setFinalExamWeight(int finalExamWeight) {
		this.finalExamWeight = finalExamWeight;
	}

	public int getMidExamWeight() {
		return midExamWeight;
	}

	public void setMidExamWeight(int midExamWeight) {
		this.midExamWeight = midExamWeight;
	}

	public int getPassingCourse() {
		return passingCourse;
	}

	public void setPassingCourse(int passingCourse) {
		this.passingCourse = passingCourse;
	}

	public Set<CourseTime> getCourseTimes() {
		return courseTimes;
	}

	public void setCourseTimes(Set<CourseTime> courseTimes) {
		this.courseTimes = courseTimes;
	}

}
