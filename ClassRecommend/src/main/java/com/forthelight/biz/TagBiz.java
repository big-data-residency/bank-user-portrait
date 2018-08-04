package com.forthelight.biz;

import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;

import java.util.List;
import java.util.Map;

public interface TagBiz {
	
	Tag findById(int id);

	Tag findByName(String Tagname);

	List<Tag> findAll();

	int update(Tag tag);

	int delete(int id);

	int insert(Tag tag);

	int tagComment(Tag tag, StudentCommentCourse studentCommentCourse);

	Map<String, Integer> sumTagByCourseId(Integer courseId);
}
