package com.forthelight.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.FileBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.biz.TagBiz;
import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
public class CourseController {

	@Autowired
	private CourseBiz courseBiz;

	@Autowired
	private FileBiz fileBiz;

	@Autowired
	private StudentCommentCourseBiz studentCommentCourseBiz;

	@Autowired
	private TagBiz tagBiz;

	@Autowired
	private StudentBiz studentBiz;
	
	
	
    // ### classrecommend.html 显示课程基本信息、评论显示界面
	@RequestMapping(value = "/courseInfo", produces = "text/json; charset=utf-8")
	@ResponseBody
	public String CourseInfo(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/json;charset:UTF-8");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

		Map<String, Object> res = new HashMap<>();

		String courseIdStr = request.getParameter("courseId");
		int courseId = Integer.parseInt(courseIdStr);
		
		boolean success = false;
		
		if(courseIdStr != null) {
			success = true;
		}

		Course course = courseBiz.findById(courseId);

		// ----------------- 左边栏数据 --------------------
		String courseName = course.getCourseName();
		String teacherName = course.getTeacher().getTeacherName();
		int likeNumber = courseBiz.likeNumber(courseId);
		int uploadsNumber = fileBiz.uploadsNumberOfCourse(courseId);
		int commentNumber = studentCommentCourseBiz.commentNumberOfCourse(courseId);

		// -------------------- 第一个标签数据 --------------------------
		Map<String, Integer> TagsNumber = new HashMap<>();
		List<Tag> tags = courseBiz.tagList(courseId);
		for (Tag tag : tags) {

			TagsNumber.put(tag.getTagName(), courseBiz.oneTagNumber(tag.getId(), courseId));

		}

		// ------------------- 第二个标签栏数据 --------------------------
		List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);

		// ----------------------- 传输左边栏、第一二个标签Json数据 ----------------------------------
		
		Map<String, Object> data = new HashMap<>();

		data.put("courseName", courseName);
		data.put("teacherName", teacherName);
		data.put("likeNumber", likeNumber);
		data.put("uploadsNumber", uploadsNumber);
		data.put("commentNumber", commentNumber);
		data.put("TagsNumber", TagsNumber);
		data.put("commentNumber", commentNumber);
		data.put("comments", comments);
		
		res.put("data", data);
		res.put("success", success);

		return gson.toJson(res);

	}
	
	
    // ### classrecommend.html 课程评论界面
	@RequestMapping(value = "/courseCommnet",  method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String courseComment(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/json;charset:UTF-8");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

		Map<String, Object> res = new HashMap<>();

		String comment = request.getParameter("comments");

		String[] tagsIdStr = request.getParameterValues("tags");

		String studentIdStr = request.getParameter("studentId");
		int studentId = Integer.parseInt(studentIdStr);

		String courseIdStr = request.getParameter("courseId");
		int courseId = Integer.parseInt(courseIdStr);

		String bearSoreStr = request.getParameter("bearSore");
		int bearSore = Integer.parseInt(bearSoreStr);

		String interestingScoreStr = request.getParameter("interestingScore");
		int interestingScore = Integer.parseInt(interestingScoreStr);

		String easyScoreStr = request.getParameter("easyScore");
		int easyScore = Integer.parseInt(easyScoreStr);

		String knowledgeScoreStr = request.getParameter("knowledgeScore");
		int knowledgeScore = Integer.parseInt(knowledgeScoreStr);

		String gradeScoreStr = request.getParameter("gradeScore");
		int gradeScore = Integer.parseInt(gradeScoreStr);

		String anonymousStr = request.getParameter("anonymous");
		int anonymous = Integer.parseInt(anonymousStr);

		int contentScore = interestingScore + easyScore + knowledgeScore + bearSore;

		List<Tag> tags = new ArrayList<Tag>();

		for (String tagIdStr : tagsIdStr) {

			int tagId = Integer.parseInt(tagIdStr);
			tags.add(tagBiz.findById(tagId));
		}

		Student student = studentBiz.findById(studentId);
		Course course = courseBiz.findById(courseId);
		Timestamp commentTime = new Timestamp(System.currentTimeMillis());
		int selectId = studentCommentCourseBiz.getSelectIdByStudentIdAndCourseId(studentId, courseId);

		StudentCommentCourse studentCommentCourse = new StudentCommentCourse();

		studentCommentCourse.setAnonymous(anonymous);
		studentCommentCourse.setBearScore(bearSore);
		studentCommentCourse.setComment(comment);
		studentCommentCourse.setCommentTime(commentTime);
		studentCommentCourse.setContentScore(contentScore);
		studentCommentCourse.setCourse(course);
		studentCommentCourse.setEasyScore(easyScore);
		studentCommentCourse.setGradeScore(gradeScore);
		studentCommentCourse.setInterestingScore(interestingScore);
		studentCommentCourse.setKnowledgeScore(knowledgeScore);
		studentCommentCourse.setStudent(student);
		studentCommentCourse.setTags(tags);
		studentCommentCourse.setSelectId(selectId);

		int result = studentCommentCourseBiz.insert(studentCommentCourse);
		String insertResult = "评论失败，请先选择此课程!";
		if (result > 0)
			insertResult = "评论成功,感谢您提出的宝贵意见";
		
		
		if (insertResult.equals("评论成功,感谢您提出的宝贵意见")) {
			res.put("success", "true");
			res.put("data", insertResult);
		} else {
			res.put("success", "false");
			res.put("data", insertResult);
		}
		
		return gson.toJson(res);

	}


	@RequestMapping(value = "/searchCourse", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String studentBasicInfo(String courseKeyword, HttpServletResponse response) {

		response.setContentType("text/json;charset:UTF-8");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		Map<String, Object> res = new HashMap<>();

		List<Course> courses;
		if(courseKeyword == ""){
			courses = courseBiz.findAll();
		}else{
			courses = courseBiz.selectByKeyword(courseKeyword);
		}

		boolean success = false;
		if(courses.size() > 0){
			success = true;
		}

		Map<String, Object> data = new HashMap<>();
		data.put("courses",courses);

		res.put("data",data);
		res.put("success",success);

		return gson.toJson(res);
	}


	@RequestMapping(value = "/rCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String recommendCourse(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/json;charset:UTF-8");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

		System.out.print(request);

		Map<String, Object> res = new HashMap<>();


		boolean success = true;

		res.put("success", success);

		return gson.toJson(res);
	}

}
