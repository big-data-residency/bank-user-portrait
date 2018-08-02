package com.forthelight.biz;

import com.forthelight.domain.Course;
import com.forthelight.domain.Student;

import java.util.List;

public interface StudentBiz {

	Student findById(int id);

	List<Student> findByMajorId(int majorId);

	int insert(Student student);

	List<Student> findByCollegeId(int collegeId);

	Student loginValidate(String NickName);

	Student findByStudentNumber(String studentNumber);

	List<Student> findByCourseId(int courseId);

	Student findByName(String studentName);

	int update(Student student);

	List<Student> findAll();
	
	List<Student> findByKeyword(String keyword);
	
	int delete(int studentId);

    int selectCourse(Student student, Course course);

    int getSelectId(Student student, Course course);
}
