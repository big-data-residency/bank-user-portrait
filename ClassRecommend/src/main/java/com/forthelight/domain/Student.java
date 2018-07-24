package com.forthelight.domain;

import java.util.HashSet;
import java.util.Set;

public class Student {
	private Integer id;
	private String studentName;
	private String gender;
	private String password;
	private String studentNumber;
	private int grade;
	private int deleteStatus;
	
	private College college;
	private Major major;
	Set<Course> courses = new HashSet<Course>();
	
}
