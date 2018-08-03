package com.forthelight;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.CourseTimeBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.domain.CourseTime;
import org.springframework.beans.factory.annotation.Autowired;
import com.forthelight.domain.Course;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseSelect {

    @Autowired
    private CourseBiz courseBiz;

    @Autowired
    private CourseTimeBiz courseTimeBiz;

    @Autowired
    private StudentCommentCourseBiz studentCommentCourseBiz;

    @Autowired
    private StudentBiz studentBiz;

    public int stu_Grade;
    public int stu_College;
    public int stu_Major;

    public boolean preferD;
    public boolean preferE;
    public boolean selectMoreD;
    public boolean selectMoreE;
    public boolean selectNumberD;
    public boolean selectNumberE;
    public Integer numberD;
    public Integer numberE;

    public boolean selectCourseType;
    public boolean allCourse;
    public boolean courseABCD;
    public boolean preScoreRequest;
    public int preScore;

    public int bareScore;
    public int interestingScore;
    public int easyScore;
    public int knowledgeScore;

    public String[] selectedCourses;
    public int[] selectedcourses;

    public Integer hasD;
    public Integer hasE;
    public int[][] Time;
    public List<Course> checkedCourses;
    public List<Course> shouldCheckCourses;

    public String Worry;
    public boolean success;
    public boolean Initialize(){
        Course temp_course ;
        Worry="";
        success=true;
        checkedCourses = new ArrayList<Course>();

        for(int i=0;i<7;i++)
            for(int j=0;j<14;j++)
                Time[i][j]=-1;
        hasD=hasE=0;

        //预处理已选课程
        if(selectedcourses!=null) {
            for (int i = 0; i < selectedcourses.length; i++) {
                temp_course = courseBiz.findById(selectedcourses[i]);
                //检查选择课程是否超过预期DE类课上限
                if(temp_course.getLevel().equals("D")){
                    hasD++;
                    if(selectNumberD && hasD>numberD){
                        Worry="您选择的D类课数量超过了您设定的修读D类课上限!";
                        success=false;
                        return false;
                    }
                }else if(temp_course.getLevel().equals("E")){
                    hasE++;
                    if(selectNumberE && hasE>numberE){
                        Worry="您选择的E类课数量超过了您设定的修读E类课上限!";
                        success=false;
                        return false;
                    }
                }

                checkedCourses.add(temp_course);

                //检查选择课程是否有冲突
                if(!checkCourseTime(temp_course.getId())){
                    success=false;
                    return false;
                }
            }
        }

        //获取应该上的课
        shouldCheckCourses = courseBiz.selectByShouldCheck(stu_Grade,stu_College,stu_Major);



        return true;
    }
    public boolean checkCourseTime(int courseId){
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        for(int i=0;i<time.size();i++){
            for(int x=time.get(i).getStartLesson()-1;x<time.get(i).getEndLesson();x++){
                if(Time[time.get(i).getLessonDay()-1][x]==-1)
                    Time[time.get(i).getLessonDay()-1][x]=courseId;
                else{
                    courseCrash(courseId,Time[time.get(i).getLessonDay()-1][x]);
                    return false;
                }
            }
        }
        return true;
    }
    public void courseCrash(int i,int j){
        Course course1,course2;
        course1=courseBiz.findById(i);
        course2=courseBiz.findById(j);
        Worry+="您所选择的课程"+course1.getCourseName()+"和"+course2.getCourseName()+"冲突!\n";
    }
    public void checkifCrash(){
        hasD=hasE=0;
        for(int i=0;i<selectedCourses.length;i++){

        }
    }

    public void checkCourseNumber(){

    }

}
