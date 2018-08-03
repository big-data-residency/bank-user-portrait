package com.forthelight.dao;

import com.forthelight.domain.Course;
import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    public void testFindByAll(){
    	
        List<Course> courses = courseDao.findAll();
        System.out.println(courses);
    }
    
    @Test
    public void testFindById(){
    	
        Course course = courseDao.findById(1);
        System.out.println(course);
    }
    
    @Test
    public void testFindByCode(){
    	
        Course course = courseDao.findByCode("s");
        System.out.println(course);
    }
    
    @Test
    public void testFindByCourseName(){
    	
        List<Course> courses = courseDao.findByCourseName("计算方法");
        System.out.println(courses);
    }
    
    @Test
    public void testFindByTeacherName(){
    	Teacher teacher = new Teacher();
    	teacher.setTeacherName("刘晓光");
        List<Course> courses = courseDao.findByTeacherName(teacher.getTeacherName());
        System.out.println(courses);
    }
    
    @Test
    public void testFindByLevel(){
    	
        List<Course> courses = courseDao.findByLevel("A");
        System.out.println(courses);
    }
    
    @Test
    public void testUpdate(){
    	Course course = new Course();
    	course.setId(2);
    	course.setLevel("B");
        int result = courseDao.update(course);
        System.out.println(result);
    }
    
    @Test
    public void testdelete(){
    	
        int result = courseDao.delete(2);
        System.out.println(result);
    }
    
    @Test
    public void testFindByTeacherId(){
    
        List<Course> courses = courseDao.findByTeacherId(1);
        System.out.println(courses);
    }
    
    @Test
    public void testFindByCollegeId(){
    
        List<Course> courses = courseDao.findByCollegeId(1);
        System.out.println(courses);
    }
    
    @Test
    public void testFindByCourseId(){
    
        List<Course> courses = courseDao.findByCourseTimeId(1);
        System.out.println(courses);
    }
    
    @Test
    public void testFindByMajorId(){
    
        List<Course> courses = courseDao.findByMajorId(1);
        System.out.println(courses);
    }
    
    @Test
    public void testFindByStudentId(){
    
        List<Course> courses = courseDao.findByStudentId(1);
        System.out.println(courses);
    }
    
    @Test
    public void testOrderByLike() {
    	List<Course> courses = courseDao.orderByLike();
    	System.out.println(courses);
    }
    
    @Test
    public void testLikeNumber() {
    	int likeNumber = courseDao.likeNumber(3);
    	System.out.println(likeNumber);
    }
    
    @Test
    public void testOneTagNumber() {
    	int number = courseDao.oneTagNumber(2, 1);
    	System.out.println(number);
    }
    
    @Test
    public void testTagList() {
    	List<Tag> tags = courseDao.tagList(3);
    	System.out.println(tags);
    }

    @Test
    public void  selectByKeyword(){
        List<Course> courses = courseDao.selectByKeyword("数据");
        System.out.println(courses.get(0).getCourseName());
    }
}
