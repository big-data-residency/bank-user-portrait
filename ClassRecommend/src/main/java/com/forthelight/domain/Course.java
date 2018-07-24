package com.forthelight.domain;

import java.io.Serializable;
import java.util.logging.Level;

public class Course implements Serializable {
    private Integer id;
    private String courseCode;
    private String courseName;
    private Integer teacherId;
    private Integer studentNumber;
    private Integer startWeek;
    private Integer endWeek;
    private Integer lessonDay;
    private String startLesson;
    private Integer isSingleWeek;
    private Integer credit;
    private Integer examingForm;
    private String level;
    private Integer deleteStatus;

    private College college;
    private Major major;

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

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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

    public Integer getLessonDay() {
        return lessonDay;
    }

    public void setLessonDay(Integer lessonDay) {
        this.lessonDay = lessonDay;
    }

    public String getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(String startLesson) {
        this.startLesson = startLesson;
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

    public Integer getExamingForm() {
        return examingForm;
    }

    public void setExamingForm(Integer examingForm) {
        this.examingForm = examingForm;
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
}
