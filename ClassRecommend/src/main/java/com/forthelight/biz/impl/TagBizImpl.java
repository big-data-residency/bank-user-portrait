package com.forthelight.biz.impl;

import com.forthelight.biz.TagBiz;
import com.forthelight.dao.StudentCommentCourseDao;
import com.forthelight.dao.TagDao;
import com.forthelight.domain.StudentCommentCourse;
import com.forthelight.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagBizImpl implements TagBiz {

    @Autowired
    private TagDao tagDao;
    @Autowired
    private StudentCommentCourseDao commentDao;

    @Override
    public Tag findById(int id) {
        return tagDao.findById(id);
    }

    @Override
    public Tag findByName(String Tagname) {
        return tagDao.findByName(Tagname);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public int update(Tag tag) {
        return tagDao.update(tag);
    }

    @Override
    public int delete(int id) {
        return tagDao.delete(id);
    }

    @Override
    public int insert(Tag tag) {
        // TODO Auto-generated method stub
        return tagDao.insert(tag);
    }

    @Override
    public int tagComment(Tag tag, StudentCommentCourse studentCommentCourse) {
        return tagDao.tagComment(tag, studentCommentCourse);
    }

    @Override
    public Map<String, Integer> sumTagByCourseId(Integer courseId) {
        Map<String, Integer> result = new HashMap<>();

        List<StudentCommentCourse> comments = commentDao.findByCourseId(courseId);
        for (StudentCommentCourse comment : comments) {
            List<Tag> tags = comment.getTags();
            for (Tag tag : tags) {
                String tagName = tag.getTagName();
                if (result.containsKey(tagName)) {
                    result.put(tagName, result.get(tagName)+1);
                } else {
                    result.put(tagName, 1);
                }
            }
        }

    return result;
    }

}
