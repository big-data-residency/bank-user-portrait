package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.Student;

public interface StudentBiz {

	Student findById(int id);

	List<Student> findByMajorId(int majorId);

	int insert(Student student);

	List<Student> findByCollegeId(int collegeId);
	String loginValidate(String studentNumber, String password);
	Student findByStudentNumber(String studentNumber);

}