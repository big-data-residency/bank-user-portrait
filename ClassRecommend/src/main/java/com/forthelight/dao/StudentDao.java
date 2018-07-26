package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Student;

public interface StudentDao {

	Student findById(int id);

	List<Student> findByMajorId(int majorId);

	int insert(Student student);

	List<Student> findByCollegeId(int collegeId);

	Student findByStudentNumber(String studentNumber);
}
