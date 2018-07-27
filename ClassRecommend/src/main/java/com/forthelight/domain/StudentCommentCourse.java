package com.forthelight.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;

@SuppressWarnings("serial")
public class StudentCommentCourse implements Serializable {
	private Integer id;
	private String comment;
	private int gradeScore;
	private int contentScore;
	private int likeNumber;
	private int Anonymous;
	private int deleteStatus;
	private Timestamp commentTime;

	private Student student;
	private Course course;
	private List<Tag> tags = new ArrayList<Tag>();

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

	public int getAnonymous() {
		return Anonymous;
	}

	public void setAnonymous(int anonymous) {
		Anonymous = anonymous;
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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	@Override
	public String toString() {
		return "StudentCommentCourse [id=" + id + ", comment=" + comment + ", gradeScore=" + gradeScore
				+ ", contentScore=" + contentScore + ", likeNumber=" + likeNumber + ", Anonymous=" + Anonymous
				+ ", deleteStatus=" + deleteStatus + ", commentTime=" + commentTime + ", student=" + student
				+ ", course=" + course + ", tags=" + tags + "]";
	}
	
	

}
