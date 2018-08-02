package com.forthelight.biz;

import com.forthelight.dao.CourseDao;
import com.forthelight.dao.MajorDao;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.CourseTime;
import com.forthelight.domain.Major;
import com.forthelight.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseBizTest {
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    MajorDao majorDao;
    @Autowired
    CourseDao courseDao;

    @Test
    public void insert() {
    }

    @Test
    public void update() {
        Random random = new Random();
        String[] level = "A B C D E".split(" ");
        for (int i = 1; i <= 647; i++) {
            Course course = courseDao.findById(i);
            course.setTeacher(teacherDao.findById(random.nextInt(253) + 1));
            course.setIsSingleWeek(random.nextInt(10) == 0 ? 1 : 0);
            course.setCredit((random.nextInt(10) + 1) / 2);
            course.setExaminingForm(random.nextInt(4));
            Major major = majorDao.findById(random.nextInt(22) + 2);
            course.setMajor(major);
            course.setCollege(major.getCollege());
            course.setLevel(level[random.nextInt(level.length)]);
            course.setHasMidExam(random.nextInt(2));
            course.setMidExamWeight(random.nextInt(5) * 10);
            course.setFinalExamWeight(random.nextInt(7) * 10);

            courseDao.update(course);
            System.out.println(i);
        }

    }

    @Test
    public void updateTeacher() {
        Random random = new Random();
        for (int i = 8; i <= 456; i++) {
            Course course = courseDao.findById(i);
            if (course.getTeacher() == null) {
                Teacher teacher = teacherDao.findById(random.nextInt(253) + 1);
                if (teacher != null) {
                    course.setTeacher(teacher);
                }

            }
        }

    }

    @Test
    public void setTime() {
        Random random = new Random();
        for (int i = 1; i <= 647; i++) {
            Course course = courseDao.findById(i);
            int number = random.nextInt(10) > 3 ? 2 : 1;
            for (int j = 0; j < number; ) {
                int startTime = random.nextInt(6) + 1;
                int endTime = startTime + random.nextInt(3) + 2;
                int lessonDay = random.nextInt(5) + 1;
                if ((startTime <= 4 && endTime <= 4) || (startTime >= 5 && endTime >= 5 && endTime <= 8)) {
                    if (j == 1) {
                        course = courseDao.findById(i);
                        CourseTime time = course.getCourseTimes().get(0);
                        if (lessonDay == time.getLessonDay() && (startTime > time.getStartLesson() && startTime < time.getEndLesson()) || endTime > time.getStartLesson() && endTime < time.getEndLesson()) {
                            courseDao.setTime(course, lessonDay, startTime, endTime);
                            System.out.println(i);
                            j++;
                        }
                    } else {
                        courseDao.setTime(course, lessonDay, startTime, endTime);
                        System.out.println(i);
                        j++;
                    }
                }
            }
        }
    }
}