package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.forthelight.biz.StudentBiz;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Student;

public class StudentBizImpl implements StudentBiz {
	
	@Autowired
	private StudentDao studentDao;

	@Override
	public Student findById(int id) {
		// TODO Auto-generated method stub
		return studentDao.findById(id);
	}

	@Override
	public List<Student> findByMajorId(int majorId) {
		// TODO Auto-generated method stub
		return studentDao.findByMajorId(majorId);
	}

	@Override
	public int insert(Student student) {
		// TODO Auto-generated method stub
		return studentDao.insert(student);
	}

	@Override
	public List<Student> findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return studentDao.findByCollegeId(collegeId);
	}

}
