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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<StudentCommentCourse> studentCommentList = studentCommentCourseDao.findAll();
        if(tagList != null && studentCommentList != null) {
            for (StudentCommentCourse studentComment : studentCommentList) {
                int times = random.nextInt(tagList.size());
                for (int i = 0; i < times; i++) {
                    Tag tag = tagList.get(random.nextInt(tagList.size()));
                    tagDao.tagComment(tag, studentComment);
                }
            }
        }
    }

    @Test
    public void sumTagByCourseId(){
//        Map<String, Integer> result = new HashMap<>();
//
//        List<StudentCommentCourse> comments = studentCommentCourseDao.findByCourseId(317);
//        for (StudentCommentCourse comment : comments) {
//            List<Tag> tags = comment.getTags();
//            for (Tag tag : tags) {
//                String tagName = tag.getTagName();
//                if (result.containsKey(tagName)) {
//                    result.put("tagName", result.get(tagName)+1);
//                } else {
//                    result.put(tagName, 1);
//                }
//            }
//        }
//        System.out.println(result);
    }
}