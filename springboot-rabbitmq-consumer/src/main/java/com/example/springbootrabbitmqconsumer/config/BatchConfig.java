package com.example.springbootrabbitmqconsumer.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


import com.example.springbootrabbitmqconsumer.model.User;
import com.example.springbootrabbitmqconsumer.mqconfig.Mqconfig;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private StepBuilderFactory stepBuilder;
	@Autowired
	private JobBuilderFactory jobBuilder;
	@Bean
	@RabbitListener(queues=Mqconfig.QUEUE)
	public AmqpItemReader<User> reader(){
		AmqpItemReader<User> reader=new AmqpItemReader<>(template);
		return reader;
	}
		
	@Bean
    public FlatFileItemWriter<User> write(){
   	 FlatFileItemWriter<User> writer=new FlatFileItemWriter<>();
   	writer.setResource(new ClassPathResource("output.csv"));
   	DelimitedLineAggregator<User> aggregator=new DelimitedLineAggregator<>();
   	aggregator.setDelimiter(",");
   	BeanWrapperFieldExtractor<User> extractor=new BeanWrapperFieldExtractor<>();
   	extractor.setNames(new String[] {"user_id","user_name","user_age"});
		aggregator.setFieldExtractor(extractor);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("user_id,user_name,user_age");
				
			}
			
		});
		writer.setLineAggregator(aggregator);
		
		return writer;
    } 
	@Bean
	public Step step() {
		return stepBuilder.get("readFromQueueStep").<User,User>chunk(3).reader(reader()).writer(write()).build();
	}
    
	@Bean
	public Job job() {
		return jobBuilder.get("readFromQueueJob").incrementer(new RunIdIncrementer()).start(step()).build();
	}
}
