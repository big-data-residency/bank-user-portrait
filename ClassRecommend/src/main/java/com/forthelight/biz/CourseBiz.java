package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;

public interface CourseBiz {
	Course findById(int id);

	List<Course> findByCode(Course course);

	List<Course> findByCourseName(Course course);

	List<Course> findByTeacherName(Teacher teacher);

	List<Course> findByLevel(Course course);

	List<Course> findAll();

	int update(Course course);

	int delete(Course course);

	int insert(Course course);
}
