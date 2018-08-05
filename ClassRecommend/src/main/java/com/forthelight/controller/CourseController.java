package com.forthelight.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.forthelight.CourseSelect;

import com.forthelight.domain.*;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.FileBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.biz.TagBiz;


@Controller
public class CourseController {

    private CourseSelect courseSelect = new CourseSelect();

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

        if (courseIdStr != null) {
            success = true;
        }

        Course course = courseBiz.findById(courseId);

        // ----------------- 左边栏数据 --------------------
        String courseName = course.getCourseName();
        String teacherName = course.getTeacher().getTeacherName();
        Map<Integer, Object> courseTimes = new HashMap<>();
        String[] day = "日,一,二,三,四,五,六".split(",");
        int i = 0;
        for (CourseTime time : course.getCourseTimes()) {
            Map<String, Object> courseTime = new HashMap();
            courseTime.put("startTime", time.getStartLesson());
            courseTime.put("endTime", time.getEndLesson());
            courseTime.put("lessonDay", day[time.getLessonDay()]);
            courseTimes.put(i, courseTime);
            i++;
        }
        Integer likeNumber = courseBiz.likeNumber(courseId);
        likeNumber = likeNumber == null ? 0 : likeNumber;
        Integer uploadsNumber = fileBiz.uploadsNumberOfCourse(courseId);
        Integer commentNumber = studentCommentCourseBiz.commentNumberOfCourse(courseId);

        // -------------------- 第一个标签数据 --------------------------
        Map<String, Integer> TagsNumber = tagBiz.sumTagByCourseId(courseId);

        // ------------------- 第二个标签栏数据 --------------------------
//        List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);

        // ----------------------- 传输左边栏、第一二个标签Json数据 ----------------------------------


        Map<String, Object> courseDetail = new HashMap<>();
        courseDetail.put("courseName", course.getCourseName());
        courseDetail.put("teacherName", course.getTeacher().getTeacherName());
        courseDetail.put("courseTimes", courseTimes);
        courseDetail.put("startWeek", String.valueOf(course.getStartWeek()));
        courseDetail.put("endWeek", String.valueOf(course.getEndWeek()));

        Map<String, Object> data = new HashMap<>();
        data.put("courseDetail", courseDetail);

        data.put("courseName", courseName);
        data.put("teacherName", teacherName);
        data.put("likeNumber", likeNumber);
        data.put("uploadsNumber", uploadsNumber);
        data.put("commentNumber", commentNumber);
        data.put("TagsNumber", TagsNumber);
        data.put("commentNumber", commentNumber);
//        expand to commentsMap
//        data.put("comments", comments);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);

    }


    // ### classrecommend.html 课程评论界面
    @RequestMapping(value = "/courseCommnet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
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


    @RequestMapping(value = "/searchCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String studentBasicInfo(String courseKeyword, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        List<Course> courses;
        if (courseKeyword == "") {
            courses = courseBiz.findAll();
        } else {
            courses = courseBiz.selectByKeyword(courseKeyword);
        }

        JsonArray coursesList = new JsonArray();
        for (Course course : courses) {
            JsonObject courseList = new JsonObject();
            courseList.addProperty("id", course.getId());
            courseList.addProperty("courseName", course.getCourseName());
            courseList.addProperty("courseCode",course.getCourseCode());
            coursesList.add(courseList);
        }

        boolean success = false;
        if (courses.size() > 0) {
            success = true;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("courses", coursesList);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }


    @RequestMapping(value = "/rCourse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8")
    @ResponseBody
    public String recommendCourse(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        courseSelect.preferD = request.getParameter("preferD").equals("true");
        System.out.println(courseSelect.preferD);
        courseSelect.preferE = request.getParameter("preferE").equals("true");
        System.out.println(courseSelect.preferE);
        courseSelect.selectMoreD = request.getParameter("selectMoreD").equals("true");
        courseSelect.selectMoreE = request.getParameter("selectMoreE").equals("true");
        courseSelect.selectNumberD = request.getParameter("selectNumberD").equals("true");
        courseSelect.selectNumberE = request.getParameter("selectNumberE").equals("true");
        if (courseSelect.selectNumberD) {
            courseSelect.numberD = Integer.parseInt(request.getParameter("numberD"));
            System.out.println(courseSelect.numberD);
        }
        if (courseSelect.selectNumberE) {
            courseSelect.numberE = Integer.parseInt(request.getParameter("numberE"));
            System.out.println(courseSelect.numberE);
        }

        courseSelect.selectCourseType = request.getParameter("selectCourseType").equals("true");
        courseSelect.allCourse = request.getParameter("allCourse").equals("true");
        courseSelect.courseABCD = request.getParameter("courseABCD").equals("true");
        courseSelect.preScoreRequest = request.getParameter("preScoreRequest").equals("true");
        if (courseSelect.preScoreRequest) {
            courseSelect.preScore = Integer.parseInt(request.getParameter("preScore"));
        }

        courseSelect.selectMoreD = request.getParameter("selectMoreD").equals("true");
        courseSelect.selectMoreD = request.getParameter("selectMoreD").equals("true");
        courseSelect.bareScore = Integer.parseInt(request.getParameter("bearScore"));
        courseSelect.interestingScore = Integer.parseInt(request.getParameter("interestingScore"));
        courseSelect.easyScore = Integer.parseInt(request.getParameter("easyScore"));
        courseSelect.knowledgeScore = Integer.parseInt(request.getParameter("knowledgeScore"));

        courseSelect.selectedCourses = request.getParameterValues("selectedCourses[]");

        Map<String, Object> res = new HashMap<>();

        boolean success = true;

        if(!courseSelect.Initialize()){
            success = false;
            res.put("data",courseSelect.Worry);
        }


        courseSelect.Recommend();

        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findCourseOfAdmin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findCourseOfAdmin(String courseType, String teacherInfo, String examType, String courseName, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        System.out.println(courseType + teacherInfo + examType + courseName);

        int exam = -1;
        if (examType.equals("闭卷考试")) {
            exam = 0;
        }
        if (examType.equals("开卷考试")) {
            exam = 1;
        }
        if (examType.equals("论文结课")) {
            exam = 2;
        }
        if (examType.equals("其他")) {
            exam = 3;
        }

        if (courseType.equals("全部")) {
            courseType = null;
        }
        if (teacherInfo.equals("全部教师")) {
            teacherInfo = null;
        }
        if (courseName == "") {
            courseName = null;
        }

        System.out.print(courseType + teacherInfo + exam + courseName);

        List<Course> courses = courseBiz.findCourseOfAdmin(courseType, teacherInfo, exam, courseName);

        boolean success = false;
        if (courses.size() > 0) {
            success = true;
        }

        JsonArray coursesInfo = new JsonArray();
        for (Course course : courses) {

            JsonObject courseInfo = new JsonObject();
            courseInfo.addProperty("courseId", course.getId());
            courseInfo.addProperty("courseCode", course.getCourseCode());
            courseInfo.addProperty("courseName", course.getCourseName());
            courseInfo.addProperty("teacherName", course.getTeacher().getTeacherName());

            int examMethod = course.getExaminingForm();
            if (examMethod == 0) {
                courseInfo.addProperty("examType", "闭卷考试");
            }
            if (examMethod == 1) {
                courseInfo.addProperty("examType", "开卷考试");
            }
            if (examMethod == 2) {
                courseInfo.addProperty("examType", "论文结课");
            }
            if (examMethod == 3) {
                courseInfo.addProperty("examType", "其他");
            }

            int passMethod = course.getPassingCourse();
            if (passMethod == 0) {
                courseInfo.addProperty("passType", "否");
            }
            if (passMethod == 1) {
                courseInfo.addProperty("passType", "是");
            }

            coursesInfo.add(courseInfo);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("courses", coursesInfo);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }


    @RequestMapping(value = "/findByStudentIdAndTeacherName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByStudentIdAndTeacherName(String teacherName, String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        boolean success = false;
        if (teacherName != "") {
            success = true;
        }
        if (studentIdStr == "") {
            success = false;
        }

        if (success) {
            int studentId = Integer.parseInt(studentIdStr);

            JsonObject allCourse = new JsonObject();
            allCourse.addProperty("courseName", "全部课程");

            List<Course> courses = new ArrayList<>();

            if (teacherName.equals("全部教师")) {
                courses = courseBiz.findByStudentId(studentId);
            } else {
                courses = courseBiz.findByStudentIdAndTeacherName(studentId, teacherName);
            }

            JsonArray coursesList = new JsonArray();
            coursesList.add(allCourse);

            for (Course course : courses) {
                JsonObject courseList = new JsonObject();
                courseList.addProperty("courseName", course.getCourseName());
                coursesList.add(courseList);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courses", coursesList);
            res.put("data", data);
        }

        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findAllCourseByStudentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findAllCourseByStudentId(String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> res = new HashMap<>();

        boolean success = false;
        if (studentIdStr != "") {
            success = true;
        }

        if (success) {
            int studentId = Integer.parseInt(studentIdStr);
            List<Course> courses = courseBiz.findByStudentId(studentId);

            JsonArray coursesList = new JsonArray();

            for (Course course : courses) {
                JsonObject courseList = new JsonObject();

                courseList.addProperty("courseId", course.getId());
                courseList.addProperty("courseCode", course.getCourseCode());
                courseList.addProperty("courseName", course.getCourseName());
                courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
                if (course.getExaminingForm() == 0) {
                    courseList.addProperty("examForm", "闭卷");
                }
                if (course.getExaminingForm() == 1) {
                    courseList.addProperty("examForm", "开卷");
                }
                if (course.getExaminingForm() == 2) {
                    courseList.addProperty("examForm", "论文结课");
                }
                if (course.getExaminingForm() == 3) {
                    courseList.addProperty("examForm", "其他");
                }

                if (course.getPassingCourse() == 0) {
                    courseList.addProperty("passingForm", "否");
                }
                if (course.getPassingCourse() == 1) {
                    courseList.addProperty("passForm", "是");
                }

                coursesList.add(courseList);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courses", coursesList);

            if (coursesList.size() < 1) {
                success = false;
            }

            res.put("data", data);
        }

        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findByStudentIdAndCourseCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByStudentIdAndCourseCode(String studentIdStr, String courseCode, String teacherName, String courseName, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        boolean success = false;
        if (!studentIdStr.equals("")) {
            success = true;
        }
        if (courseName.equals("全部课程")) {
            courseName = null;
        }
        if (teacherName.equals("全部教师")) {
            teacherName = null;
        }

        if (success) {
            List<Course> courses = new ArrayList<>();

            int studentId = Integer.parseInt(studentIdStr);
            if (courseCode == "") {
                courses = courseBiz.findByCourseAndTeacher(studentId, courseName, teacherName);
            } else {
                courses = courseBiz.findByStudentIdAndCourseCode(studentId, courseCode);
            }

            JsonArray coursesList = new JsonArray();
            for (Course course : courses) {
                JsonObject courseList = new JsonObject();

                courseList.addProperty("courseId", course.getId());
                courseList.addProperty("courseCode", course.getCourseCode());
                courseList.addProperty("courseName", course.getCourseName());
                courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
                if (course.getExaminingForm() == 0) {
                    courseList.addProperty("examForm", "闭卷");
                }
                if (course.getExaminingForm() == 1) {
                    courseList.addProperty("examForm", "开卷");
                }
                if (course.getExaminingForm() == 2) {
                    courseList.addProperty("examForm", "论文结课");
                }
                if (course.getExaminingForm() == 3) {
                    courseList.addProperty("examForm", "其他");
                }

                if (course.getPassingCourse() == 0) {
                    courseList.addProperty("passingForm", "否");
                }
                if (course.getPassingCourse() == 1) {
                    courseList.addProperty("passForm", "是");
                }

                coursesList.add(courseList);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courses", coursesList);
            if (coursesList.size() < 1) {
                success = false;
            }

            res.put("data", data);
        }


        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findAllCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findAllCourse(HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        boolean success = true;

        List<Course> courses = courseBiz.findAll();

        JsonArray coursesList = new JsonArray();
        for (Course course : courses) {
            JsonObject courseList = new JsonObject();

            courseList.addProperty("courseId", course.getId());
            courseList.addProperty("courseCode", course.getCourseCode());
            courseList.addProperty("courseName", course.getCourseName());
            courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
            if (course.getExaminingForm() == 0) {
                courseList.addProperty("examForm", "闭卷");
            }
            if (course.getExaminingForm() == 1) {
                courseList.addProperty("examForm", "开卷");
            }
            if (course.getExaminingForm() == 2) {
                courseList.addProperty("examForm", "论文结课");
            }
            if (course.getExaminingForm() == 3) {
                courseList.addProperty("examForm", "其他");
            }

            if (course.getPassingCourse() == 0) {
                courseList.addProperty("passingForm", "否");
            }
            if (course.getPassingCourse() == 1) {
                courseList.addProperty("passForm", "是");
            }

            coursesList.add(courseList);
        }

        Map<String, Object> res = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("courses", coursesList);

        if (coursesList.size() < 1) {
            success = false;
        }

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findByType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByType(String courseType, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        boolean success = false;
        if (courseType != "") {
            success = true;
        }

        List<Course> courses = new ArrayList<>();

        if (success) {
            courses = courseBiz.findByLevel(courseType);

            JsonArray coursesList = new JsonArray();
            for (Course course : courses) {
                JsonObject courseList = new JsonObject();

                courseList.addProperty("courseId", course.getId());
                courseList.addProperty("courseCode", course.getCourseCode());
                courseList.addProperty("courseName", course.getCourseName());
                courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
                if (course.getExaminingForm() == 0) {
                    courseList.addProperty("examForm", "闭卷");
                }
                if (course.getExaminingForm() == 1) {
                    courseList.addProperty("examForm", "开卷");
                }
                if (course.getExaminingForm() == 2) {
                    courseList.addProperty("examForm", "论文结课");
                }
                if (course.getExaminingForm() == 3) {
                    courseList.addProperty("examForm", "其他");
                }

                if (course.getPassingCourse() == 0) {
                    courseList.addProperty("passingForm", "否");
                }
                if (course.getPassingCourse() == 1) {
                    courseList.addProperty("passForm", "是");
                }

                coursesList.add(courseList);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courses", coursesList);
            res.put("data", data);
            if (coursesList.size() < 1) {
                success = false;
            }
        }

        res.put("success", success);
        return gson.toJson(res);
    }

    @RequestMapping(value = "/findByTeacherName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByTeacherName(String teacherName, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();


        boolean success = false;
        if (teacherName != "") {
            success = true;
        }

        if (success) {
            List<Course> courses = new ArrayList<>();
            if (teacherName.equals("全部教师")) {
                courses = courseBiz.findAll();
            } else {
                courses = courseBiz.findByTeacherName(teacherName);
            }

            JsonObject allCourse = new JsonObject();
            allCourse.addProperty("courseName", "全部课程");

            JsonArray coursesList = new JsonArray();
            coursesList.add(allCourse);

            for (Course course : courses) {
                JsonObject courseList = new JsonObject();
                courseList.addProperty("courseName", course.getCourseName());
                coursesList.add(courseList);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("courses", coursesList);
            res.put("data", data);
        }

        res.put("success", success);
        return gson.toJson(res);
    }


    @RequestMapping(value = "/findByCourseCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByCourseCode(String courseCode, String teacherName, String courseName, String passType, String examType, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();
        boolean success = false;

        if (courseName.equals("全部课程")) {
            courseName = null;
        }
        if (teacherName.equals("全部教师")) {
            teacherName = null;
        }
        int exam = -1;
        if (examType.equals("闭卷考试")) {
            exam = 0;
        }
        if (examType.equals("开卷考试")) {
            exam = 1;
        }
        if (examType.equals("论文结课")) {
            exam = 2;
        }
        if (examType.equals("其他")) {
            exam = 3;
        }
        int pass = -1;
        if (passType.equals("true")) {
            pass = 1;
        }
        if (passType.equals("false")) {
            pass = 0;
        }

        List<Course> courses = new ArrayList<>();
        Course courseOfCode = new Course();
        if (courseCode == "") {
            courses = courseBiz.findByTeacherCourseExamPass(courseName, teacherName, exam, pass);
        } else {
            courseOfCode = courseBiz.findByCode(courseCode);
        }

        Map<String, Object> data = new HashMap<>();

        if (courses.size() > 0) {

            JsonArray coursesList = new JsonArray();
            for (Course course : courses) {
                JsonObject courseList = new JsonObject();

                courseList.addProperty("courseId", course.getId());
                courseList.addProperty("courseCode", course.getCourseCode());
                courseList.addProperty("courseName", course.getCourseName());
                courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
                if (course.getExaminingForm() == 0) {
                    courseList.addProperty("examForm", "闭卷");
                }
                if (course.getExaminingForm() == 1) {
                    courseList.addProperty("examForm", "开卷");
                }
                if (course.getExaminingForm() == 2) {
                    courseList.addProperty("examForm", "论文结课");
                }
                if (course.getExaminingForm() == 3) {
                    courseList.addProperty("examForm", "其他");
                }

                if (course.getPassingCourse() == 0) {
                    courseList.addProperty("passingForm", "否");
                }
                if (course.getPassingCourse() == 1) {
                    courseList.addProperty("passForm", "是");
                }

                coursesList.add(courseList);
            }
            if (coursesList.size() > 0) {
                success = true;
            }
            data.put("courses", coursesList);
        } else {

            JsonObject course = new JsonObject();
            if (courseOfCode.getExaminingForm() == 0) {
                course.addProperty("examForm", "闭卷");
            }
            if (courseOfCode.getExaminingForm() == 1) {
                course.addProperty("examForm", "开卷");
            }
            if (courseOfCode.getExaminingForm() == 2) {
                course.addProperty("examForm", "论文结课");
            }
            if (courseOfCode.getExaminingForm() == 3) {
                course.addProperty("examForm", "其他");
            }

            if (courseOfCode.getPassingCourse() == 0) {
                course.addProperty("passingForm", "否");
            }
            if (courseOfCode.getPassingCourse() == 1) {
                course.addProperty("passForm", "是");
            }
            course.addProperty("courseId", courseOfCode.getId());
            course.addProperty("courseCode", courseOfCode.getCourseCode());
            course.addProperty("courseName", courseOfCode.getCourseName());
            course.addProperty("teacherName", courseOfCode.getTeacher().getTeacherName());

            if (course.size() > 0) {
                success = true;
            }

            data.put("courses", course);
        }

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }


    @RequestMapping(value = "/getTop10Course", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getTop10Course(String teacherName, String courseName, String passType, String examType, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        List<Course> courses = courseBiz.orderByLike();

        JsonArray top10Courses = new JsonArray();

        for (int i = 0; i < 10; i++) {
            Course course = courses.get(i);
            JsonObject topCourse = new JsonObject();

            topCourse.addProperty("courseCode", course.getCourseCode());
            topCourse.addProperty("courseName", course.getCourseName());
            topCourse.addProperty("studentNumber", course.getStudentNumber());
            topCourse.addProperty("likeNumber", courseBiz.likeNumber(course.getId()));

            top10Courses.add(topCourse);
        }

        boolean success = false;
        if (top10Courses.size() > 0) {
            success = true;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("courses", top10Courses);

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/getScoreRate", produces = "text/json; charset=utf-8")
    @ResponseBody
    public String getScoreRate(@RequestParam("courseId") String courseId) {
        Map<String, Object> rsp = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Course course = courseBiz.findById(Integer.parseInt(courseId));
        if (course == null) {
            rsp.put("success", false);
            rsp.put("errorInfo", "没有找到id为" + courseId + "的课程");
        } else {
            data.put("midExamWeight", course.getMidExamWeight());
            data.put("finalExamWeight", course.getFinalExamWeight());
            data.put("otherWeight", 100 - course.getMidExamWeight() - course.getFinalExamWeight());
            rsp.put("data", data);
            rsp.put("success", true);
        }

        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/getScoreGroupByYear", produces = "text/json; charset=utf-8")
    @ResponseBody
    public String getScoreGroupByYear(@RequestParam("courseId")String courseId){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject rsp = new JsonObject();

        List<StudentCommentCourse> commentList = studentCommentCourseBiz.findByCourseId(Integer.parseInt(courseId));
        HashMap<Integer, List<Integer>> gradeMap = new HashMap<>();
        gradeMap.clear();
        for(StudentCommentCourse comment: commentList){
            Integer grade = comment.getStudent().getGrade();
            Integer score = comment.getGradeScore();
            List<Integer> temp = gradeMap.get(grade);
            if(temp != null) {
                temp.add(score);
                gradeMap.put(grade, temp);
            } else {
                List<Integer> newArray = new ArrayList<>();
                newArray.add(score);
                gradeMap.put(grade, newArray);
            }
        }


        Map<Integer, Integer>avgScore = new HashMap<>();
        JsonArray data = new JsonArray();
        for(Integer key: gradeMap.keySet()){
            Integer sumScore = 0;
            List<Integer> scores = gradeMap.get(key);
            for(Integer score: scores){
                sumScore+=score;
            }
            JsonElement element = new JsonObject();
            element.getAsJsonObject().addProperty("year", "20" + String.valueOf(key));
            element.getAsJsonObject().addProperty("avgScore", sumScore/scores.size());
            data.add(element);
        }

        rsp.add("data", data);
        rsp.addProperty("success", true);
        return gson.toJson(rsp);
    }

    @RequestMapping(value = "/getRecommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getRecommend(HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        JsonArray recommendCourses = new JsonArray();

        for (Course course : courseSelect.checkedCourses) {
            JsonObject recommend = new JsonObject();
            recommend.addProperty("courseName", course.getCourseName());
            recommend.addProperty("courseCode", course.getCourseCode());
            recommend.addProperty("teacherName", course.getTeacher().getTeacherName());
            recommend.addProperty("studentNumber", course.getStudentNumber());
            recommend.addProperty("courseId", course.getId());
            int i = 0;
            for (CourseTime courseTime : course.getCourseTimes()) {
                recommend.addProperty("courseDay" + i, courseTime.getLessonDay());
                int startLesson = courseTime.getStartLesson();
                int endLesson = courseTime.getEndLesson();
                if (startLesson > 4) {
                    startLesson = startLesson + 2;
                }
                if (endLesson > 4) {
                    endLesson = endLesson + 2;
                }

                recommend.addProperty("courseStartLesson" + i, startLesson);
                recommend.addProperty("courseEndLesson" + i,endLesson);
                i++;
            }
            recommend.addProperty("courseNumber", i);

            recommendCourses.add(recommend);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("courseInfo", recommendCourses);

        res.put("data", data);
        res.put("success", true);

        return gson.toJson(res);
    }

}