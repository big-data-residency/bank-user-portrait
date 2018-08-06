package com.forthelight.biz;

import com.forthelight.domain.Course;
import com.forthelight.domain.Tag;

import java.util.List;

public interface CourseBiz {

    Course findById(int id);

    Course findByCode(String courseCode);

    List<Course> findByCourseName(String courseName);

    List<Course> findByTeacherName(String teacher);

    List<Course> findByLevel(String leve);

    List<Course> findAll();

    int update(Course course);

    int delete(int id);

    int insert(Course course);

    List<Course> findByTeacherId(int teacherId);

    List<Course> findByCollegeId(int collegeId);

    List<Course> findByCourseTimeId(int courseTimeId);

    List<Course> findByMajorId(int majorId);

    List<Course> findByStudentId(int studentId);

    List<Course> orderByLike();

    Integer likeNumber(int id);

    int oneTagNumber(int tagId, int id);

    List<Tag> tagList(int courseId);

    List<Course> selectByKeyword(String keyword);


    int setTime(Course course, int lessonDay, int startTime, int endTime);

    List<Course> findCourseOfAdmin(String courseType, String teacherName, int examType, String courseName);


    List<Course> findByStudentIdAndTeacherName(int studentId, String teacherName);

    List<Course> findByStudentIdAndCourseCode(int studentId, String courseCode);

    List<Course> findByCourseAndTeacher(int studentId, String courseName, String teacherName);

    Course findByTeacherNameAndCourseName(String teacherName, String courseName);

    List<Course> findByTeacherCourseExamPass(String courseName, String teacherName, int examType, int passType);

    List<String> selectByShouldCheck(int grade, int collegeId, int majorId);

    List<Course> selectByRecommendCourse(int Grade, int College, int Major);

    List<Course> findByTeacherAndCode(String keyword);
}
