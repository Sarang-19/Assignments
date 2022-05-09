package com.example.producer_mq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer_mq.Config.MqConfig;
import com.example.producer_mq.model.User;
@RestController
public class UserPublisher {
    @Autowired
    private RabbitTemplate template;
    
    @PostMapping("/user")
    public String publish(@RequestBody User user) {
    	template.convertAndSend(MqConfig.REST_EXCHANGE,MqConfig.REST_KEY,user );
    	return "User added to the queue";
    }
    @GetMapping("/view")
    public String view() {
    	return "Hello";
    }
    
}
