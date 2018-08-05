package com.forthelight;

import com.forthelight.biz.CourseBiz;
import com.forthelight.biz.CourseTimeBiz;
import com.forthelight.biz.StudentBiz;
import com.forthelight.biz.StudentCommentCourseBiz;
import com.forthelight.domain.CourseTime;
import com.forthelight.domain.StudentCommentCourse;
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
    public int[][] Time = new int [10][10];
    public List<Course> checkedCourses;
    public List<String> shouldCheckCourses;

    public String Worry;
    public boolean success;

    public boolean Initialize(){
        Course temp_course ;
        Worry="";
        success=true;
        checkedCourses = new ArrayList<Course>();

        for(int i=0;i<5;i++)
            for(int j=0;j<2;j++)
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

                //检查选择课程是否有冲突
                if(!checkCourseTime1(temp_course.getId(),1)){
                    success=false;
                    return false;
                }

                checkedCourses.add(temp_course);
            }
        }

        //获取应该上的课
        shouldCheckCourses = courseBiz.selectByShouldCheck(stu_Grade,stu_College,stu_Major);
        //添加认为必须选的课程
        checkShouldCheckLessons();

        return true;
    }

    public boolean Recommend(){

        List<Course> recommendCourses = courseBiz.selectByRecommendCourse(stu_Grade,stu_College,stu_Major);

        //删去冲突的课程
        Iterator<Course> iterator = recommendCourses.iterator();
        while(iterator.hasNext()){
            Course course = iterator.next();
            int t=0;
            for(int i=0;i<checkedCourses.size();i++){
                if(course.getCourseName().equals(checkedCourses.get(i).getCourseName())){
                    t=1;
                    break;
                }
            }
            if(t==1 || !checkCourseTime2(course.getId())){
                iterator.remove();
            }
        }

        float count[] = new float[200];
        for(int i=0;i<recommendCourses.size();i++){
            if(recommendCourses.get(i).getLevel().equals("E"))
                count[i]=getCourseScore(recommendCourses.get(i).getId());
            else
                count[i]=getShouldCheckCourseScore(recommendCourses.get(i).getId());
            if(checkCourseTime3(recommendCourses.get(i).getId())){
                checkedCourses.add(recommendCourses.get(i));
            }
        }

        //初始化背包过程数组长度
//        int TM[][] = new int [10][5];
//        for(int i=0;i<5;i++) {
//            for (int j = 0; j < 2; j++) {
//                if (Time[i][j] == -1)
//                    TM[i][j] = 1;
//                else
//                    TM[i][j] = 0;
//            }
//        }
//        int Dnumber = numberD-hasD;
//        int Enumber = numberE-hasE;
//        int count[][][][][][][][][][][][][] = new int[200][5][5][2][2][2][2][2][2][2][2][2][2];
//        count[0][0][0][0][0][0][0][0][0][0][0][0][0]=0;
//        for(int courseNum=0;courseNum<recommendCourses.size();courseNum++){
//            for(int D=0;D<Dnumber;D++){
//                for(int E=0;E<Enumber;E++){
//                    for(int T00=0;T00<TM[0][0];T00++){
//                        for(int T01=0;T01<TM[0][1];T01++){
//                            for(int T10=0;T10<TM[1][0];T10++){
//                                for(int T11=0;T11<TM[1][1];T11++){
//                                    for(int T20=0;T20<TM[2][0];T20++){
//                                        for(int T21=0;T21<TM[2][1];T21++){
//                                            for(int T30=0;T30<TM[3][0];T30++){
//                                                for(int T31=0;T31<TM[3][1];T31++){
//                                                    for(int T40=0;T40<TM[4][0];T40++){
//                                                        for(int T41=0;T41<TM[4][1];T41++){
//
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }


        return true;
    }

    public boolean checkCourseTime3(int courseId){
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson =0 ;
        for(int i=0;i<time.size();i++){
            if(time.get(i).getStartLesson()<5)
                lesson=0;
            else lesson=1;
            if(Time[time.get(i).getLessonDay()-1][lesson]!=-1)
                return false;
        }
        for(int i=0;i<time.size();i++){
            Time[time.get(i).getLessonDay()-1][lesson]=courseId;
        }
        return true;
    }

    public boolean checkCourseTime1(int courseId, int type){
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson=0;
        for(int i=0;i<time.size();i++){
            if(time.get(i).getStartLesson()<5)
                lesson=0;
            else lesson=1;
            if(Time[time.get(i).getLessonDay()-1][lesson]==-1)
                Time[time.get(i).getLessonDay()-1][lesson]=courseId;
            else{
                if(type==1){
                    courseCrash(courseId,Time[time.get(i).getLessonDay()-1][lesson]);
                }else if(type==2){
                    courseCover(courseId,Time[time.get(i).getLessonDay()-1][lesson]);
                }
                return false;
            }
        }
        return true;
    }
    public boolean checkCourseTime2(int courseId){
        List<CourseTime> time;
        time = courseTimeBiz.findByClassId(courseId);
        int lesson=0;
        for(int i=0;i<time.size();i++){
            if(time.get(i).getStartLesson()<5)
                lesson=0;
            else lesson=1;
            if(Time[time.get(i).getLessonDay()-1][lesson]!=-1){
                return false;
            }
        }
        return true;
    }
    public void courseCrash(int i,int j){
        Course course1,course2;
        course1=courseBiz.findById(i);
        course2=courseBiz.findById(j);
        Worry+="警告：您所选择的课程"+course1.getCourseName()+"和"+course2.getCourseName()+"冲突!\n";
    }
    public void courseCover(int i,int j){
        Course course1,course2;
        course1=courseBiz.findById(i);
        course2=courseBiz.findById(j);
        Worry+="提示：您所选择的课程"+course2.getCourseName()+"挤掉了课程"+course1.getCourseName()+"!\n";

    }

    public void checkShouldCheckLessons(){

       Iterator<String> iterator = shouldCheckCourses.iterator();
       while(iterator.hasNext()){
           String course = iterator.next();
           //判断是否有和已选课程重复的课程
           int t=0;
           for(int i=0;i<checkedCourses.size();i++){
               if(course.equals(checkedCourses.get(i).getCourseName())){
                   t=1;
                   break;
               }
           }
           if(t==1) {
               iterator.remove();
               continue;
           }

           checkShouldCheckCourses(course);

       }
    }
    public void checkShouldCheckCourses(String courseName){
        List<Course> givenCourses = courseBiz.findByCourseName(courseName);
        Course givencourse;
        float max=0;
        int maxi=0;
        if(givenCourses.size()>1){
            float[] score = new float[20];
            for(int i=0;i<givenCourses.size();i++){
                score[i]=getShouldCheckCourseScore(givenCourses.get(i).getId());
                if(score[i]>=max)maxi=i;
            }
            givencourse = givenCourses.get(maxi);
        }else{
            givencourse = givenCourses.get(0);
        }

        if(checkCourseTime1(givencourse.getId(),2)){
            checkedCourses.add(givencourse);
        }
    }
    public float getShouldCheckCourseScore(int courseId){
        List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);
        float grade=0;
        for(int i=0;i<comments.size();i++){
            grade+=comments.get(i).getGradeScore()*0.2;
            grade+=comments.get(i).getBearScore()*0.05;
            grade+=comments.get(i).getInterestingScore()*0.05;
            grade+=comments.get(i).getEasyScore()*0.05;
            grade+=comments.get(i).getKnowledgeScore()*0.05;
        }
        return grade;
    }
    public float getCourseScore(int courseId){
        List<StudentCommentCourse> comments = studentCommentCourseBiz.findByCourseId(courseId);
        float grade = 0;
        for(int i=0;i<comments.size();i++){
            grade+=comments.get(i).getBearScore()*0.05;
            grade+=comments.get(i).getInterestingScore()*0.05;
            grade+=comments.get(i).getEasyScore()*0.05;
            grade+=comments.get(i).getKnowledgeScore()*0.05;
            grade+=0.3;
        }
        return grade;
    }
    public void checkifCrash(){
        hasD=hasE=0;
        for(int i=0;i<selectedCourses.length;i++){

        }
    }

    public void checkCourseNumber(){

    }

}

