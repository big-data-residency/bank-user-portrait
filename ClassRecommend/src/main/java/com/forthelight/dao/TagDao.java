package com.forthelight.dao;

import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagDao {
	
	Tag findById(int id);

	Tag findByName(String Tagname);

	List<Tag> findAll();

	int update(Tag tag);

	int delete(int id);

	int insert(Tag tag);

	int tagComment(@Param("tag") Tag tag, @Param("comment") StudentCommentCourse studentCommentCourse);
}
