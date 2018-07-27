package com.forthelight.biz;

import com.forthelight.dao.StudentDao;
import com.forthelight.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentBizTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    public void loginValidate() {
        Student student = studentDao.findByStudentNumber("Batman");
        if (student != null && student.getPassword() != null){
            if (student.getPassword().equals("batman")){
                System.out.println("登录成功");
            } else {
                System.out.println("密码错误");
            }
        } else {
            System.out.println("用户不存在");
        }
    }
}