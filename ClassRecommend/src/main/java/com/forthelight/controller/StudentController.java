package com.forthelight.controller;

import com.forthelight.biz.CollegeBiz;
import com.forthelight.biz.MajorBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.domain.College;
import com.forthelight.domain.Major;
import com.forthelight.domain.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentController {
    private StudentBiz studentBiz;
    private MajorBiz majorBiz;
    private CollegeBiz collegeBiz;

    @Autowired
    public void setStudentBiz(StudentBiz studentBiz) {
        this.studentBiz = studentBiz;
    }

    @Autowired
    public void setMajorBiz(MajorBiz majorBiz) {
        this.majorBiz = majorBiz;
    }

    @Autowired
    public void setCollegeBiz(CollegeBiz collegeBiz) {
        this.collegeBiz = collegeBiz;
    }


    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("json");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> rsp = new HashMap<>();
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");

        String validateResult = studentBiz.loginValidate(studentNumber, password);
        if (validateResult.equals("登录成功")) {
            rsp.put("success", true);
            rsp.put("data", validateResult);
        } else {
            rsp.put("success", false);
            rsp.put("data", validateResult);
        }
        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String register(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> rsp = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        String collegeName = request.getParameter("collegeName");
        College college = collegeBiz.findByName(collegeName);
        Major major = majorBiz.findByName(request.getParameter("majorName"));

        // 验证学院和专业
        if (college == null) {
            rsp.put("success", false);
            rsp.put("data", String.format("学院 %s 不存在", request.getParameter("collegeName")));
            return gson.toJson(rsp);
        } else {
            if (major == null || !major.getCollege().getId().equals(college.getId())) {
                rsp.put("success", false);
                rsp.put("data", String.format("学院 %s 下没有专业 %s", request.getParameter("collegeName"), request.getParameter("majorName")));
                return gson.toJson(rsp);
            }
        }
        // 验证学号重复
        if (studentBiz.findByStudentNumber(request.getParameter("studentNumber")) != null) {
            rsp.put("success", false);
            rsp.put("data", "该学号已注册");
            return gson.toJson(rsp);
        }
        // 验证姓名重复
        if(studentBiz.findByName(request.getParameter("studentName")) != null){
            rsp.put("success", false);
            rsp.put("data", "该姓名已注册");
            return gson.toJson(rsp);
        }
        // 验证两次密码
        if (!request.getParameter("password").equals(request.getParameter("re_password"))) {
            rsp.put("success", false);
            rsp.put("data", "两次密码不一致");
            return gson.toJson(rsp);
        }

        // 创建用户
        Student newStudent = new Student();
        newStudent.setStudentName(request.getParameter("studentName"));
        newStudent.setStudentNumber(request.getParameter("studentNumber"));
        newStudent.setPassword(request.getParameter("password"));
        newStudent.setNickName(request.getParameter("nickName"));
        newStudent.setGender(request.getParameter("gender"));
        newStudent.setGrade(Integer.parseInt(request.getParameter("grade")));

        newStudent.setCollege(college);
        newStudent.setMajor(major);

        studentBiz.insert(newStudent);
        rsp.put("success", true);
        rsp.put("data", "注册成功");
        return gson.toJson(rsp);
    }
}
