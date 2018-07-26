package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.College;


public interface CollegeDao {
	
	College findById(int id);

	College findByName(String collegeName);

	List<College> findByType(int type);

	List<College> findAll();

	int update(College college);

	int delete(int id);

	int insert(College college);
}
