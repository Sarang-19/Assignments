package com.example.springbatchdelta.model;

public class Student {
   private int student_Id;
   private String student_Name;
   private String subject;
   private int marks;
   public static String[] getFields() {
	   return new String[] {"student_Id","student_Name","subject","marks"};
   }
public int getStudent_Id() {
	return student_Id;
}
public void setStudent_Id(int student_Id) {
	this.student_Id = student_Id;
}
public String getStudent_Name() {
	return student_Name;
}
public void setStudent_Name(String student_Name) {
	this.student_Name = student_Name;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public int getMarks() {
	return marks;
}
public void setMarks(int marks) {
	this.marks = marks;
}
public Student(int student_Id, String student_Name, String subject, int marks) {
	super();
	this.student_Id = student_Id;
	this.student_Name = student_Name;
	this.subject = subject;
	this.marks = marks;
}
   public Student() {
	   
   }
}
