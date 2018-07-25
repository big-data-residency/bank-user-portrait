package com.forthelight.dao;

import java.util.List;

import com.forthelight.domain.Tag;

public interface TagDao {
	Tag findById(int id);

	Tag findByName(String Tagname);

	List<Tag> findAll();

	int update(Tag tag);

	int delete(int id);

	int insert(Tag tag);

}
