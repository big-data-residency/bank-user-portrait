package com.forthelight.dao;

import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

//    @Test
//    public void findById() {
//        Student student = studentDao.findById(1);
//        System.out.println("======");
//    }
//
//    @Test
//    public void findByMajorId() {
//        studentDao.findByMajorId(1);
//
//    }
//
//    @Test
//    public void insert() {
//        Student student = new Student();
//        student.setStudentName("TestStudent");
//        studentDao.insert(student);
//
//    }
//
//    @Test
//    public void findByCollegeId() {
//        studentDao.findByCollegeId(1);
//    }
//
//    @Test
//    public void findByStudentNumber() {
//        Student student = studentDao.findByStudentNumber("1611111");
//        List<Course> courses = student.getCourses();
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(student));
//        System.out.println(gson.toJson(courses));
//
//    }
//
//    @Test
//    public void findByName() {
//        Student student = studentDao.findByName("蝙蝠侠");
//        System.out.println(student.getStudentName());
//    }
//    
//    @Test
//	public void Testupdate() {
//		Student student = new Student();
//		student.setId(4);
//		student.setNickName("hhh");
//		int result = studentDao.update(student);
//		System.out.println(result);
//	}
    
    @Test
    public void TestfindAll() {
    	List<Student> students = studentDao.findAll();
    	for(Student student : students) {
    		System.out.println(student.getStudentName());
    	}
    	
    }
    
    @Test
    public void TestfindByKeyword() {
    	List<Student> students = studentDao.findByKeyword("计算机");
    	for(Student student : students) {
    		
    		System.out.println(student.getStudentName());
    	}
    }
    
    @Test
    public void Testdelete() {
    	int result = studentDao.delete(8);
    	System.out.println(result);
    }
}