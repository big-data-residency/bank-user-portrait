package com.forthelight.domain;

import java.util.Comparator;

public class CompareByCommentTime implements Comparator<StudentCommentCourse> {

	@Override
	public int compare(StudentCommentCourse s1, StudentCommentCourse s2) {
		// TODO Auto-generated method stub
		long s2Time = s2.getCommentTime().getTime();
		long s1Time = s1.getCommentTime().getTime();
		return (int) (s2Time - s1Time);
	}
	
	

}
