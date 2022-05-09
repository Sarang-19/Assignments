package com.example.producer_mq.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
   public static final String REST_KEY = "Rest_key";
public static final String REST_EXCHANGE = "Rest_exchange";
   public static final String REST_QUEUE = "Rest_queue";
	@Bean
	public Queue queue() {
    	return new Queue(REST_QUEUE);
    }
    @Bean
    public TopicExchange exchange() {
    	return new TopicExchange(REST_EXCHANGE);
    }
    @Bean
    public Binding binding(Queue queue,TopicExchange exchange) {
    	return BindingBuilder.bind(queue).to(exchange).with(REST_KEY);
    }
    
    @Bean
    public  MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
    
    @Bean
	public RabbitTemplate template(ConnectionFactory connectionFactory) {
	
		RabbitTemplate template=new RabbitTemplate(connectionFactory);
		template.setMessageConverter(converter());
		return template;
	}
}
