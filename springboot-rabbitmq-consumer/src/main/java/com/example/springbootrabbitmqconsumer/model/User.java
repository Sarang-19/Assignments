package com.example.springbootrabbitmqconsumer.model;

public class User {
	  private int user_id;
	  private String user_name;
	  private int user_age;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}
	public User(int user_id, String user_name, int user_age) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_age = user_age;
	}
	  public User() {
		  
	  }
	  
	}
