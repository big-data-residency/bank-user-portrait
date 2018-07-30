package com.forthelight.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.biz.TagBiz;
import com.forthelight.biz.TeacherBiz;
import com.forthelight.domain.College;
import com.forthelight.domain.Course;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Teacher;
import com.google.gson.JsonObject;

@Controller
public class Profile_templateJspController {

	@Autowired
	private TeacherBiz teacherBiz;

	@Autowired
	private CourseBiz courseBiz;

	@Autowired
	private TagBiz tagBiz;

	@Autowired
	private StudentCommentCourseBiz studentCommentCourseBiz;

	@RequestMapping("/view/temp/profile_template")
	public String teacherInfo(String id, HttpServletRequest request) {

		// ------------- 教师信息传输 -----------------

		int teacherId = Integer.parseInt(id);
		Teacher teacher = teacherBiz.findById(teacherId);
		College college = teacher.getCollege();
		//List<String> tags = teacherBiz.tagList(teacherId);

		List<Course> courses = courseBiz.findByTeacherId(teacherId);
		System.out.println("courses===" + courses);

		request.setAttribute("teacher", teacher);
		request.setAttribute("college", college);
		//request.setAttribute("tags", tags);
		request.setAttribute("courses", courses);

		// ------------- 教师标签云信息传输 -----------------

		JsonObject TagsNumber = new JsonObject();

		/*for (String tag : tags) {

			int tagId = tagBiz.findByName(tag).getId();
			int TagNumber = 0;

			for (Course course : courses) {

				TagNumber = TagNumber + courseBiz.oneTagNumber(tagId, course.getId());
			}

			TagsNumber.addProperty(tag, TagNumber);
		}

		request.setAttribute("TagsNumber", TagsNumber);
*/
		// --------------- 评论信息传输 --------------------

		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseBiz.orderByLikeNumber(teacherId) ;

		request.setAttribute("comments", studentCommentCourses );

		return "/temp/profile_template";
	}

}
 
