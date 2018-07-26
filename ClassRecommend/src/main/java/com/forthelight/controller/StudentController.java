package com.forthelight.controller;

import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.impl.StudentBizImpl;
import com.forthelight.domain.Student;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentController {
    private StudentBiz studentBiz = new StudentBizImpl();

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        Map<String, String> rsp = new HashMap<>();
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");

        String validateResult = studentBiz.loginValidate(studentNumber, password);
        if (validateResult.equals("登陆成功")){
            rsp.put("success", "true");
            rsp.put("data", validateResult);
        } else {
            rsp.put("success", "false");
            rsp.put("data", validateResult);
        }
        return gson.toJson(rsp);
    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        Map<String, String> rsp = new HashMap<>();
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re_password");

        Student student = studentBiz.findByStudentNumber(studentNumber);
        if (student != null){
            rsp.put("success", "false");
            rsp.put("data", "该用户已注册");
        }
        else {
            if (!password.equals(rePassword)){
                rsp.put("success", "false");
                rsp.put("data", "两次密码不一致");
            }
            else {
                Student newStudent = new Student();
                newStudent.setStudentNumber(studentNumber);
                newStudent.setPassword(password);
                studentBiz.insert(newStudent);
                rsp.put("success", "true");
                rsp.put("data", "注册成功");
            }
        }

        return gson.toJson(rsp);
    }
}
