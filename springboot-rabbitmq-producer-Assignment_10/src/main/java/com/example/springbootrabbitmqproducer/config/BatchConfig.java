package com.example.springbootrabbitmqproducer.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.springbootrabbitmqproducer.model.User;
import com.example.springbootrabbitmqproducer.processor.Userprocessor;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private StepBuilderFactory stepBuilder;
	@Autowired
	private JobBuilderFactory jobBuilder;
	@Autowired
	private RabbitTemplate template;
    @Bean
    public FlatFileItemReader<User> read(){
    	FlatFileItemReader<User> reader=new FlatFileItemReader<>();
    	reader.setResource(new ClassPathResource("users.csv"));
    	DefaultLineMapper<User> mapper=new DefaultLineMapper<>();
    	DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	tokenizer.setDelimiter(",");
    	tokenizer.setNames(new String[] {"user_id","user_name","user_age"});
       	mapper.setLineTokenizer(tokenizer);
       	BeanWrapperFieldSetMapper<User> fieldmapper=new BeanWrapperFieldSetMapper<>();
       	fieldmapper.setTargetType(User.class);
       	mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	reader.setLinesToSkip(1);
    	return reader;
    }
    
    @Bean
    public AmqpItemWriter<User> write(){
    	
  		return new AmqpItemWriter<User>(template);
    	
    }
    @Bean
    public Step step() {
    	return stepBuilder.get("CSVtoqueue").<User,User>chunk(4).reader(read()).processor(new Userprocessor()).writer(write()).build();
    }
    @Bean
    public Job job() {
    	return jobBuilder.get("CSVtoQueueJob").incrementer(new RunIdIncrementer()).start(step()).build();
    }
}
