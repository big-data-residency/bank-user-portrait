package com.forthelight.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.domain.StudentCommentCourse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonArrayTest {

	@Autowired
	private StudentCommentCourseBiz studentCommentCourseBiz;
	
	@Test
	public void testjsonArray() {
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseBiz.orderByLikeNumber(1) ;
		
		JsonArray commentses = new JsonArray();
		for(StudentCommentCourse  studentCommentCourse : studentCommentCourses ) {
			JsonObject comment = new JsonObject();
			
		}
		
	}
}
