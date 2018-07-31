package com.forthelight.biz.impl;

import java.util.List;

import com.forthelight.dao.MajorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.MajorBiz;
import com.forthelight.domain.Major;

@Service
public class MajorBizImpl implements MajorBiz {
	@Autowired
	private MajorDao majorDao;

	@Override
	public Major findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Major> findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Major findByName(String majorName) {
		return majorDao.findByName(majorName);
	}

}
