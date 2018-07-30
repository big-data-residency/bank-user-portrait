package com.forthelight.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseWithLikeNumber implements Serializable {

	Course course;
	int likeNumber;

	public CourseWithLikeNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

}
