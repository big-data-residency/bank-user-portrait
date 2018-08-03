package com.forthelight.dao;

import com.forthelight.domain.File;

import java.util.List;

public interface FileDao {

	File findById(int id);

	List<File> findByStudentId(int studentId);

	List<File> findByCourseId(int courseId);

	int uploadsNumberOfCourse(int courseId);

	int insert(File file);

}
