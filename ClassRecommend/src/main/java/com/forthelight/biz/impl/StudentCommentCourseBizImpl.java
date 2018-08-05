package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.domain.StudentCommentCourse;

@Service
public class StudentCommentCourseBizImpl implements StudentCommentCourseBiz {
	
	@Autowired
	private StudentCommentCourseDao studentCommentCourseDao;

	@Override
	public StudentCommentCourse findById(int id) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.findById(id);
	}

	@Override
	public List<StudentCommentCourse> findByTagId(int tagId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.findByTagId(tagId);
	}

	@Override
	public List<StudentCommentCourse> findByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.findByCourseId(courseId);
	}

	@Override
	public List<StudentCommentCourse> orderByLikeNumber(int teacherId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.orderByLikeNumber(teacherId);
	}

	@Override
	public int commentNumberOfCourse(int courseId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.commentNumberOfCourse(courseId);
	}

	@Override
	public Integer getSelectIdByStudentIdAndCourseId(int studentId, int courseId) {
		return studentCommentCourseDao.getSelectIdByStudentIdAndCourseId(studentId, courseId);
	}

	@Override
	public int insert(StudentCommentCourse studentCommentCourse) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.insert(studentCommentCourse);
	}

	@Override
	public Integer update(StudentCommentCourse studentCommentCourse) {
		return studentCommentCourseDao.update(studentCommentCourse);
	}

	@Override
	public Integer getScoreByGrade(String studentNumber, int courseId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.getScoreByGrade(studentNumber, courseId);
	}

	@Override
	public int numberOfGetScoreByGrade(String studentNumber, int courseId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.numberOfGetScoreByGrade(studentNumber, courseId);
	}

	@Override
	public int getContentScoreByGrade(String studentNumber) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.getContentScoreByGrade(studentNumber);
	}

	@Override
	public int numberOfGetContentScoreByGrade(String studentNumber) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.numberOfGetContentScoreByGrade(studentNumber);
	}

	@Override
	public List<StudentCommentCourse> findByStudentId(int studentId) {
		// TODO Auto-generated method stub
		return studentCommentCourseDao.findByStudentId(studentId);
	}

}
