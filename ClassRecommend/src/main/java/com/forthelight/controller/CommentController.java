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
import java.sql.Timestamp;
import java.util.*;

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
        Integer selectId = studentBiz.getSelectId(student, course);
        if(selectId == null){
            rsp.put("success", false);
            rsp.put("data", "你没有选择这门课");
            return gson.toJson(rsp);
        }
        comment.setComment(request.getParameter("description"));
        String anonymous = request.getParameter("Anonymous");
        if(anonymous == null || anonymous.equals("false")) {
            comment.setAnonymous(0);
        } else {
            comment.setAnonymous(1);
        }

        comment.setCourse(course);
        comment.setStudent(student);
        comment.setSelectId(selectId);
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

            Collections.sort(commentList, new Comparator<StudentCommentCourse>() {
                @Override
                public int compare(StudentCommentCourse o1, StudentCommentCourse o2) {
                    return o2.getCommentTime().compareTo(o1.getCommentTime());
                }
            });

            int i=0;
            for (StudentCommentCourse comment : commentList) {  // 每一条评论
                if(comment.getReplyTo() == null) {  // 如果是根评论
                    JsonObject element = gson.toJsonTree(comment, StudentCommentCourse.class).getAsJsonObject();    // 获取根评论内容
                    element.add("student", gson.toJsonTree(comment.getStudent(), Student.class));  // 根评论的学生信息
                    // 添加子评论
                    JsonObject subComments = new JsonObject();
                    int j = 0;
                    for(StudentCommentCourse subComment: comment.getSubComment()){  // 每一个子评论
                        JsonObject subElement = gson.toJsonTree(subComment, StudentCommentCourse.class).getAsJsonObject();
                        subElement.add("student", gson.toJsonTree(subComment.getStudent(), Student.class));
                        j++;
                        subComments.add(String.valueOf(j), subElement);
                    }
                    element.add("subComments", subComments);
                    comments.add(String.valueOf(i), element);
                    i++;
                }
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
    @RequestMapping(value = "/appendComment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appendComment(
            @RequestParam("commentId")String commentId,
            @RequestParam("content")String content,
            @RequestParam("courseId")String courseId,
            @RequestParam("studentId")String studentId){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        StudentCommentCourse comment = new StudentCommentCourse();

        comment.setStudent(studentBiz.findById(Integer.parseInt(studentId)));
        comment.setCourse(courseBiz.findById(Integer.parseInt(courseId)));
        comment.setComment(content);
        comment.setReplyTo(studentCommentCourseBiz.findById(Integer.parseInt(commentId)));
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));

        studentCommentCourseBiz.insert(comment);

        JsonObject rsp = new JsonObject();
        rsp.addProperty("success", true);
        return gson.toJson(rsp);
    }
}
