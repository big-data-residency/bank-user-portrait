package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.StudentBiz;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Student;

@Service("studentBiz")
public class StudentBizImpl implements StudentBiz {
	@Autowired
	StudentDao studentDao;

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
	public List<Student> findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return studentDao.findByCollegeId(collegeId);
	}

    @Override
    public String loginValidate(String studentNumber, String password) {
        Student student = studentDao.findByStudentNumber(studentNumber);
        if (student != null && student.getPassword() != null){
            if (student.getPassword().equals(password)){
                return "登录成功";
            } else {
                return "密码错误";
            }
        } else {
            return "用户不存在";
        }
    }

    @Override
    public Student findByStudentNumber(String studentNumber) {
        return studentDao.findByStudentNumber(studentNumber);
    }

    @Override
    public int insert(Student student) {
        return studentDao.insert(student);
    }

	@Override
	public List<Student> findByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return studentDao.findByCourseId(courseId);
	}
}
