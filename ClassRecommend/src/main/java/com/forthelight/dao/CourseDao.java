package com.forthelight.dao;

import com.forthelight.domain.Course;
import com.forthelight.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {

	Course findById(int id);

	Course findByCode(String courseCode);

	List<Course> findByCourseName(String courseName);

	List<Course> findByTeacherName(String teacherName);

	List<Course> findByLevel(String level);

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
	
	int oneTagNumber(int tagId , int id);
	
	List<Tag> tagList(int courseId);

	List<Course> selectByKeyword(String keyword);

    int setTime(@Param("course") Course course, @Param("lessonDay") int lessonDay, @Param("startTime") int startTime, @Param("endTime") int endTime);
}
