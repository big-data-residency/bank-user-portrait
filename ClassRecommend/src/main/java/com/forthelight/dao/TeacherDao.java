package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;

public interface TeacherDao {

	Teacher findById(int id);

	List<Teacher> findByName(Teacher teacher);

	List<Teacher> findAll();

	List<Teacher> OrderByLike();

	int update(Teacher teacher);

	int delete(Teacher teacher);

	int insert(Teacher teacher);
}
