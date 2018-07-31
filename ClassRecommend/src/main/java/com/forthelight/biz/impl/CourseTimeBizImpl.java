package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.CourseTimeBiz;
import com.forthelight.dao.CourseTimeDao;
import com.forthelight.domain.CourseTime;

@Service
public class CourseTimeBizImpl implements CourseTimeBiz {
	
	@Autowired
	private CourseTimeDao courseTimeDao;

	@Override
	public CourseTime findById(int id) {
		// TODO Auto-generated method stub
		return courseTimeDao.findById(id);
	}

	@Override
	public List<CourseTime> findByClassId(int classId) {
		// TODO Auto-generated method stub
		return courseTimeDao.findByClassId(classId);
	}

}
