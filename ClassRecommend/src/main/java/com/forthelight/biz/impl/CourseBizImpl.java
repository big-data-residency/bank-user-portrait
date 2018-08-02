package com.forthelight.biz.impl;

import com.forthelight.biz.CourseBiz;
import com.forthelight.dao.CourseDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("courseBiz")
public class CourseBizImpl implements CourseBiz {
	
	@Autowired
	private CourseDao courseDao;

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
	public int likeNumber(int id) {
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
	public List<Course> selectByKeyword(String keyword){
		return courseDao.selectByKeyword(keyword);
	}
		
    @Override
    public int setTime(Course course, int lessonDay, int startTime, int endTime) {
        return courseDao.setTime(course, lessonDay, startTime, endTime);
    }


}
