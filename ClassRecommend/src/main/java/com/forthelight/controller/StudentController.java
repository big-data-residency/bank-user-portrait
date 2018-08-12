package com.forthelight.controller;

import com.forthelight.biz.CollegeBiz;
import com.forthelight.biz.MajorBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.domain.College;
import com.forthelight.domain.Major;
import com.forthelight.domain.Student;
import com.forthelight.domain.StudentCommentCourse;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    private StudentBiz studentBiz;
    private MajorBiz majorBiz;
    private CollegeBiz collegeBiz;

    @Autowired
    private StudentCommentCourseBiz studentCommentCourseBiz;

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

        JsonObject rsp = new JsonObject();
        String NickName = request.getParameter("NickName");
        String password = request.getParameter("password");

        Student student = studentBiz.loginValidate(NickName);
        if (student != null && student.getPassword() != null) {

            if (student.getPassword().equals(password)) {
                rsp.addProperty("success", true);
                rsp.add("data", gson.toJsonTree(student, Student.class));
                HttpSession session = request.getSession();
                session.setAttribute("user", student);
            } else {
                rsp.addProperty("success", false);
                rsp.addProperty("data", "密码错误");
            }
        } else {
            rsp.addProperty("success", false);
            rsp.addProperty("data", "用户不存在");
        }
        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/getsession", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getsession(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("json");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> rsp = new HashMap<>();

        HttpSession session = request.getSession(false);
        if (session != null) {
            Student student = (Student) session.getAttribute("user");
            if (student != null) {
                JsonObject studentInfo = new JsonObject();
                studentInfo.addProperty("studentId", student.getId());
                studentInfo.addProperty("privilege", student.getPrivilege());
                rsp.put("success", true);
                rsp.put("data", studentInfo);
            } else {
                rsp.put("success", false);
                rsp.put("data", "当前未登录");
            }
        } else {
            rsp.put("success", false);
            rsp.put("data", "当前未登录");
        }

        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/out", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String out(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("json");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> rsp = new HashMap<>();

        HttpSession session = request.getSession(false);
        session.invalidate();
        rsp.put("data","您已成功退出登陆");
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
                rsp.put("data", String.format("学院 %s 下没有专业 %s", request.getParameter("collegeName"),
                        request.getParameter("majorName")));
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
        if (studentBiz.findByName(request.getParameter("studentName")) != null) {
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


    @RequestMapping(value = "/studentInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8")
    @ResponseBody
    public String getStudentInfo(String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        int studentId = Integer.parseInt(studentIdStr);

        boolean success = false;

        if (studentIdStr != null) {
            success = true;
        }

        // ------------------ 左边栏学生信息数据 ------------------
        Student student = studentBiz.findById(studentId);
        if (student == null) {
            success = false;
        }

        JsonObject studentInfo = new JsonObject();

        studentInfo.addProperty("studentName", student.getStudentName());
        studentInfo.addProperty("nickName", student.getNickName());
        studentInfo.addProperty("studentNumber", student.getStudentNumber());
        studentInfo.addProperty("college", student.getCollege().getCollegeName());
        studentInfo.addProperty("major", student.getMajor().getMajorName());
        studentInfo.addProperty("portrait", student.getStudentPortrait());
        studentInfo.addProperty("id", studentId);
        // ------------------ 第一个标签栏数据 ---------------------
        List<StudentCommentCourse> studentCommentCourses = studentCommentCourseBiz.findByStudentId(studentId);
        JsonArray comments = new JsonArray();

        for (StudentCommentCourse studentCommentCourse : studentCommentCourses) {

            JsonObject comment = new JsonObject();
            // comment.addProperty("studentPortrait",
            // studentCommentCourse.getStudent().getStudentPortrait());
            int courseId = studentCommentCourse.getSelectId();

            comment.addProperty("courseName", studentCommentCourse.getCourse().getCourseName());
            String commentTime = studentCommentCourse.getCommentTime().toString();
            comment.addProperty("commentTime", commentTime);
            comment.addProperty("likeNumber", studentCommentCourse.getLikeNumber());
            comment.addProperty("commentContent", studentCommentCourse.getComment());

            comments.add(comment);

        }

        Map<String, Object> data = new HashMap<>();
        data.put("studentInfo", studentInfo);
        data.put("comments", comments);

        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);

    }


    @RequestMapping(value = "/editStudentInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8")
    @ResponseBody
    public String editStudentInfo(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        String studentIdStr = request.getParameter("studentIdStr");
        String nickName = request.getParameter("nickName");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String college = request.getParameter("college");
        String major = request.getParameter("major");

        int genderNumber = Integer.parseInt(gender);

        Student student = new Student();
        int studentId = Integer.parseInt(studentIdStr);

        student.setId(studentId);
        if (nickName == "")
            student.setNickName(studentBiz.findById(studentId).getNickName());
        else
            student.setNickName(nickName);
        student.setGender(gender);
        student.setStudentPortrait(studentBiz.findById(studentId).getStudentPortrait());
        student.setPassword(password);
//		if(college == ""){
//			college = collegeBiz.findByName(studentBiz.findById(studentId).getCollege().getCollegeName()).getCollegeName();
//		}
        if (college == "") {
            student.setCollege(studentBiz.findById(studentId).getCollege());
        } else
            student.setCollege(collegeBiz.findByName(college));
        if (major == "") {
            student.setMajor((studentBiz.findById(studentId).getMajor()));
        } else
            student.setMajor(majorBiz.findByName(major));

        int result = studentBiz.update(student);

        boolean success = false;
        if (result > 0) {
            success = true;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return gson.toJson(res);
    }


    @RequestMapping(value = "/searchStudent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8")
    @ResponseBody
    public String searchStudentInfo(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        String keyword = request.getParameter("keyword");
        List<Student> students = new ArrayList<Student>();

        if (keyword == "") {

            students = studentBiz.findAll();

        } else {

            students = studentBiz.findByKeyword(keyword);
        }

        boolean success = false;

        if (students.size() > 0) {
            success = true;
        }


        JsonArray studentInfo = new JsonArray();

        for (Student student : students) {

            JsonObject studentInfoArray = new JsonObject();
            studentInfoArray.addProperty("studentId", student.getId());
            studentInfoArray.addProperty("studentName", student.getStudentName());
            studentInfoArray.addProperty("nickName", student.getNickName());
            studentInfoArray.addProperty("studentNumber", student.getStudentNumber());
            studentInfoArray.addProperty("college", student.getCollege().getCollegeName());
            studentInfoArray.addProperty("major", student.getMajor().getMajorName());
            studentInfoArray.addProperty("selectNumber", student.getCourses().size());
            studentInfoArray.addProperty("commentNumber", studentCommentCourseBiz.findByStudentId(student.getId()).size());

            studentInfo.add(studentInfoArray);

        }

        Map<String, Object> data = new HashMap<>();
        data.put("students", studentInfo);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);

    }


    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String deleteStudent(String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        boolean success = false;

        int studentId = Integer.parseInt(studentIdStr);

        int result = studentBiz.delete(studentId);
        if (result > 0) {

            success = true;
        }

        res.put("success", success);

        return gson.toJson(res);

    }


    @RequestMapping(value = "/studentBasicInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String studentBasicInfo(String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        int studentId = Integer.parseInt(studentIdStr);

        boolean success = false;

        if (studentIdStr != null) {
            success = true;
        }

        Student studentInfo = studentBiz.findById(studentId);
        if (studentInfo == null) {
            success = false;
        }

        JsonObject studentInformation = new JsonObject();
        studentInformation.addProperty("studentName", studentInfo.getNickName());
        studentInformation.addProperty("studentNumber", studentInfo.getStudentNumber());

        Map<String, Object> student = new HashMap<>();

        student.put("student", studentInformation);
        student.put("college", studentInfo.getCollege().getCollegeName());
        student.put("major", studentInfo.getMajor().getMajorName());
        student.put("grade", studentInfo.getGrade());


        Map<String, Object> data = new HashMap<>();
        data.put("student", student);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }


    @RequestMapping(value = "/studentPagesInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String studentPagesInfo(String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        int studentId = Integer.parseInt(studentIdStr);
        Student student = studentBiz.findById(studentId);

        JsonObject studentInfo = new JsonObject();
        studentInfo.addProperty("nickName", student.getNickName());
        studentInfo.addProperty("major", student.getMajor().getMajorName());
        studentInfo.addProperty("studentNumber", student.getStudentNumber());
        studentInfo.addProperty("selectNumber", studentBiz.selectCourseNumber(studentId));
        studentInfo.addProperty("commentNumber", studentBiz.commentCourseNumber(studentId));
        studentInfo.addProperty("portrait", student.getStudentPortrait());
        studentInfo.addProperty("privilege", student.getPrivilege());
        studentInfo.addProperty("id", student.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("student", studentInfo);

        res.put("data", data);

        return gson.toJson(res);
    }

}