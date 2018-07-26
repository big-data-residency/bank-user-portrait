package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.domain.StudentCommentCourse;

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

}