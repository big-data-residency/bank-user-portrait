package com.forthelight.biz.impl;

import com.forthelight.biz.TeacherBiz;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Teacher;

public class TeacherBizImpl implements TeacherBiz {
    private TeacherDao teacherDao;

    @Override
    public Teacher findById(Integer id) {
        return teacherDao.findById(id);
    }
}
