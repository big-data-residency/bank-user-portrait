package com.forthelight.biz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.biz.impl.TeacherBizImpl;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Teacher;



@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTeacherBiz {
	
	private TeacherBiz teacherBiz = new TeacherBizImpl();
	
	@Test
	public void testFindById() {
		Teacher teacher = teacherBiz.findById(1);
		System.out.println(teacher.getTeacherName());
		System.out.println("hello");
	}

}
