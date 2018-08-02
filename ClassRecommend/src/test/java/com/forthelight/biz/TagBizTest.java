package com.forthelight.biz;

import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.dao.TagDao;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TagBizTest {
    @Autowired
    TagDao tagDao;
    @Autowired
    StudentCommentCourseDao studentCommentCourseDao;

    @Test
    public void tagComment() {
        Random random = new Random();
        List<Tag> tagList = tagDao.findAll();
        for (int i = 1; i < 354; i += random.nextInt(4) + 1) {
            StudentCommentCourse comment = studentCommentCourseDao.findById(i);
            Tag tag = tagList.get(random.nextInt(tagList.size()));
            tagDao.tagComment(tag, comment);
        }
    }
}