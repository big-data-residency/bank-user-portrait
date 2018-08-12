package com.forthelight.controller;

import com.forthelight.biz.*;
import com.forthelight.domain.*;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class CourseController {

    public int stu_Grade;
    public int stu_College;
    public int stu_Major;
    public boolean preferD;
    public boolean preferE;
    public boolean selectMoreD;
    public boolean selectMoreE;
    public boolean selectNumberD;
    public boolean selectNumberE;
    public Integer numberD;
    public Integer numberE;
    public boolean selectCourseType;
    public boolean allCourse;
    public boolean courseABCD;
    public boolean preScoreRequest;
    public int preScore;
    public int bareScore;
    public int interestingScore;
    public int easyScore;
    public int knowledgeScore;
    public String[] selectedCourses;
    public int[] selectedcourses = new int[20];
    public Integer hasD;
    public Integer hasE;
    public int[][] Time = new int[10][10];
    public List<Course> checkedCourses;
    public List<String> shouldCheckCourses;
    public String Worry;
    public boolean success;
    @Autowired
    CollegeBiz collegeBiz;
    private CourseSelectController courseSelectController = new CourseSelectController();
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private CourseTimeBiz courseTimeBiz;
    @Autowired
    private FileBiz fileBiz;
    @Autowired
    private StudentCommentCourseBiz studentCommentCourseBiz;
    @Autowired
    private TagBiz tagBiz;
    @Autowired
    private StudentBiz studentBiz;
    @Autowired
    private MajorBiz majorBiz;

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
            courseList.addProperty("courseCode", course.getCourseCode());
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


    @RequestMapping(value = "/rCourse", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String recommendCourse(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        preferD = request.getParameter("preferD").equals("true");
        System.out.println(preferD);
        preferE = request.getParameter("preferE").equals("true");
        System.out.println(preferE);
        selectMoreD = request.getParameter("selectMoreD").equals("true");
        selectMoreE = request.getParameter("selectMoreE").equals("true");
        selectNumberD = request.getParameter("selectNumberD").equals("true");
        selectNumberE = request.getParameter("selectNumberE").equals("true");
        if (selectNumberD) {
            numberD = Integer.parseInt(request.getParameter("numberD"));
            System.out.println(numberD);
        }
        if (selectNumberE) {
            numberE = Integer.parseInt(request.getParameter("numberE"));
            System.out.println(numberE);
        }

        selectCourseType = request.getParameter("selectCourseType").equals("true");
        allCourse = request.getParameter("allCourse").equals("true");
        courseABCD = request.getParameter("courseABCD").equals("true");
        preScoreRequest = request.getParameter("preScoreRequest").equals("true");
        if (preScoreRequest) {
            preScore = Integer.parseInt(request.getParameter("preScore"));
        }

        selectMoreD = request.getParameter("selectMoreD").equals("true");
        selectMoreD = request.getParameter("selectMoreD").equals("true");
        bareScore = Integer.parseInt(request.getParameter("bearScore"));
        interestingScore = Integer.parseInt(request.getParameter("interestingScore"));
        easyScore = Integer.parseInt(request.getParameter("easyScore"));
        knowledgeScore = Integer.parseInt(request.getParameter("knowledgeScore"));

        stu_Grade = Integer.parseInt(request.getParameter("grade")) * -1 + 19;
        stu_College = collegeBiz.findByName(request.getParameter("college")).getId();
        stu_Major = majorBiz.findByName(request.getParameter("major")).getId();

        selectedcourses = new int[20];
        selectedCourses = request.getParameterValues("selectedCourses[]");
        if (selectedCourses != null) {
            for (int i = 0; i < selectedCourses.length; i++) {
                selectedcourses[i] = Integer.parseInt(selectedCourses[i]);
            }
        }
        Map<String, Object> res = new HashMap<>();
        boolean success = true;

        if (!Initialize()) {
            success = false;
            res.put("data", Worry);
        }


        Recommend();

        res.put("success", success);

        return gson.toJson(res);
    }

    public boolean Initialize() {
        Course temp_course;
        Worry = "";
        success = true;
        checkedCourses = new ArrayList<Course>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                Time[i][j] = -1;
            }
        }
        hasD = hasE = 0;

        //预处理已选课程
        if (selectedcourses != null) {
            for (int i = 0; selectedcourses[i] != 0; i++) {
                temp_course = courseBiz.findById(selectedcourses[i]);
                //检查选择课程是否超过预期DE类课上限
                if (temp_course.getLevel().equals("D")) {
                    hasD++;
                    if (selectNumberD && hasD > numberD) {
                        Worry = "您选择的D类课数量超过了您设定的修读D类课上限!";
                        success = false;
                        return false;
                    }
                } else if (temp_course.getLevel().equals("E")) {
                    hasE++;
                    if (selectNumberE && hasE > numberE) {
                        Worry = "您选择的E类课数量超过了您设定的修读E类课上限!";
                        success = false;
                        return false;
                    }
                }

                //检查选择课程是否有冲突
                int wtf = temp_course.getId();
                if (!checkCourseTime1(wtf, 1)) {
                    success = false;
                    return false;
                }

                checkedCourses.add(temp_course);
            }
        }

        //获取应该上的课
        shouldCheckCourses = courseBiz.selectByShouldCheck(stu_Grade, stu_College, stu_Major);
        //添加认为必须选的课程
        checkShouldCheckLessons();

        return true;
    }


    private boolean checkCourseTime3(int courseId) {
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson = 0;
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).getStartLesson() < 5)
                lesson = 0;
            else lesson = 1;
            if (Time[time.get(i).getLessonDay() - 1][lesson] != -1)
                return false;
        }
        for (int i = 0; i < time.size(); i++) {
            Time[time.get(i).getLessonDay() - 1][lesson] = courseId;
        }
        return true;
    }

    private boolean checkCourseTime1(int courseId, int type) {
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson = 0;
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).getStartLesson() < 5)
                lesson = 0;
            else lesson = 1;
            if (Time[time.get(i).getLessonDay() - 1][lesson] == -1)
                Time[time.get(i).getLessonDay() - 1][lesson] = courseId;
            else {
                if (type == 1) {
                    courseCrash(courseId, Time[time.get(i).getLessonDay() - 1][lesson]);
                } else if (type == 2) {
                    courseCover(courseId, Time[time.get(i).getLessonDay() - 1][lesson]);
                }
                return false;
            }
        }
        return true;
    }

    private boolean checkCourseTime2(int courseId) {
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson = 0;
        for (int i = 0; i < time.size(); i++) {
            if (time.get(i).getStartLesson() < 5)
                lesson = 0;
            else lesson = 1;
            if (Time[time.get(i).getLessonDay() - 1][lesson] != -1) {
                return false;
            }
        }
        return true;
    }

    private void courseCrash(int i, int j) {
        Course course1, course2;
        course1 = courseBiz.findById(i);
        course2 = courseBiz.findById(j);
        Worry += "警告：您所选择的课程" + course1.getCourseName() + "和" + course2.getCourseName() + "冲突!\n";
    }

    private void courseCover(int i, int j) {
        Course course1, course2;
        course1 = courseBiz.findById(i);
        course2 = courseBiz.findById(j);
        Worry += "提示：您所选择的课程" + course2.getCourseName() + "挤掉了课程" + course1.getCourseName() + "!\n";

    }

    private void checkShouldCheckLessons() {
        Iterator<String> iterator = shouldCheckCourses.iterator();
        while (iterator.hasNext()) {
            String course = iterator.next();
            //判断是否有和已选课程重复的课程
            int t = 0;
            for (int i = 0; i < checkedCourses.size(); i++) {
                if (course.equals(checkedCourses.get(i).getCourseName())) {
                    t = 1;
                    break;
                }
            }
            if (t == 1) {
                iterator.remove();
                continue;
            }
            checkShouldCheckCourses(course);

        }
    }

    private void checkShouldCheckCourses(String courseName) {
        List<Course> givenCourses = courseBiz.findByCourseName(courseName);
        Course givencourse;
        float max = 0;
        int maxi = 0;
        if (givenCourses.size() > 1) {
            float[] score = new float[20];
            for (int i = 0; i < givenCourses.size(); i++) {
                score[i] = getShouldCheckCourseScore(givenCourses.get(i).getId());
                if (score[i] >= max) maxi = i;
            }
            givencourse = givenCourses.get(maxi);
        } else {
            givencourse = givenCourses.get(0);
        }

        if (checkCourseTime1(givencourse.getId(), 2)) {
            checkedCourses.add(givencourse);
        }
    }

    private float getShouldCheckCourseScore(int courseId) {
        List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);
        float grade = 0;
        for (int i = 0; i < comments.size(); i++) {
            grade += comments.get(i).getGradeScore() * 0.2;
            grade += comments.get(i).getBearScore() * 0.05;
            grade += comments.get(i).getInterestingScore() * 0.05;
            grade += comments.get(i).getEasyScore() * 0.05;
            grade += comments.get(i).getKnowledgeScore() * 0.05;
        }
        return grade;
    }

    private float getCourseScore(int courseId) {
        List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);
        float grade = 0;
        for (int i = 0; i < comments.size(); i++) {
            grade += comments.get(i).getBearScore() * 0.05;
            grade += comments.get(i).getInterestingScore() * 0.05;
            grade += comments.get(i).getEasyScore() * 0.05;
            grade += comments.get(i).getKnowledgeScore() * 0.05;
            grade += 0.3;
        }
        return grade;
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

    private boolean Recommend() {
        List<Course> recommendCourses = courseBiz.selectByRecommendCourse(stu_Grade, stu_College, stu_Major);

        //删去冲突的课程
        Iterator<Course> iterator = recommendCourses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            int t = 0;
            for (int i = 0; i < checkedCourses.size(); i++) {
                String shit = checkedCourses.get(i).getCourseName();
                String holyshit = course.getCourseName();
                if (holyshit.equals(shit)) {
                    t = 1;
                    break;
                }
            }
            if (t == 1 || !checkCourseTime2(course.getId())) {
                iterator.remove();
            }
        }

        int Dnumber = 0;
        int Enumber = 0;
        if (selectMoreD) {
            if (selectNumberD) {
                Dnumber = numberD - hasD;
            } else {
                Dnumber = 100;
            }
        }

        if (selectMoreE) {
            if (selectNumberE) {
                Enumber = numberE - hasE;
            } else {
                Enumber = 100;
            }
        }


        for (int i = 0; i < recommendCourses.size(); i++) {
            if (checkCourseTime3(recommendCourses.get(i).getId())) {
                if (recommendCourses.get(i).getLevel().equals("E") && Enumber > 0) {
                    checkedCourses.add(recommendCourses.get(i));
                    Enumber--;
                } else if (recommendCourses.get(i).getLevel().equals("D") && Dnumber > 0) {
                    checkedCourses.add(recommendCourses.get(i));
                    Dnumber--;
                } else {
                    checkedCourses.add(recommendCourses.get(i));
                }
            }
        }

        float count[] = new float[200];
        for (int i = 0; i < recommendCourses.size(); i++) {
            if (recommendCourses.get(i).getLevel().equals("E"))
                count[i] = getCourseScore(recommendCourses.get(i).getId());
            else
                count[i] = getShouldCheckCourseScore(recommendCourses.get(i).getId());
            if (checkCourseTime3(recommendCourses.get(i).getId())) {
                checkedCourses.add(recommendCourses.get(i));
            }
        }

        //初始化背包过程数组长度
//        int TM[][] = new int [10][5];
//        for(int i=0;i<5;i++) {
//            for (int j = 0; j < 2; j++) {
//                if (Time[i][j] == -1)
//                    TM[i][j] = 1;
//                else
//                    TM[i][j] = 0;
//            }
//        }
//        int Dnumber = numberD-hasD;
//        int Enumber = numberE-hasE;
//        int count[][][][][][][][][][][][][] = new int[200][5][5][2][2][2][2][2][2][2][2][2][2];
//        count[0][0][0][0][0][0][0][0][0][0][0][0][0]=0;
//        for(int courseNum=0;courseNum<recommendCourses.size();courseNum++){
//            for(int D=0;D<Dnumber;D++){
//                for(int E=0;E<Enumber;E++){
//                    for(int T00=0;T00<TM[0][0];T00++){
//                        for(int T01=0;T01<TM[0][1];T01++){
//                            for(int T10=0;T10<TM[1][0];T10++){
//                                for(int T11=0;T11<TM[1][1];T11++){
//                                    for(int T20=0;T20<TM[2][0];T20++){
//                                        for(int T21=0;T21<TM[2][1];T21++){
//                                            for(int T30=0;T30<TM[3][0];T30++){
//                                                for(int T31=0;T31<TM[3][1];T31++){
//                                                    for(int T40=0;T40<TM[4][0];T40++){
//                                                        for(int T41=0;T41<TM[4][1];T41++){
//
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }


        return true;
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

    @RequestMapping(value = "/noCheckingCourseList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findNoCheckingCourseList(@RequestParam("studentIdStr")String studentIdStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> res = new HashMap<>();

        boolean success = false;
        if (!studentIdStr.equals("")) {
            success = true;
        }

        if (success) {
            int studentId = Integer.parseInt(studentIdStr);
            List<Course> courses = courseBiz.findByStudentId(studentId);

            List<StudentCommentCourse> checkedCourses = studentCommentCourseBiz.findByStudentId(studentId);

            Iterator<Course> iterator = courses.iterator();
            while (iterator.hasNext()) {
                Course course = iterator.next();
                for (int i = 0; i < checkedCourses.size(); i++) {
                    if(courses.size() <= 0 || iterator.hasNext() ){break;}
                    if (course.getId().equals(checkedCourses.get(i).getCourse().getId())) {
                        iterator.remove();
                    }
                }
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

            if (coursesList.size() < 0) {
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
            System.out.print(course.getId());
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

            topCourse.addProperty("courseId",course.getId());
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
    public String getScoreGroupByYear(@RequestParam("courseId") String courseId) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject rsp = new JsonObject();

        List<StudentCommentCourse> commentList = studentCommentCourseBiz.findByCourseId(Integer.parseInt(courseId));
        HashMap<Integer, List<Integer>> gradeMap = new HashMap<>();
        gradeMap.clear();
        for (StudentCommentCourse comment : commentList) {
            Integer grade = comment.getStudent().getGrade();
            Integer score = comment.getGradeScore();
            List<Integer> temp = gradeMap.get(grade);
            if (temp != null) {
                temp.add(score);
                gradeMap.put(grade, temp);
            } else {
                List<Integer> newArray = new ArrayList<>();
                newArray.add(score);
                gradeMap.put(grade, newArray);
            }
        }


        Map<Integer, Integer> avgScore = new HashMap<>();
        JsonArray data = new JsonArray();
        for (Integer key : gradeMap.keySet()) {
            Integer sumScore = 0;
            List<Integer> scores = gradeMap.get(key);
            for (Integer score : scores) {
                sumScore += score;
            }
            JsonElement element = new JsonObject();
            element.getAsJsonObject().addProperty("year", "20" + String.valueOf(key));
            element.getAsJsonObject().addProperty("avgScore", sumScore / scores.size());
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
        JsonArray recommendCoursesTime = new JsonArray();

        for (Course course : checkedCourses) {
            JsonObject recommend = new JsonObject();
            recommend.addProperty("courseName", course.getCourseName());
            recommend.addProperty("courseCode", course.getCourseCode());
            recommend.addProperty("teacherName", course.getTeacher().getTeacherName());
            recommend.addProperty("studentNumber", course.getStudentNumber());
            recommend.addProperty("courseId", course.getId());

            JsonArray courseTimeTables = new JsonArray();
            for (CourseTime courseTime : course.getCourseTimes()) {

                JsonObject courseTimeTable = new JsonObject();
                courseTimeTable.addProperty("lessonDay", courseTime.getLessonDay());
                int startLesson = courseTime.getStartLesson();
                int endLesson = courseTime.getEndLesson();
                if (startLesson > 4) {
                    startLesson = startLesson + 2;
                }
                if (endLesson > 4) {
                    endLesson = endLesson + 2;
                }
                courseTimeTable.addProperty("courseStartLesson", startLesson);
                courseTimeTable.addProperty("courseEndLesson", endLesson);

                courseTimeTables.add(courseTimeTable);
            }

            recommendCoursesTime.add(courseTimeTables);

            recommendCourses.add(recommend);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("courseInfo", recommendCourses);
        data.put("courseTable", recommendCoursesTime);
        data.put("warning", Worry);

        res.put("data", data);
        res.put("success", true);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findByTeacherOrCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findByTeacherAndCode(String keyword, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> data = new HashMap<>();

        boolean success = false;
        if (keyword != "") {
            success = true;
        }

        if (success) {
            List<Course> courses = courseBiz.findByTeacherAndCode(keyword);

            JsonArray coursesList = new JsonArray();

            for (Course course : courses) {
                JsonObject courseList = new JsonObject();
                courseList.addProperty("courseId", course.getId());
                courseList.addProperty("courseCode", course.getCourseCode());
                courseList.addProperty("courseName", course.getCourseName());
                courseList.addProperty("teacherName", course.getTeacher().getTeacherName());
                courseList.addProperty("studentNumber", course.getStudentNumber());

                coursesList.add(courseList);
            }

            data.put("courses", coursesList);

            if (coursesList.size() < 1) {
                success = false;
            }
        }


        Map<String, Object> res = new HashMap<>();

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/findCourseTime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String findCourseTime(String idStr, HttpServletResponse response) {

        response.setContentType("text/json;charset:UTF-8");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Map<String, Object> res = new HashMap<>();

        JsonArray coursesTime = new JsonArray();

        int id = Integer.parseInt(idStr);
        for (CourseTime courseTime : courseBiz.findById(id).getCourseTimes()) {
            JsonObject time = new JsonObject();
            time.addProperty("lessonDay", courseTime.getLessonDay());

            if (courseTime.getStartLesson() > 4) {
                time.addProperty("startLesson", courseTime.getStartLesson() + 2);
            } else
                time.addProperty("startLesson", courseTime.getStartLesson());

            if (courseTime.getEndLesson() > 4) {
                time.addProperty("endLesson", courseTime.getEndLesson() + 2);
            } else time.addProperty("endLesson", courseTime.getEndLesson());

            coursesTime.add(time);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("coursesTime", coursesTime);
        data.put("courseName", courseBiz.findById(id).getCourseName());
        data.put("courseId", courseBiz.findById(id).getId());

        boolean success = false;
        if (coursesTime.size() > 0) {
            success = true;
        }

        res.put("data", data);
        res.put("success", success);

        return gson.toJson(res);
    }

    @RequestMapping(value = "/getCourseInfo", produces = "text/json; charset=utf-8")
    @ResponseBody
    public String getCourseInfo(@RequestParam("courseId") String courseId) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        JsonObject rsp = new JsonObject();

        Course course = courseBiz.findById(Integer.parseInt(courseId));
        if (course == null) {
            rsp.addProperty("success", false);
            rsp.addProperty("data", "没有id为" + courseId + "的课程");
            return gson.toJson(rsp);
        }

        JsonElement data = new JsonObject();
        data.getAsJsonObject().add("course", gson.toJsonTree(course, Course.class));
        data.getAsJsonObject().add("teacher", gson.toJsonTree(course.getTeacher(), Teacher.class));
        data.getAsJsonObject().add("major", gson.toJsonTree(course.getMajor(), Major.class));
        data.getAsJsonObject().add("college", gson.toJsonTree(course.getCollege(), College.class));


        JsonElement teachers = new JsonArray();
        for( Teacher teacher: course.getCollege().getTeachers()){
            teachers.getAsJsonArray().add(gson.toJsonTree(teacher, Teacher.class));
        }
        data.getAsJsonObject().add("teachers", teachers);

        JsonElement colleges = new JsonArray();
        for(College college: collegeBiz.findAll()){
            colleges.getAsJsonArray().add(gson.toJsonTree(college, College.class));
        }
        data.getAsJsonObject().add("colleges", colleges);

        JsonElement majors = new JsonArray();
        for(Major major: course.getCollege().getMajors()){
            majors.getAsJsonArray().add(gson.toJsonTree(major, Major.class));
        }
        data.getAsJsonObject().add("majors", majors);

        rsp.add("data", data);
        rsp.addProperty("success", true);

        return gson.toJson(rsp);

    }

    @RequestMapping(value = "/updateCourseInfo", produces = "text/json; charset=utf-8")
    @ResponseBody
    public String updateCourseInfo(@RequestParam("course") Course course) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        JsonObject rsp = new JsonObject();
        courseBiz.update(course);
        rsp.addProperty("success", true);
        return gson.toJson(rsp);
    }
}