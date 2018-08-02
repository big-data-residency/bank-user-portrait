package com.forthelight.biz.impl;

import com.forthelight.biz.StudentBiz;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Student loginValidate(String NickName) {
        Student student = studentDao.findByNickName(NickName);
        return student;
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

    @Override
    public Student findByName(String studentName) {
        return studentDao.findByName(studentName);
    }

	@Override
	public int update(Student student) {
		// TODO Auto-generated method stub
		return studentDao.update(student);
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return studentDao.findAll();
	}

	@Override
	public List<Student> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return studentDao.findByKeyword(keyword);
	}

	@Override
	public int delete(int studentId) {
		return studentDao.delete(studentId);
	}

	@Override
	public int selectCourse(Student student, Course course) {
		return studentDao.selectCourse(student, course);
	}

	@Override
	public int getSelectId(Student student, Course course) {
		return studentDao.getSelectId(student, course);
	}
}
