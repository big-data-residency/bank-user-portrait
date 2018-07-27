package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.File;

public interface FileBiz {
	File findById(int id);

	List<File> findByStudentId(int studentId);

	List<File> findByCourseId(int courseId);

	int uploadsNumberOfCourse(int courseId);
}
