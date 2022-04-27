package com.example.springbatchdelta.writer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;

import com.example.springbatchdelta.model.Student;

public class Previous_Writer implements ItemWriter<Student> {
     public static Map<Integer,Student> Previous_Map=new HashMap<>();
	@Override
	public void write(List<? extends Student> items) throws Exception {
	   for(Student st:items) {
		   Previous_Map.put(st.getStudent_Id(),st);
		  
	   }
	
	}

}
