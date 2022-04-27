package com.example.batch.Processor;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


import com.example.batch.model.Student;
@Component
public class StudentProcessor implements ItemProcessor<Student,Student> {
	Logger logger=org.slf4j.LoggerFactory.getLogger(StudentProcessor.class);
	@Override
	public Student process(Student item) throws Exception {
		logger.info("Student objects greater than 45 marks are returned");
		if(item.getMarks()<45) {
			return null;
		}
		return item;
		
	}

}
