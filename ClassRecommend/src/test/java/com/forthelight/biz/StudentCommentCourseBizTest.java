package com.forthelight.biz;

import com.forthelight.dao.CourseDao;
import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.util.RandomGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.*;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentCommentCourseBizTest {
    @Autowired
    StudentCommentCourseDao studentCommentCourseDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentDao studentDao;

    @Test
    public void insert() {
        Random random = new Random();
        List<Student> studentList = studentDao.findAll();
        for (Student student : studentList) {
            List<Course> courseList = student.getCourses();
            if (courseList != null && courseList.size() != 0) {
                for (int i = 0; i < courseList.size(); i++) {
                    StudentCommentCourse studentCommentCourse = new StudentCommentCourse();
                    Course course = courseList.get(random.nextInt(courseList.size()));
                    studentCommentCourse.setCourse(course);
                    studentCommentCourse.setStudent(student);
                    studentCommentCourse.setSelectId(studentDao.getSelectId(student, course));
                    studentCommentCourse.setComment(RandomGenerator.getComment());
                    studentCommentCourse.setAnonymous(random.nextInt(2));
                    studentCommentCourse.setLikeNumber(random.nextInt(300));

                    long timeStamp = System.currentTimeMillis() - random.nextInt(9999999) + 10000;
                    while (timeStamp <= 0) {
                        timeStamp = System.currentTimeMillis() - random.nextInt(999999999) + 10000;
                    }
                    studentCommentCourse.setCommentTime(new Timestamp(timeStamp));
                    studentCommentCourse.setBearScore(random.nextInt(5) + 1);
                    studentCommentCourse.setGradeScore(random.nextInt(5) + 1);
                    studentCommentCourse.setContentScore(random.nextInt(5) + 1);
                    studentCommentCourse.setEasyScore(random.nextInt(5) + 1);
                    studentCommentCourse.setInterestingScore(random.nextInt(5) + 1);
                    studentCommentCourse.setKnowledgeScore(random.nextInt(5) + 1);

                    studentCommentCourseDao.insert(studentCommentCourse);
                    System.out.println("success");
                }
            }
        }
    }

    @Test
    public void update() {
        Random random = new Random();
        List<StudentCommentCourse> commentList = studentCommentCourseDao.findAll();

        for(StudentCommentCourse comment: commentList){
            comment.setGradeScore(random.nextInt(40)+ 60);
            studentCommentCourseDao.update(comment);
        }
    }

    @Test
    public void findById() {
        StudentCommentCourse comment = studentCommentCourseDao.findById(2737);
        List<StudentCommentCourse> subComments = comment.getSubComment();
        System.out.println("====");
    }
}