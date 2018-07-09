package com.test.entity;

public class Address {
	private Integer id;
	private String address;
	private String zipecode;
	private User user;
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipecode() {
		return zipecode;
	}
	public void setZipecode(String zipecode) {
		this.zipecode = zipecode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}			

}
