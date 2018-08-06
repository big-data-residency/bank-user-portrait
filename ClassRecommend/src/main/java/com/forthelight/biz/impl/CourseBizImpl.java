package com.forthelight.biz.impl;

import com.forthelight.biz.CourseBiz;
import com.forthelight.dao.CourseDao;
import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("courseBiz")
public class CourseBizImpl implements CourseBiz {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudentCommentCourseDao studentCommentCourseDao;

    @Override
    public Course findById(int id) {
        return courseDao.findById(id);
    }

    @Override
    public Course findByCode(String courseCode) {
        return courseDao.findByCode(courseCode);
    }

    @Override
    public List<Course> findByCourseName(String courseName) {
        return courseDao.findByCourseName(courseName);
    }

    @Override
    public List<Course> findByTeacherName(String teacherName) {
        return courseDao.findByTeacherName(teacherName);
    }

    @Override
    public List<Course> findByLevel(String leve) {
        return courseDao.findByLevel(leve);
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public int update(Course course) {
        return courseDao.update(course);
    }

    @Override
    public int delete(int id) {
        return courseDao.delete(id);
    }

    @Override
    public int insert(Course course) {
        return courseDao.insert(course);
    }

    @Override
    public List<Course> findByTeacherId(int teacherId) {
        return courseDao.findByTeacherId(teacherId);
    }

    @Override
    public List<Course> findByCollegeId(int collegeId) {
        return courseDao.findByCollegeId(collegeId);
    }

    @Override
    public List<Course> findByCourseTimeId(int courseTimeId) {
        return courseDao.findByCourseTimeId(courseTimeId);
    }

    @Override
    public List<Course> findByMajorId(int majorId) {
        return courseDao.findByMajorId(majorId);
    }

    @Override
    public List<Course> findByStudentId(int studentId) {
        return courseDao.findByStudentId(studentId);
    }

    @Override
    public List<Course> orderByLike() {
        return courseDao.orderByLike();
    }

    @Override
    public Integer likeNumber(int id) {
        return courseDao.likeNumber(id);
    }

    @Override
    public int oneTagNumber(int tagId, int id) {
        return courseDao.oneTagNumber(tagId, id);
    }

    @Override
    public List<Tag> tagList(int courseId) {

        return courseDao.tagList(courseId);
    }

    @Override
    public List<Course> selectByKeyword(String keyword) {
        return courseDao.selectByKeyword(keyword);
    }

    @Override
    public int setTime(Course course, int lessonDay, int startTime, int endTime) {
        return courseDao.setTime(course, lessonDay, startTime, endTime);
    }

    @Override

    public List<Course> findCourseOfAdmin(String courseType, String teacherName, int examType, String courseName) {
        return courseDao.findCourseOfAdmin(courseType, teacherName, examType, courseName);
    }

    @Override
    public List<Course> findByStudentIdAndTeacherName(int studentId, String teacherName) {
        return courseDao.findByStudentIdAndTeacherName(studentId, teacherName);
    }

    @Override
    public List<Course> findByStudentIdAndCourseCode(int studentId, String courseCode) {
        return courseDao.findByStudentIdAndCourseCode(studentId, courseCode);
    }

    @Override
    public List<Course> findByCourseAndTeacher(int studentId, String courseName, String teacherName) {
        return courseDao.findByCourseAndTeacher(studentId, courseName, teacherName);
    }

    @Override
    public Course findByTeacherNameAndCourseName(String teacherName, String courseName) {
        return courseDao.findByTeacherNameAndCourseName(teacherName, courseName);
    }

    @Override
    public List<Course> findByTeacherCourseExamPass(String courseName, String teacherName, int examType,
                                                    int passType) {
        return courseDao.findByTeacherCourseExamPass(courseName, teacherName, examType, passType);
    }

    @Override
    public List<String> selectByShouldCheck(int grade, int collegeId, int majorId) {

        List<Course> courses = courseDao.findRecommendCourse(collegeId,majorId);

        List<String> recommendCourseName = new ArrayList<>();

        for(Course course:courses){

            List<StudentCommentCourse> comments = studentCommentCourseDao.findByCourseId(course.getId());

            int total = comments.size();
            int recommendGrade = 0;

            for(StudentCommentCourse comment : comments){
                if(comment.getRecommendGrade() == grade){
                    recommendGrade++;
                }
            }

            double totalDouble = (double) total;
            double recommendGradeDouble =(double) recommendGrade;

            if(recommendGradeDouble/totalDouble >0.8){
                recommendCourseName.add(course.getCourseName());
            }

        }

        return recommendCourseName;
    }

    @Override
    public List<Course> selectByRecommendCourse(int Grade,int College, int Major){
        List<Course> courses = courseDao.findSelectCourse(College,Major);

        return courses;
    }

    @Override
    public List<Course> findByTeacherAndCode(String keyword){
        return courseDao.findByTeacherAndCode(keyword);
    }

}
