package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.File;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FileDaoTest {
	@Autowired
	private FileDao fileDao;
	
	@Test
	public void TestFindById() {
		File file = fileDao.findById(1);
		System.out.println(file);
	}
	
	@Test
	public void TestFindByStudentId() {
		List<File> files = fileDao.findByStudentId(1);
		System.out.println(files);
	}
	
	@Test
	public void TestFindByCourseId() {
		List<File> files = fileDao.findByCourseId(1);
		System.out.println(files);
	}
}
