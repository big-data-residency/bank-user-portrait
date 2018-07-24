package com.forthelight.biz.impl;

import java.util.List;

import com.forthelight.biz.CourseBiz;
import com.forthelight.dao.CourseDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;

public class CourseBizImpl implements CourseBiz {
	private CourseDao courseDao ;

	@Override
	public Course findById(int id) {
		return courseDao.findById(id);
	}

	@Override
	public List<Course> findByCode(Course course) {
		return courseDao.findByCode(course);
	}

	@Override
	public List<Course> findByCourseName(Course course) {
		return courseDao.findByCourseName(course);
	}

	@Override
	public List<Course> findByTeacherName(Teacher teacher) {
		return courseDao.findByTeacherName(teacher);
	}

	@Override
	public List<Course> findByLevel(Course course) {
		return courseDao.findByLevel(course);
	}

	@Override
	public List<Course> findAll() {
		return courseDao.findAll();
	}

	@Override
	public int update(Course course) {
		return courseDao.update(course);
	}

	@Override
	public int delete(Course course) {
		return courseDao.delete(course);
	}

	@Override
	public int insert(Course course) {
		return courseDao.insert(course);
	}

}
