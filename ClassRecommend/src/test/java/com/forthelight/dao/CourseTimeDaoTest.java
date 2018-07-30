package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.CourseTime;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)

public class CourseTimeDaoTest {
	@Autowired
	private CourseTimeDao courseTimeDao;

	@Test
	public void findById() {
		CourseTime courseTime = courseTimeDao.findById(1);
		System.out.println(courseTime);
	}

	@Test
	public void findByClassId() {
		List<CourseTime> courseTimes = courseTimeDao.findByClassId(1);
		System.out.println(courseTimes);
	}

}
