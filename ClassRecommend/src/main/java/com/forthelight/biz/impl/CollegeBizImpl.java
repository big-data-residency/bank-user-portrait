package com.forthelight.biz.impl;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.CollegeBiz;
import com.forthelight.dao.CollegeDao;
import com.forthelight.domain.College;
import org.springframework.stereotype.Service;

@Service
public class CollegeBizImpl implements CollegeBiz {
	@Autowired
	private CollegeDao collegeDao;

	@Override
	public College findById(int id) {
		// TODO Auto-generated method stub
		return collegeDao.findById(id);
	}

	@Override
	public College findByName(String collegeName) {
		// TODO Auto-generated method stub
		return collegeDao.findByName(collegeName);
	}

	@Override
	public List<College> findByType(int type) {
		// TODO Auto-generated method stub
		return collegeDao.findByType(type);
	}

	@Override
	public List<College> findAll() {
		// TODO Auto-generated method stub
		return collegeDao.findAll();
	}

	@Override
	public int update(College college) {
		// TODO Auto-generated method stub
		return collegeDao.update(college);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return collegeDao.delete(id);
	}

	@Override
	public int insert(College college) {
		// TODO Auto-generated method stub
		return collegeDao.insert(college);
	}

}
