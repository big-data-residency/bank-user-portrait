package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.StudentCommentCourse;

public interface StudentCommentCourseDao {

	StudentCommentCourse findById(int id);

	List<StudentCommentCourse> findByTagId(int tagId);

	List<StudentCommentCourse> findByCourseId(int courseId);

	List<StudentCommentCourse> orderByLikeNumber(int teacherId);

	int commentNumberOfCourse(int courseId);

	Integer update(StudentCommentCourse studentCommentCourse);
	
	int getSelectIdByStudentIdAndCourseId(int studentId, int courseId);
	
	int insert(StudentCommentCourse studentCommentCourse);
	
	Integer getScoreByGrade(String studentNumber , int courseId);
	
	int numberOfGetScoreByGrade(String studentNumber , int courseId);
	
	int getContentScoreByGrade(String studentNumber);
	
	int numberOfGetContentScoreByGrade(String studentNumber);
	
	List<StudentCommentCourse> findByStudentId(int studentId);

	List<StudentCommentCourse> findAll();

	List<StudentCommentCourse> findByReplyTo(Integer replyTo);
	
}
