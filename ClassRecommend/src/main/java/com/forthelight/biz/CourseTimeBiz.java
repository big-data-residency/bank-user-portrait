package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.CourseTime;

public interface CourseTimeBiz {

	CourseTime findById(int id);

	List<CourseTime> findByClassId(int classId);
}
