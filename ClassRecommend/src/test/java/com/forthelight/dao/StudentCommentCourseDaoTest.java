package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.StudentCommentCourse;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentCommentCourseDaoTest {
	
	@Autowired
	private StudentCommentCourseDao studentCommentCourseDao;
	
	@Test
	public void TestFindById() {
		StudentCommentCourse studentCommentCourse = studentCommentCourseDao.findById(1);
		System.out.println(studentCommentCourse);
	}
	
	@Test
	public void TestFindByTagId() {
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseDao.findByTagId(1);
		System.out.println(studentCommentCourses);
	}
	
	@Test
	public void TestFindByCourseId() {
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseDao.findByCourseId(3);
		System.out.println(studentCommentCourses);

	}
	
	@Test
	public void TestorderByLikeNumber() {
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseDao.orderByLikeNumber(1);
		System.out.println(studentCommentCourses);

	}
}
