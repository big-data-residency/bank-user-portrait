package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.StudentCommentCourse;

public interface StudentCommentCourseDao {

	StudentCommentCourse findById(int id);

	List<StudentCommentCourse> findByTagId(int tagId);
}
