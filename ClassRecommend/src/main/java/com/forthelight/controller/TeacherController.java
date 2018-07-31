package com.forthelight.controller;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.biz.TeacherBiz;
import com.forthelight.domain.College;
import com.forthelight.domain.Course;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TeacherController {

    @Autowired
    private TeacherBiz teacherBiz;
    
    @Autowired
    private CourseBiz courseBiz;
    
    @Autowired
    private StudentCommentCourseBiz studentCommentCourseBiz;
    

  
    @RequestMapping(value= {"/teacherInfo"} , method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getTeacherInfo(String teacherIdStr, HttpServletResponse response) {
    	
    	response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        System.out.println(teacherIdStr);
        int teacherId = Integer.parseInt(teacherIdStr);
        
		boolean success = false;
		
		if(teacherIdStr != null) {
			success = true;
		}
        
        
        Map<String, Object> res = new HashMap<>();
        
        // ----------------- 左边栏数据 ------------
        Teacher teacher = teacherBiz.findById(teacherId);
        if(teacher == null) {
        	
        	success = false;
        }
		College college = teacher.getCollege();
		
		// --------------- 第一个标签栏数据 -------------------
		List<Tag> tags = teacherBiz.tagList(teacherId);
		Map<String, Object> keywords = new HashMap<>();
		List<Course> courses = courseBiz.findByTeacherId(teacherId);
		for (Tag tag : tags) {

			int tagId = tag.getId();
			int tagNumber = 0;

			for (Course course : courses) {

				tagNumber = tagNumber + courseBiz.oneTagNumber(tagId, course.getId());
			}

			keywords.put(tag.getTagName(), tagNumber);
		}
		
		// --------------- 第二个标签栏数据 ---------------------
		
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseBiz.orderByLikeNumber(teacherId) ;
		JsonArray comments = new JsonArray();
		
		for(StudentCommentCourse studentCommentCourse : studentCommentCourses) {
			
			JsonObject comment = new JsonObject();
			comment.addProperty("nickName",studentCommentCourse.getStudent().getNickName());
			String commentTime = studentCommentCourse.getCommentTime().toString();
			comment.addProperty("commentTime", commentTime);
			comment.addProperty("likeNumber", studentCommentCourse.getLikeNumber());
			comment.addProperty("commentContent", studentCommentCourse.getComment());
			
			comments.add(comment);
			
		}

		
		// --------------- 第三个标签栏数据 ----------------------
		
		int allCoursesBearScore = 0;
		int allCoursesInterestingScore = 0;
		int allCoursesEasyScore = 0;
		int allCoursesKnowledgeScore = 0;
		
		int gradeScore08 = 0;
		int gradeScore09 = 0;
		int gradeScore10 = 0;
		int gradeScore11 = 0;
		int gradeScore12 = 0;
		int gradeScore13 = 0;
		int gradeScore14 = 0;
		int gradeScore15 = 0;
		int gradeScore16 = 0;
		int gradeScore17 = 0;
		
		int gradeNumber08 = 0;
		int gradeNumber09 = 0;
		int gradeNumber10 = 0;
		int gradeNumber11 = 0;
		int gradeNumber12 = 0;
		int gradeNumber13 = 0;
		int gradeNumber14 = 0;
		int gradeNumber15 = 0;
		int gradeNumber16 = 0;
		int gradeNumber17 = 0;
		
		
		for(StudentCommentCourse studentCommentCourse : studentCommentCourses) {
			
			int courseId = studentCommentCourse.getCourse().getId();
			allCoursesBearScore = allCoursesBearScore + studentCommentCourse.getBearScore();
			allCoursesInterestingScore = allCoursesInterestingScore + studentCommentCourse.getInterestingScore();
			allCoursesEasyScore = allCoursesEasyScore + studentCommentCourse.getEasyScore();
			allCoursesKnowledgeScore = allCoursesKnowledgeScore + studentCommentCourse.getKnowledgeScore();
			
			gradeScore08 = gradeScore08 + studentCommentCourseBiz.getScoreByGrade("08", courseId);
			gradeScore09 = gradeScore09 + studentCommentCourseBiz.getScoreByGrade("09", courseId);
			gradeScore10 = gradeScore10 + studentCommentCourseBiz.getScoreByGrade("10", courseId);
			gradeScore11 = gradeScore11 + studentCommentCourseBiz.getScoreByGrade("11", courseId);
			gradeScore12 = gradeScore12 + studentCommentCourseBiz.getScoreByGrade("12", courseId);
			gradeScore13 = gradeScore13 + studentCommentCourseBiz.getScoreByGrade("13", courseId);
			gradeScore14 = gradeScore14 + studentCommentCourseBiz.getScoreByGrade("14", courseId);
			gradeScore15 = gradeScore15 + studentCommentCourseBiz.getScoreByGrade("15", courseId);
			gradeScore16 = gradeScore16 + studentCommentCourseBiz.getScoreByGrade("16", courseId);
			gradeScore17 = gradeScore17 + studentCommentCourseBiz.getScoreByGrade("17", courseId);
			
			gradeNumber08 = gradeNumber08 + studentCommentCourseBiz.numberOfGetScoreByGrade("08", courseId);
			gradeNumber09 = gradeNumber09 + studentCommentCourseBiz.numberOfGetScoreByGrade("09", courseId);
			gradeNumber10 = gradeNumber10 + studentCommentCourseBiz.numberOfGetScoreByGrade("10", courseId);
			gradeNumber11 = gradeNumber11 + studentCommentCourseBiz.numberOfGetScoreByGrade("11", courseId);
			gradeNumber12 = gradeNumber12 + studentCommentCourseBiz.numberOfGetScoreByGrade("12", courseId);
			gradeNumber13 = gradeNumber13 + studentCommentCourseBiz.numberOfGetScoreByGrade("13", courseId);
			gradeNumber14 = gradeNumber14 + studentCommentCourseBiz.numberOfGetScoreByGrade("14", courseId);
			gradeNumber15 = gradeNumber15 + studentCommentCourseBiz.numberOfGetScoreByGrade("15", courseId);
			gradeNumber16 = gradeNumber16 + studentCommentCourseBiz.numberOfGetScoreByGrade("16", courseId);
			gradeNumber17 = gradeNumber17 + studentCommentCourseBiz.numberOfGetScoreByGrade("17", courseId);
			
		}
		
		JsonArray tagsPercent = new JsonArray();
		
		JsonObject TagsWithColor1 = new JsonObject();
		TagsWithColor1.addProperty("label", "手下留情");
		TagsWithColor1.addProperty("data",allCoursesBearScore);
		TagsWithColor1.addProperty("color", "#3c8dbc");
		
		JsonObject TagsWithColor2 = new JsonObject();
		TagsWithColor2.addProperty("label", "课堂有趣");
		TagsWithColor2.addProperty("data",allCoursesInterestingScore);
		TagsWithColor2.addProperty("color", "#0073b7");
		
		JsonObject TagsWithColor3 = new JsonObject();
		TagsWithColor3.addProperty("label", "划水程度");
		TagsWithColor3.addProperty("data",allCoursesEasyScore);
		TagsWithColor3.addProperty("color", "#00c0ef");
		
		JsonObject TagsWithColor4 = new JsonObject();
		TagsWithColor4.addProperty("label", "干货满满");
		TagsWithColor4.addProperty("data",allCoursesKnowledgeScore);
		TagsWithColor4.addProperty("color", "#B2DFEE");
		
		tagsPercent.add(TagsWithColor1);
		tagsPercent.add(TagsWithColor2);
		tagsPercent.add(TagsWithColor3);
		tagsPercent.add(TagsWithColor4);
		
		
		
		
		JsonArray preExamScore = new JsonArray();
		
		JsonObject ave08 = new JsonObject();
		ave08.addProperty("y", "2008");
		ave08.addProperty("item1", gradeScore08/gradeNumber08);
		
		JsonObject ave09 = new JsonObject();
		ave09.addProperty("y", "2009");
		ave09.addProperty("item1", gradeScore09/gradeNumber09);
		
		JsonObject ave10 = new JsonObject();
		ave10.addProperty("y", "2010");
		ave10.addProperty("item1", gradeScore10/gradeNumber10);
		
		JsonObject ave11 = new JsonObject();
		ave11.addProperty("y", "2011");
		ave11.addProperty("item1", gradeScore11/gradeNumber11);
		
		JsonObject ave12 = new JsonObject();
		ave12.addProperty("y", "2012");
		ave12.addProperty("item1", gradeScore12/gradeNumber12);
		
		JsonObject ave13 = new JsonObject();
		ave13.addProperty("y", "2013");
		ave13.addProperty("item1", gradeScore13/gradeNumber13);
		
		JsonObject ave14 = new JsonObject();
		ave14.addProperty("y", "2014");
		ave14.addProperty("item1", gradeScore14/gradeNumber14);
		
		JsonObject ave15 = new JsonObject();
		ave15.addProperty("y", "2015");
		ave15.addProperty("item1", gradeScore15/gradeNumber15);
		
		JsonObject ave16 = new JsonObject();
		ave16.addProperty("y", "2016");
		ave16.addProperty("item1", gradeScore16/gradeNumber16);
		
		JsonObject ave17 = new JsonObject();
		ave17.addProperty("y", "2017");
		ave17.addProperty("item1", gradeScore17/gradeNumber17);
		
		preExamScore.add(ave08);
		preExamScore.add(ave09);
		preExamScore.add(ave10);
		preExamScore.add(ave11);
		preExamScore.add(ave12);
		preExamScore.add(ave13);
		preExamScore.add(ave14);
		preExamScore.add(ave15);
		preExamScore.add(ave16);
		preExamScore.add(ave17);
		
		
		
		int contentScore08 = studentCommentCourseBiz.getContentScoreByGrade("08");
		int contentScore09 = studentCommentCourseBiz.getContentScoreByGrade("09");
		int contentScore10 = studentCommentCourseBiz.getContentScoreByGrade("10");
		int contentScore11 = studentCommentCourseBiz.getContentScoreByGrade("11");
		int contentScore12 = studentCommentCourseBiz.getContentScoreByGrade("12");
		int contentScore13 = studentCommentCourseBiz.getContentScoreByGrade("13");
		int contentScore14 = studentCommentCourseBiz.getContentScoreByGrade("14");
		int contentScore15 = studentCommentCourseBiz.getContentScoreByGrade("15");
		int contentScore16 = studentCommentCourseBiz.getContentScoreByGrade("16");
		int contentScore17 = studentCommentCourseBiz.getContentScoreByGrade("17");
		
		int contentScoreNumber08 = studentCommentCourseBiz.getContentScoreByGrade("08");
		int contentScoreNumber09 = studentCommentCourseBiz.getContentScoreByGrade("09");
		int contentScoreNumber10 = studentCommentCourseBiz.getContentScoreByGrade("10");
		int contentScoreNumber11 = studentCommentCourseBiz.getContentScoreByGrade("11");
		int contentScoreNumber12 = studentCommentCourseBiz.getContentScoreByGrade("12");
		int contentScoreNumber13 = studentCommentCourseBiz.getContentScoreByGrade("13");
		int contentScoreNumber14 = studentCommentCourseBiz.getContentScoreByGrade("14");
		int contentScoreNumber15 = studentCommentCourseBiz.getContentScoreByGrade("15");
		int contentScoreNumber16 = studentCommentCourseBiz.getContentScoreByGrade("16");
		int contentScoreNumber17 = studentCommentCourseBiz.getContentScoreByGrade("17");
		
		JsonArray preContentScore = new JsonArray();
		
		JsonObject content08 = new JsonObject();
		content08.addProperty("y","2008");
		content08.addProperty("a", contentScore08/contentScoreNumber08);
		
		JsonObject content09 = new JsonObject();
		content09.addProperty("y","2009");
		content09.addProperty("a", contentScore09/contentScoreNumber09);
		
		JsonObject content10 = new JsonObject();
		content10.addProperty("y","2010");
		content10.addProperty("a", contentScore10/contentScoreNumber10);
		
		JsonObject content11 = new JsonObject();
		content11.addProperty("y","2011");
		content11.addProperty("a", contentScore11/contentScoreNumber11);
		
		JsonObject content12 = new JsonObject();
		content12.addProperty("y","2012");
		content12.addProperty("a", contentScore12/contentScoreNumber12);
		
		JsonObject content13 = new JsonObject();
		content13.addProperty("y","2013");
		content13.addProperty("a", contentScore13/contentScoreNumber13);
		
		JsonObject content14 = new JsonObject();
		content14.addProperty("y","2014");
		content14.addProperty("a", contentScore14/contentScoreNumber14);
		
		JsonObject content15 = new JsonObject();
		content15.addProperty("y","2015");
		content15.addProperty("a", contentScore15/contentScoreNumber15);
		
		JsonObject content16 = new JsonObject();
		content16.addProperty("y","2016");
		content16.addProperty("a", contentScore16/contentScoreNumber16);
		
		JsonObject content17 = new JsonObject();
		content17.addProperty("y","2017");
		content17.addProperty("a", contentScore17/contentScoreNumber17);
		
		preContentScore.add(content08);
		preContentScore.add(content09);
		preContentScore.add(content10);
		preContentScore.add(content11);
		preContentScore.add(content12);
		preContentScore.add(content13);
		preContentScore.add(content14);
		preContentScore.add(content15);
		preContentScore.add(content16);
		preContentScore.add(content17);
		

		Map<String, Object> data = new HashMap<>();
		
		data.put("teacher", teacher);
		data.put("college", college);
		data.put("keywords", keywords);
		data.put("courses",courses);
		data.put("comments", comments);
		data.put("preExamScore", preExamScore);
		data.put("preContentScore", preContentScore);
		data.put("tagsPercent", tagsPercent);
		data.put("tags", tags);
		
		res.put("data", data);
		res.put("success", success);
        
        return gson.toJson(res);
    }
    
}
