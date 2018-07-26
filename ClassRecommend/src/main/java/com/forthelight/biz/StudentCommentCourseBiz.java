package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.StudentCommentCourse;

public interface StudentCommentCourseBiz {
	
	StudentCommentCourse findById(int id);

	List<StudentCommentCourse> findByTagId(int tagId);

}
