package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.Major;

public interface MajorBiz {

	Major findById(int id);

	List<Major> findByCollegeId(int collegeId);

	Major findByName(String majorName);
}
