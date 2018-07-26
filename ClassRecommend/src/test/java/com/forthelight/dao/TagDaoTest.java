package com.forthelight.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forthelight.domain.Tag;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TagDaoTest {
	@Autowired
	private TagDao tagDao;
	
	@Test
	public void TestFindById() {
		Tag tag = tagDao.findById(1);
		System.out.println(tag);
	}
	
	@Test
	public void TestFindByName() {
		Tag tag = tagDao.findByName("给分很高");
		System.out.println(tag);
	}	
	
	@Test
	public void TestFindAll() {
		List<Tag> tags = tagDao.findAll();
		System.out.println(tags);
	}
	
/*	@Test
	public void TestInsert() {
		Tag tag = new Tag();
		tag.setTagName("你很优秀");
		int result = tagDao.insert(tag);
		System.out.println(result);
	}*/
	
	@Test
	public void TestUpdate() {
		Tag tag = new Tag();
		tag.setTagName("你很优秀");
		tag.setId(5);
		int result = tagDao.update(tag);
		System.out.println(result);
	}
	
	@Test
	public void TestDelete() {
		int result = tagDao.delete(6);
		System.out.println(result);
	}

}
