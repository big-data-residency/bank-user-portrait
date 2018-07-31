package com.forthelight.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forthelight.biz.FileBiz;
import com.forthelight.dao.FileDao;
import com.forthelight.domain.File;

@Service
public class FileBizImpl implements FileBiz {
	
	@Autowired
	private FileDao fileDao;

	@Override
	public File findById(int id) {
		// TODO Auto-generated method stub
		return fileDao.findById(id);
	}

	@Override
	public List<File> findByStudentId(int studentId) {
		// TODO Auto-generated method stub
		return fileDao.findByStudentId(studentId);
	}

	@Override
	public List<File> findByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return fileDao.findByCourseId(courseId);
	}

	@Override
	public int uploadsNumberOfCourse(int courseId) {
		// TODO Auto-generated method stub
		return fileDao.uploadsNumberOfCourse(courseId);
	}

}
