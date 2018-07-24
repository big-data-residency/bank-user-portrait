package com.forthelight.domain;

public class StudentCommentCourse {
	private Integer id;
	private String comment;
	private int gradeScore;
	private int contentScore;
	private int likeNumber;
	private int deleteStatus;
	
	private Student student;
	private Course course;
	
	public StudentCommentCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getGradeScore() {
		return gradeScore;
	}
	public void setGradeScore(int gradeScore) {
		this.gradeScore = gradeScore;
	}
	public int getContentScore() {
		return contentScore;
	}
	public void setContentScore(int contentScore) {
		this.contentScore = contentScore;
	}
	public int getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

	
}
