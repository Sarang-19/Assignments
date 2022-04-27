package com.example.batch.model;


import java.util.List;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.batch.repository.StudentRepository;
@Component
public class Studentwriter implements ItemWriter<Student> {
	@Autowired
	private StudentRepository studrep;
	Logger logger=org.slf4j.LoggerFactory.getLogger(Studentwriter.class);
	@Override
	public void write(List<? extends Student> items) throws Exception {
		logger.info("Data read from the CSV file is written to the DB");
	
		studrep.saveAll(items);		
		
	}

}
