package com.forthelight.biz.impl;

import java.util.List;

import com.forthelight.biz.TeacherBiz;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Teacher;

public class TeacherBizImpl implements TeacherBiz {

	private TeacherDao teacherDao;

	@Override
	public Teacher findById(int id) {
		return teacherDao.findById(id);
	}

	@Override
	public List<Teacher> findByName(Teacher teacher) {
		return teacherDao.findByName(teacher);
	}

	@Override
	public List<Teacher> findAll() {
		return teacherDao.findAll();
	}

	@Override
	public List<Teacher> OrderByLike() {
		return teacherDao.OrderByLike();
	}

	@Override
	public int update(Teacher teacher) {
		return teacherDao.update(teacher);
	}

	@Override
	public int delete(Teacher teacher) {
		return teacherDao.delete(teacher);
	}

	@Override
	public int insert(Teacher teacher) {
		return teacherDao.insert(teacher);
	}

}
