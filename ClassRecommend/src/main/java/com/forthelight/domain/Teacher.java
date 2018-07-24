package com.forthelight.domain;

import java.io.Serializable;

public class Teacher implements Serializable {
    private Integer id;
    private String teacherName;
    private Integer gender;
    private String telPhone;
    private String email;
    private String officeAddress;
    private College college;
    private Integer deleteStatus;

}
