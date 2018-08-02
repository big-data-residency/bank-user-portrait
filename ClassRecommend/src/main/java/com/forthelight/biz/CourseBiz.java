package com.forthelight.biz;

import java.util.List;

import com.forthelight.domain.Course;
import com.forthelight.domain.Student;
import com.forthelight.domain.Tag;
import com.forthelight.domain.Teacher;

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

	int likeNumber(int id);

	int oneTagNumber(int tagId, int id);

	List<Tag> tagList(int courseId);

	List<Course> selectByKeyword(String keyword);

}
