package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.forthelight.biz.TeacherBiz;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Teacher;
import org.springframework.stereotype.Service;


@Service("teacherBiz")
public class TeacherBizImpl implements TeacherBiz {
    @Autowired
	private TeacherDao teacherDao;

    @Override
	public Teacher findById(int id) {
		return teacherDao.findById(id);
	}

	@Override
	public List<Teacher> findByName(String teacherName) {
		// TODO Auto-generated method stub
		return teacherDao.findByName(teacherName);
	}

	@Override
	public List<Teacher> findAll() {
		// TODO Auto-generated method stub
		return teacherDao.findAll();
	}

	@Override
	public List<Teacher> OrderByLike() {
		// TODO Auto-generated method stub
		return teacherDao.OrderByLike();
	}

	@Override
	public int update(Teacher teacher) {
		// TODO Auto-generated method stub
		return teacherDao.update(teacher);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return teacherDao.delete(id);
	}

	@Override
	public int insert(Teacher teacher) {
		// TODO Auto-generated method stub
		return teacherDao.insert(teacher);
	}

	@Override
	public List<Teacher> findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return teacherDao.findByCollegeId(collegeId);
	}

}
