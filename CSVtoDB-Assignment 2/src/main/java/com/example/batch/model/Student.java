package com.example.batch.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student {
  
	@Id
  private int id;
  private String stu_name;
  private String subject;
  private int marks;
  
public int getMarks() {
	return marks;
}
public void setMarks(int marks) {
	this.marks = marks;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getStu_name() {
	return stu_name;
}
public void setStu_name(String stu_name) {
	this.stu_name = stu_name;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public Student(int id, String stu_name, String subject,int marks) {
	super();
	this.id = id;
	this.stu_name = stu_name;
	this.subject = subject;
	this.marks=marks;
}
  public Student() {
	  
  }
}
