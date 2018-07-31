package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.forthelight.domain.StudentCommentCourse;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentCommentCourseDaoTest {
	
	@Autowired
	private StudentCommentCourseDao studentCommentCourseDao;
	
	@Test
	public void TestFindById() {
		StudentCommentCourse studentCommentCourse = studentCommentCourseDao.findById(1);
		System.out.println(studentCommentCourse.getCommentTime());
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
	
	@Test
	public void TestgetSelectIdBystudentIdAndCourseId() {
		int selectId = studentCommentCourseDao.getSelectIdByStudentIdAndCourseId(2, 3);
		System.out.println(selectId);
	}
	
	@Test
	public void Testinsert() {
		
		StudentCommentCourse studentCommentCourse = new StudentCommentCourse();
		studentCommentCourse.setComment("优秀");
		Student student = new Student();
		student.setId(1);
		Course course = new Course();
		course.setId(1);
		studentCommentCourse.setSelectId(studentCommentCourseDao.getSelectIdByStudentIdAndCourseId(1, 1));
		studentCommentCourse.setStudent(student);
		studentCommentCourse.setCourse(course);
		int result = studentCommentCourseDao.insert(studentCommentCourse);
		System.out.println(result);
	}
	
	@Test
	public void TestgetScoreByGrade() {
		int grade = studentCommentCourseDao.getScoreByGrade("16",3);
		System.out.println(grade);
	}
	
	@Test
	public void TestgetContentScoreByGrade() {
		int contentScore = studentCommentCourseDao.getContentScoreByGrade("16");
		System.out.println(contentScore);
	}
	
	@Test
	public void TestfindByStudentId() {
		
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseDao.findByStudentId(2);
		System.out.println(studentCommentCourses);
		
	}
}
