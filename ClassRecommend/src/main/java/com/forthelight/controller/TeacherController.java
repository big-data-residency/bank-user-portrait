package com.forthelight.controller;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.TeacherBiz;
import com.forthelight.biz.impl.TeacherBizImpl;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.College;
import com.forthelight.domain.Course;
import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TeacherController {
    private TeacherBiz teacherBiz;

    @Autowired
    public void setTeacherBiz(TeacherBiz teacherBiz) {
        this.teacherBiz = teacherBiz;
    }
    
    @Autowired
    private CourseBiz courseBiz;

  
   @RequestMapping(value= {"/teacherInfo"} , method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getTeacherInfo(String teacherIdStr, HttpServletResponse response) {
    	
    	response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        System.out.println(teacherIdStr);
        int teacherId = Integer.parseInt(teacherIdStr);
        
        Map<String, Object> res = new HashMap<>();
        Teacher teacher = teacherBiz.findById(teacherId);
		College college = teacher.getCollege();
		List<Tag> tags = teacherBiz.tagList(teacherId);

		List<Course> courses = courseBiz.findByTeacherId(teacherId);
		System.out.println("courses===" + courses);
		boolean success = false;
		if(teacher != null) {
			success = true;
		}

		Map<String, Object> data = new HashMap<>();
		
		data.put("teacher", teacher);
		data.put("college", college);
		data.put("tags", tags);
		data.put("courses",courses);
		
		res.put("data", data);
		res.put("success", success);
        
        return gson.toJson(res);
    }
    
}
