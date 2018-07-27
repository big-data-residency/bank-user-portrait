package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.StudentCommentCourse;

public interface StudentCommentCourseBiz {

	StudentCommentCourse findById(int id);

	List<StudentCommentCourse> findByTagId(int tagId);

	List<StudentCommentCourse> findByCourseId(int courseId);
	
	List<StudentCommentCourse> orderByLikeNumber(int teacherId);

}
