package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Major;

public interface MajorDao {
	
	Major findById(int id);

	List<Major> findByCollegeId(int collegeId);

	Major findByName(String majorName);

}
