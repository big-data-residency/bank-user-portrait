package com.forthelight.dao;

import com.forthelight.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    public void findById() {
        Student student = studentDao.findById(1);
        System.out.println("======");
    }

    @Test
    public void findByMajorId() {
        studentDao.findByMajorId(1);

    }

    @Test
    public void insert() {
        Student student = new Student();
        student.setStudentName("TestStudent");
        studentDao.insert(student);

    }

    @Test
    public void findByCollegeId() {
        studentDao.findByCollegeId(1);
    }
}