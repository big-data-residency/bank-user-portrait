package com.forthelight.biz;

import com.forthelight.dao.CollegeDao;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;
import com.forthelight.util.RandomGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherBizTest {
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    CollegeDao collegeDao;

    @Test
    public void findById() {
        Teacher teacher = teacherDao.findById(5);
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        Gson gson = gsonBuilder.create();
        List<Course> courses = teacher.getCourses();
        Teacher temp = new Teacher();
        String JsonTeacher = gson.toJson(teacher);
        temp = gson.fromJson(JsonTeacher, Teacher.class);
        System.out.println(gson.toJson(teacher));
        System.out.println(gson.toJson(courses));
        System.out.println("===");
    }

    @Test
    public void insert() {
        for (int i = 0; i < 500; i++) {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(RandomGenerator.getName());
            teacher.setGender(RandomGenerator.getSex());
            teacher.setTelPhone(RandomGenerator.getPhone());
            teacher.setEmail(RandomGenerator.getEmail(6, 11));
            teacher.setOfficeAddress(RandomGenerator.getAddress());
            teacher.setCollege(collegeDao.findById(RandomGenerator.random.nextInt(7) + 1));
            teacher.setLevel(RandomGenerator.getLevel());

            teacherDao.insert(teacher);
            System.out.println(i);
        }
    }


}