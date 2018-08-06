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

    @Test
    public void insert(){
        for(int i =0;i<1000;i++){
            ;
        }
    }

    @Test
    public void selectCourseNumber(){
        int result = studentDao.selectCourseNumber(10);
        System.out.print(result);
    }

    @Test
    public void commentCourseNumber(){
        int result = studentDao.commentCourseNumber(10);
        System.out.print(result);
    }

}