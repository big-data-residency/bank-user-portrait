package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Tag;
import com.forthelight.util.RandomGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherDaoTest {
	@Autowired
	private TeacherDao teacherDao;
	
	@Test
	public void TestFindById() {
		Teacher teacher = teacherDao.findById(1);
		System.out.println(teacher);
	}
	
	@Test
	public void TestFindByName() {
		List<Teacher> teachers = teacherDao.findByName("杨巨峰");
		System.out.println(teachers);
	}
	
	@Test
	public void TestFindAll() {
		List<Teacher> teachers = teacherDao.findAll();
		System.out.println(teachers);
	}
	
	@Test
	public void TestFindByCollegeId() {
		List<Teacher> teachers = teacherDao.findByCollegeId(1);
		System.out.println(teachers);
	}
	
	@Test
	public void TestOrderByLike() {
		List<Teacher> teachers = teacherDao.OrderByLike();
		System.out.println("hot===="+teachers.get(0).getTeacherName());
	}
	
/*	@Test
	public void TestInsert() {
		Teacher teacher = new Teacher();
		teacher.setTeacherName("TestTeacher");
		int result = teacherDao.insert(teacher);
		System.out.println(result);
	}*/
	
	@Test
	public void TestUpdate() {
		List<Teacher> teacherList = teacherDao.findAll();
		for(Teacher teacher: teacherList){
			teacher.setTeacherPortrait(RandomGenerator.getAvatarPath());
			teacherDao.update(teacher);
		}
	}
	
	@Test
	public void TestInsert() {
		int result = teacherDao.delete(7);
		System.out.println(result);
	}
	
	@Test
	public void TestLikeNumber() {
		int likeNumber = teacherDao.likeNumber(1);
		System.out.println("==========="+likeNumber);
	}
	
	@Test
	public void TestCollege() {
		String collegeName = teacherDao.college(1);
		System.out.println(collegeName);
	}
	
	@Test
	public void TestTagList() {
		List<Tag> tags = teacherDao.tagList(1);
		System.out.println(tags);
	}

	@Test
	public void findByStudentIdAndCourseName(){
		List<Teacher> teachers = teacherDao.findByStudentIdAndCourseName(2,"数据库");
		System.out.print(teachers);
	}

	@Test
	public void  findByStudentId(){
		List<Teacher> teachers = teacherDao.findByStudentId(2);
		System.out.print(teachers);
	}

}
