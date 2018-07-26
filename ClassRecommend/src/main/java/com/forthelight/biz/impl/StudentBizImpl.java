package com.forthelight.biz.impl;

import com.forthelight.biz.StudentBiz;
import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Student;

public class StudentBizImpl implements StudentBiz {
    private StudentDao studentDao;

    @Override
    public String loginValidate(String studentNumber, String password) {
        Student student = studentDao.findByStudentNumber(studentNumber);
        if (student != null && student.getPassword() != null){
            if (student.getPassword().equals(password)){
                return "登录成功";
            } else {
                return "密码错误";
            }
        } else {
            return "用户不存在";
        }
    }

    @Override
    public Student findByStudentNumber(String studentNumber) {
        return studentDao.findByStudentNumber(studentNumber);
    }

    @Override
    public int insert(Student student) {
        return studentDao.insert(student);
    }
}
