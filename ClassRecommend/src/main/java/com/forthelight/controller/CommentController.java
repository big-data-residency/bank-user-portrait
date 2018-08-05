package com.forthelight.controller;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.forthelight.domain.StudentCommentCourse;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private StudentBiz studentBiz;
    @Autowired
    private StudentCommentCourseBiz studentCommentCourseBiz;

    @RequestMapping(value = "/commentUpload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadComment(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> rsp = new HashMap<>();

        Map<String, String[]> requestMap = request.getParameterMap();
        StudentCommentCourse comment = new StudentCommentCourse();
        Course course = courseBiz.findById(Integer.parseInt(request.getParameter("courseId")));
        Student student = studentBiz.findById(Integer.parseInt(request.getParameter("studentId")));
        comment.setComment(request.getParameter("description"));
        comment.setAnonymous(request.getParameter("anonymous").equals("true") ? 1 : 0);
        comment.setCourse(course);
        comment.setStudent(student);
        comment.setSelectId(studentBiz.getSelectId(student, course));
        comment.setGradeScore(Integer.parseInt(request.getParameter("gradeScore")));
        comment.setBearScore(Integer.parseInt(request.getParameter("bearScore")));
        comment.setKnowledgeScore(Integer.parseInt(request.getParameter("knowledgeScore")));
        comment.setEasyScore(Integer.parseInt(request.getParameter("easyScore")));
        comment.setInterestingScore(Integer.parseInt(request.getParameter("interestingScore")));
        comment.setRecommendGrade(Integer.parseInt(request.getParameter("recommendGrade")));
        studentCommentCourseBiz.insert(comment);


        rsp.put("success", true);
        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/getCommentsByCourseId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCommentsByCourseId(@RequestParam("courseId") String courseId){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject rsp = new JsonObject();
        JsonObject comments = new JsonObject();


        List<StudentCommentCourse> commentList = studentCommentCourseBiz.findByCourseId(Integer.parseInt(courseId));
        if(commentList != null && commentList.size() > 0) {
            int i=0;
            for (StudentCommentCourse comment : commentList) {
                JsonObject element = gson.toJsonTree(comment, StudentCommentCourse.class).getAsJsonObject();
                element.add("student", gson.toJsonTree(comment.getStudent(), Student.class));
                comments.add(String.valueOf(i),element);
                i++;
            }
        } else {
            rsp.addProperty("success", false);
            return gson.toJson(rsp);
        }
        JsonObject data = new JsonObject();
        data.add("comments", comments);
        rsp.add("data", data);
        rsp.addProperty("success", true);
        return gson.toJson(rsp);

    }
}
