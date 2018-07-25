package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;

public interface TeacherDao {
	Teacher findById(int id);

	List<Teacher> findByName(String name);
	
	List<Course> courses(String name);

	int update(Teacher teacher);

	int delete(int id);

	int insert(Teacher teacher);
}
