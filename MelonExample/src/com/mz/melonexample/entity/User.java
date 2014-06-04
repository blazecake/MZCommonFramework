package com.mz.melonexample.entity;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class User {
	
	@Expose
	Long id;
	
	@Expose
	String name;
	
	@Expose
	Integer age;
	
	@Expose
	Date birthday;
	
	@Expose
	int height;
	
	@Expose
	char gender;
	
	@Expose
	String address;
	
	@Expose
	long IDNum;
	
	@Expose
	double weight;
	
	public User(){
	}
	
	public User(Long id, String name, Integer age, Date birthday, int height, char gender, String address, long iDNum, double weight) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthday = birthday;
		this.height = height;
		this.gender = gender;
		this.address = address;
		IDNum = iDNum;
		this.weight=weight;
	}

	/*public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getIDNum() {
		return IDNum;
	}

	public void setIDNum(long iDNum) {
		IDNum = iDNum;
	}*/
	
	
	
	
}
