package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.forthelight.biz.CourseBiz;
import com.forthelight.dao.CourseDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;
import org.springframework.stereotype.Service;

@Service("courseBiz")
public class CourseBizImpl implements CourseBiz {
	
	@Autowired
	private CourseDao courseDao;

	@Override
	public Course findById(int id) {
		// TODO Auto-generated method stub
		return courseDao.findById(id);
	}

	@Override
	public Course findByCode(String courseCode) {
		// TODO Auto-generated method stub
		return courseDao.findByCode(courseCode);
	}

	@Override
	public List<Course> findByCourseName(String courseName) {
		// TODO Auto-generated method stub
		return courseDao.findByCourseName(courseName);
	}

	@Override
	public List<Course> findByTeacherName(String teacherName) {
		return courseDao.findByTeacherName(teacherName);
	}

	@Override
	public List<Course> findByLevel(String leve) {
		// TODO Auto-generated method stub
		return courseDao.findByLevel(leve);
	}

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		return courseDao.findAll();
	}

	@Override
	public int update(Course course) {
		// TODO Auto-generated method stub
		return courseDao.update(course);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return courseDao.delete(id);
	}

	@Override
	public int insert(Course course) {
		// TODO Auto-generated method stub
		return courseDao.insert(course);
	}

	@Override
	public List<Course> findByTeacherId(int teacherId) {
		// TODO Auto-generated method stub
		return courseDao.findByTeacherId(teacherId);
	}

	@Override
	public List<Course> findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return courseDao.findByCollegeId(collegeId);
	}

	@Override
	public List<Course> findByCourseTimeId(int courseTimeId) {
		// TODO Auto-generated method stub
		return courseDao.findByCourseTimeId(courseTimeId);
	}

	@Override
	public List<Course> findByMajorId(int majorId) {
		// TODO Auto-generated method stub
		return courseDao.findByMajorId(majorId);
	}

	@Override
	public List<Course> findByStudentId(int studentId) {
		// TODO Auto-generated method stub
		return courseDao.findByStudentId(studentId);
	}

}
