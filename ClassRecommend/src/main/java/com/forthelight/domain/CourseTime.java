package com.forthelight.domain;

import java.util.HashSet;
import java.util.Set;

public class CourseTime {
	private Integer id;
	private int startLesson;
	private int endLesson;
	private int lessonDay;

	private Set<Course> courses = new HashSet<Course>();

	public CourseTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStartLesson() {
		return startLesson;
	}

	public void setStartLesson(int startLesson) {
		this.startLesson = startLesson;
	}

	public int getEndLesson() {
		return endLesson;
	}

	public void setEndLesson(int endLesson) {
		this.endLesson = endLesson;
	}

	public int getLessonDay() {
		return lessonDay;
	}

	public void setLessonDay(int lessonDay) {
		this.lessonDay = lessonDay;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

}
