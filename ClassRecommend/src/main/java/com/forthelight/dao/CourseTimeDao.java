package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.CourseTime;

public interface CourseTimeDao {

	CourseTime findById(int id);

	List<CourseTime> findByClassId(int classId);

}
