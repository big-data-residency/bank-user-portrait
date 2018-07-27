package com.forthelight.biz;

import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherBizTest {
    @Autowired
    TeacherDao teacherDao;

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
}