package com.forthelight.domain;

public class TeacherWithLikeNumber {

	private transient Teacher teacher;
	private int likeNumber;

	public TeacherWithLikeNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

}
