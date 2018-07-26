package com.forthelight.biz;

import com.forthelight.domain.Student;

public interface StudentBiz {
    String loginValidate(String studentNumber, String password);
    Student findByStudentNumber(String studentNumber);
    int insert(Student student);
}
