package com.forthelight.dao;

import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {

	Student findById(int id);

	List<Student> findByMajorId(int majorId);

	int insert(Student student);

	List<Student> findByCollegeId(int collegeId);

	Student findByStudentNumber(String studentNumber);

	Student findByNickName(String NickName);
	
	List<Student> findByCourseId(int courseId);

	Student findByName(String studentName);
	
	int update (Student student);
	
	List<Student> findAll();
	
	List<Student> findByKeyword(String keyword);
	
	int delete(int studentId);

    int selectCourse(@Param("student") Student student, @Param("course") Course course);

    Integer getSelectId(@Param("student") Student student, @Param("course") Course course);

	Integer selectCourseNumber(int studentId);

	Integer commentCourseNumber(int studentId);
}
