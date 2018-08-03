package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;
import org.apache.ibatis.annotations.Param;

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
	
	List<Tag> tagList(int teacherId);

	List<Teacher> findByStudentIdAndCourseName(int studentId,String courseName);

	List<Teacher> findByStudentId(@Param("studentId") int studentId);
}
