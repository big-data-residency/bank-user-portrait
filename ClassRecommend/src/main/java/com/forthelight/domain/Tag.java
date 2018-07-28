package com.forthelight.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Tag implements Serializable {
	private Integer id;
	private String tagName;
	private int deleteStatus;

	private transient List<StudentCommentCourse> comments = new ArrayList<StudentCommentCourse>();

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

	public List<StudentCommentCourse> getComments() {
		return comments;
	}

	public void setComments(List<StudentCommentCourse> comments) {
		this.comments = comments;
	}

}
