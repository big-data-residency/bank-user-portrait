package com.forthelight.domain;

import java.util.HashSet;
import java.util.Set;

public class Tag {
	private Integer id;
	private String tagName;
	private int deleteStatus;
	
	private Set<StudentCommentCourse> comments = new HashSet<StudentCommentCourse>();
}
