package com.forthelight.domain;

import java.util.HashSet;
import java.util.Set;

public class Tag {
	private Integer id;
	private String tagName;
	private int deleteStatus;
	
	private Set<StudentCommentCourse> comments = new HashSet<StudentCommentCourse>();
	
	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Set<StudentCommentCourse> getComments() {
		return comments;
	}

	public void setComments(Set<StudentCommentCourse> comments) {
		this.comments = comments;
	}
	
	
}
