package com.forthelight.dao;

import com.forthelight.domain.College;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CollegeDaoTest {
    @Autowired
    private CollegeDao collegeDao;

    @Test
    public void testFindById(){
        College college = collegeDao.findById(1);
        System.out.println(college.getCollegeName());
    }
}


//public class CollegeDaoTest {
//    public static void main(String[] args) {
//
//    }
//}