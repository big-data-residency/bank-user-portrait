package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.Major;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MajorDaoTest {

	@Autowired
	private MajorDao majorDao;
	
	@Test
	public void TestFingById() {
		Major major = majorDao.findById(1);
		System.out.println(major);
	}
	
	@Test
	public void TestFingByCollegeId() {
		List<Major> majors = majorDao.findByCollegeId(1);
		System.out.println(majors);
	}
}
