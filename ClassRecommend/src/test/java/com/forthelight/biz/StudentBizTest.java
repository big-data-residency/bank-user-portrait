package com.forthelight.biz;

import com.forthelight.dao.CourseDao;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.CourseTime;
import com.forthelight.domain.Student;
import com.forthelight.util.RandomGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentBizTest {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseDao courseDao;

    @Test
    public void loginValidate() {
        Student student = studentDao.findByStudentNumber("Batman");
        if (student != null && student.getPassword() != null){
            if (student.getPassword().equals("batman")){
                System.out.println("登录成功");
            } else {
                System.out.println("密码错误");
            }
        } else {
            System.out.println("用户不存在");
        }
    }

    @Test
    public void selectCourse() {
        Random random = new Random();
        List<Student> studentList = studentDao.findAll();
        for (Student student: studentList) {
//             每一个用户选numberOfClass 节课
            int numberOfClass = random.nextInt(6);
            for (int j = 0; j < numberOfClass; ) {
                List<Course> studentCourses = student.getCourses();
                List<Course> courseList = courseDao.findAll();
                Course course_to_insert = courseList.get(random.nextInt(courseList.size()));
                // 验证课程时间
                boolean flag = true;
                for (Course course : studentCourses) {
                    List<CourseTime> courseTimes = course.getCourseTimes();
                    List<CourseTime> course_to_insert_times = course_to_insert.getCourseTimes();
                    if (flag) {
                        for (CourseTime time : courseTimes) {
                            if (flag) {
                                for (CourseTime time_to_insert : course_to_insert_times) {
                                    if (time.getLessonDay() == time_to_insert.getLessonDay() && time_to_insert.getStartLesson() > time.getStartLesson() && time_to_insert.getStartLesson() < time.getEndLesson() || time_to_insert.getEndLesson() > time.getStartLesson() && time_to_insert.getEndLesson() < time.getEndLesson()) {
                                        flag = false;
                                        System.out.println("false");
                                    }
                                }
                            }
                        }
                    }
                }
                if (flag && course_to_insert.getStudentNumber() != null && (course_to_insert.getStudents() == null || course_to_insert.getStudents().size() < course_to_insert.getStudentNumber())) {
                    studentDao.selectCourse(student, course_to_insert);
                    j++;
                    System.out.println("success");
                }

            }
        }
    }

    @Test
    public void update() {
        List<Student> studentList = studentDao.findAll();
        for(Student student: studentList){
            student.setStudentPortrait(RandomGenerator.getAvatarPath());
            studentDao.update(student);
        }
    }
}