package com.forthelight.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.TeacherBiz;
import com.forthelight.domain.Course;
import com.forthelight.domain.CourseWithLikeNumber;
import com.forthelight.domain.Teacher;
import com.forthelight.domain.TeacherWithLikeNumber;

@Controller
public class StarterJspController {

	@Autowired
	private TeacherBiz teacherBiz;
	
	@Autowired
	private CourseBiz courseBiz;
	
	

	@RequestMapping("/view/user/starter")
	public String findTop3Techer(HttpServletRequest request) {

		List<Teacher> teachers = teacherBiz.OrderByLike();
		List<TeacherWithLikeNumber> top3TeachersWithlikeNumber = new ArrayList<TeacherWithLikeNumber>();

		for (int i = 0; i < 3; i++) {

			if (i < teachers.size()) {

				Teacher teacher = teachers.get(i);
				System.out.println("topTeachers:" + teacher.getTeacherName());

				TeacherWithLikeNumber teacherWithLikeNumber = new TeacherWithLikeNumber();
				teacherWithLikeNumber.setTeacher(teacher);
				teacherWithLikeNumber.setLikeNumber(teacherBiz.likeNumber(teacher.getId()));
				top3TeachersWithlikeNumber.add(teacherWithLikeNumber);

			}
		}

		
		
		List<Course> courses = courseBiz.orderByLike();
		List<CourseWithLikeNumber> top10CourseWithLikeNumber = new ArrayList<CourseWithLikeNumber>();

		for (int i = 0; i < 10; i++) {

			if (i < courses.size()) {

				Course course = courses.get(i);
				System.out.println("topCourses" + course.getCourseName());
				
				CourseWithLikeNumber courseWithLikeNumber = new CourseWithLikeNumber();
				courseWithLikeNumber.setCourse(course);
				courseWithLikeNumber.setLikeNumber(courseBiz.likeNumber(course.getId()));
				top10CourseWithLikeNumber.add(courseWithLikeNumber);

			}
		}

		request.setAttribute("top3TeacherWithLikeNumbers", top3TeachersWithlikeNumber);
		request.setAttribute("top10CourseWithLikeNumbers", top10CourseWithLikeNumber);
		return "user/starter";

	}
}
