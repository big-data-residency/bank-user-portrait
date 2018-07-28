package com.forthelight.dao;

import java.util.List;
import com.forthelight.domain.Teacher;

public interface TeacherDao {

	Teacher findById(int id);

	List<Teacher> findByName(String teacherName);

	List<Teacher> findAll();

	List<Teacher> OrderByLike();

	int update(Teacher teacher);

	int delete(int id);

	int insert(Teacher teacher);

	List<Teacher> findByCollegeId(int collegeId);
	
	int likeNumber(int id);
	
	String college(int collegeId);
	
	List<String> tagList(int teacherId);
	
}
