package com.forthelight.controller;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.biz.TeacherBiz;
import com.forthelight.biz.impl.TeacherBizImpl;
import com.forthelight.dao.TeacherDao;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		College college = teacher.getCollege();
		
		// --------------- 第一个标签栏数据 -------------------
		List<Tag> tags = teacherBiz.tagList(teacherId);
		List<Course> courses = courseBiz.findByTeacherId(teacherId);
		
		// --------------- 第二个标签栏数据 ---------------------
		List<StudentCommentCourse> studentCommentCourses = studentCommentCourseBiz.orderByLikeNumber(teacherId) ;
		
		JsonArray comments = new JsonArray();

		
		// --------------- 第三个标签栏数据 ----------------------
		
//		int allCoursesBearScore = 0;
//		int allCoursesInterestingScore = 0;
//		int allCoursesEasyScore = 0;
//		int allCoursesKnowledgeScore = 0;
//		
//		int gradeScore08 = 0;
//		int gradeScore09 = 0;
//		int gradeScore10 = 0;
//		int gradeScore11 = 0;
//		int gradeScore12 = 0;
//		int gradeScore13 = 0;
//		int gradeScore14 = 0;
//		int gradeScore15 = 0;
//		int gradeScore16 = 0;
//		int gradeScore17 = 0;
//		
//		int gradeNumber08 = 0;
//		int gradeNumber09 = 0;
//		int gradeNumber10 = 0;
//		int gradeNumber11 = 0;
//		int gradeNumber12 = 0;
//		int gradeNumber13 = 0;
//		int gradeNumber14 = 0;
//		int gradeNumber15 = 0;
//		int gradeNumber16 = 0;
//		int gradeNumber17 = 0;
//		
//		
//		for(StudentCommentCourse studentCommentCourse : studentCommentCourses) {
//			
//			int courseId = studentCommentCourse.getCourse().getId();
//			allCoursesBearScore = allCoursesBearScore + studentCommentCourse.getBearScore();
//			allCoursesInterestingScore = allCoursesInterestingScore + studentCommentCourse.getInterestingScore();
//			allCoursesEasyScore = allCoursesEasyScore + studentCommentCourse.getEasyScore();
//			allCoursesKnowledgeScore = allCoursesKnowledgeScore + studentCommentCourse.getKnowledgeScore();
//			
//			gradeScore08 = gradeScore08 + studentCommentCourseBiz.getScoreByGrade("08", courseId);
//			gradeScore09 = gradeScore09 + studentCommentCourseBiz.getScoreByGrade("09", courseId);
//			gradeScore10 = gradeScore10 + studentCommentCourseBiz.getScoreByGrade("10", courseId);
//			gradeScore11 = gradeScore11 + studentCommentCourseBiz.getScoreByGrade("11", courseId);
//			gradeScore12 = gradeScore12 + studentCommentCourseBiz.getScoreByGrade("12", courseId);
//			gradeScore13 = gradeScore13 + studentCommentCourseBiz.getScoreByGrade("13", courseId);
//			gradeScore14 = gradeScore14 + studentCommentCourseBiz.getScoreByGrade("14", courseId);
//			gradeScore15 = gradeScore15 + studentCommentCourseBiz.getScoreByGrade("15", courseId);
//			gradeScore16 = gradeScore16 + studentCommentCourseBiz.getScoreByGrade("16", courseId);
//			gradeScore17 = gradeScore17 + studentCommentCourseBiz.getScoreByGrade("17", courseId);
//			
//			gradeNumber08 = gradeNumber08 + studentCommentCourseBiz.numberOfGetScoreByGrade("08", courseId);
//			gradeNumber09 = gradeNumber09 + studentCommentCourseBiz.numberOfGetScoreByGrade("09", courseId);
//			gradeNumber10 = gradeNumber10 + studentCommentCourseBiz.numberOfGetScoreByGrade("10", courseId);
//			gradeNumber11 = gradeNumber11 + studentCommentCourseBiz.numberOfGetScoreByGrade("11", courseId);
//			gradeNumber12 = gradeNumber12 + studentCommentCourseBiz.numberOfGetScoreByGrade("12", courseId);
//			gradeNumber13 = gradeNumber13 + studentCommentCourseBiz.numberOfGetScoreByGrade("13", courseId);
//			gradeNumber14 = gradeNumber14 + studentCommentCourseBiz.numberOfGetScoreByGrade("14", courseId);
//			gradeNumber15 = gradeNumber15 + studentCommentCourseBiz.numberOfGetScoreByGrade("15", courseId);
//			gradeNumber16 = gradeNumber16 + studentCommentCourseBiz.numberOfGetScoreByGrade("16", courseId);
//			gradeNumber17 = gradeNumber17 + studentCommentCourseBiz.numberOfGetScoreByGrade("17", courseId);
//			
//		}
//		
//		Map<String, Object> TagsWithColor1 = new HashMap<>();
//		TagsWithColor1.put("label", "手下留情");
//		TagsWithColor1.put("data",allCoursesBearScore);
//		TagsWithColor1.put("color", "#3c8dbc");
//		
//		Map<String, Object> TagsWithColor2 = new HashMap<>();
//		TagsWithColor2.put("label", "诙谐幽默");
//		TagsWithColor2.put("data",allCoursesInterestingScore);
//		TagsWithColor2.put("color", "#0073b7");
//		
//		Map<String, Object> TagsWithColor3 = new HashMap<>();
//		TagsWithColor3.put("label", "水一水");
//		TagsWithColor3.put("data",allCoursesEasyScore);
//		TagsWithColor3.put("color", "#00c0ef");
//		
//		Map<String, Object> TagsWithColor4 = new HashMap<>();
//		TagsWithColor4.put("label", "干货满满");
//		TagsWithColor4.put("data",allCoursesKnowledgeScore);
//		TagsWithColor4.put("color", "#004532");
//		
//		
//		Map<String, Object> tagsPercent = new HashMap<>();
//		tagsPercent.put("tag1", TagsWithColor1);
//		tagsPercent.put("tag2", TagsWithColor2);
//		tagsPercent.put("tag3", TagsWithColor3);
//		tagsPercent.put("tag4", TagsWithColor4);
//		
//		
//		Map<String, Object> preExamScore = new HashMap<>();
//		
//		preExamScore.put("ave08", gradeScore08/gradeNumber08);
//		preExamScore.put("ave09", gradeScore08/gradeNumber09);
//		preExamScore.put("ave10", gradeScore08/gradeNumber10);
//		preExamScore.put("ave11", gradeScore08/gradeNumber11);
//		preExamScore.put("ave12", gradeScore08/gradeNumber12);
//		preExamScore.put("ave13", gradeScore08/gradeNumber13);
//		preExamScore.put("ave14", gradeScore08/gradeNumber14);
//		preExamScore.put("ave15", gradeScore08/gradeNumber15);
//		preExamScore.put("ave16", gradeScore08/gradeNumber16);
//		preExamScore.put("ave17", gradeScore08/gradeNumber17);
//		
//		
//		int contentScore08 = studentCommentCourseBiz.getContentScoreByGrade("08");
//		int contentScore09 = studentCommentCourseBiz.getContentScoreByGrade("09");
//		int contentScore10 = studentCommentCourseBiz.getContentScoreByGrade("10");
//		int contentScore11 = studentCommentCourseBiz.getContentScoreByGrade("11");
//		int contentScore12 = studentCommentCourseBiz.getContentScoreByGrade("12");
//		int contentScore13 = studentCommentCourseBiz.getContentScoreByGrade("13");
//		int contentScore14 = studentCommentCourseBiz.getContentScoreByGrade("14");
//		int contentScore15 = studentCommentCourseBiz.getContentScoreByGrade("15");
//		int contentScore16 = studentCommentCourseBiz.getContentScoreByGrade("16");
//		int contentScore17 = studentCommentCourseBiz.getContentScoreByGrade("17");
//		
//		int contentScoreNumber08 = studentCommentCourseBiz.getContentScoreByGrade("08");
//		int contentScoreNumber09 = studentCommentCourseBiz.getContentScoreByGrade("09");
//		int contentScoreNumber10 = studentCommentCourseBiz.getContentScoreByGrade("10");
//		int contentScoreNumber11 = studentCommentCourseBiz.getContentScoreByGrade("11");
//		int contentScoreNumber12 = studentCommentCourseBiz.getContentScoreByGrade("12");
//		int contentScoreNumber13 = studentCommentCourseBiz.getContentScoreByGrade("13");
//		int contentScoreNumber14 = studentCommentCourseBiz.getContentScoreByGrade("14");
//		int contentScoreNumber15 = studentCommentCourseBiz.getContentScoreByGrade("15");
//		int contentScoreNumber16 = studentCommentCourseBiz.getContentScoreByGrade("16");
//		int contentScoreNumber17 = studentCommentCourseBiz.getContentScoreByGrade("17");
//		
//		Map<String, Object> preContentScore = new HashMap<>();
//		
//		preContentScore.put("contentScore08", contentScore08/contentScoreNumber08);
//		preContentScore.put("contentScore09", contentScore09/contentScoreNumber09);
//		preContentScore.put("contentScore10", contentScore10/contentScoreNumber10);
//		preContentScore.put("contentScore11", contentScore11/contentScoreNumber11);
//		preContentScore.put("contentScore12", contentScore12/contentScoreNumber12);
//		preContentScore.put("contentScore13", contentScore13/contentScoreNumber13);
//		preContentScore.put("contentScore14", contentScore14/contentScoreNumber14);
//		preContentScore.put("contentScore15", contentScore15/contentScoreNumber15);
//		preContentScore.put("contentScore16", contentScore16/contentScoreNumber16);
//		preContentScore.put("contentScore17", contentScore17/contentScoreNumber17);
		

		Map<String, Object> data = new HashMap<>();
		
		data.put("teacher", teacher);
		data.put("college", college);
		//data.put("tags", tags);
		data.put("courses",courses);
		//data.put("comments", comments);
		/*data.put("preExamScore", preExamScore);
		data.put("preContentScore", preContentScore);
		data.put("tagsPercent", tagsPercent);*/
		
		res.put("data", data);
		res.put("success", success);
        
        return gson.toJson(res);
    }
    
}
