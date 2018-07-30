package com.forthelight.dao;

import com.forthelight.domain.College;

import java.util.List;

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
    
    @Test
    public void testFindByName(){

        College college = collegeDao.findByName("计算机与控制工程学院");
        System.out.println(college.getCollegeName());
    }
    
    @Test
    public void testFindByType(){
    	
        List<College> colleges = collegeDao.findByType(1);
        System.out.println(colleges);
        
    }
    
    @Test
    public void testFindAll(){
    	
        List<College> colleges = collegeDao.findAll();
        System.out.println(colleges);
        
    }
    
/*    @Test
    public void testInsert(){
    	
        College college = new College();
        college.setCollegeName("CollegeTest");
        int result = collegeDao.insert(college);
        System.out.println(result);
        
    }*/
    
    @Test
    public void testUpdate(){
    	
        College college = new College();
        college.setId(7);
        college.setType(1);
        int result = collegeDao.update(college);
        System.out.println(result);
        
    }
    
    @Test
    public void testDelete(){
    	
        int result = collegeDao.delete(7);
        System.out.println(result);
        
    }

}
