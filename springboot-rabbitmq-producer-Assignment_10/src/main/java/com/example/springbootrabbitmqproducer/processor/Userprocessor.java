package com.example.springbootrabbitmqproducer.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.springbootrabbitmqproducer.model.User;

public class Userprocessor implements ItemProcessor<User,User>{

	@Override
	public User process(User item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(item);
		return item;
	}
	
	

}
