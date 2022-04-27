package com.example.batch.config;


import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.LineMapper;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.batch.model.Student;



@Configuration
@EnableBatchProcessing
public class BatchConfig {
	Logger logger=org.slf4j.LoggerFactory.getLogger(BatchConfig.class);
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
	StepBuilderFactory stepBuilderFactory,ItemReader<Student> itemReader,
	ItemProcessor<Student,Student> itemProcessor,ItemWriter<Student> itemWriter) {
       logger.info("The batch for reading from CSV and writing DB has started");
	Step step=stepBuilderFactory.get("batch-step").<Student,Student>chunk(100).reader(itemReader)
	.processor(itemProcessor).writer(itemWriter).build();

	return jobBuilderFactory.get("batch-job").start(step).incrementer(new RunIdIncrementer()).build();
	}
	
	
	
	@Bean
	public ItemReader<Student> read(){
		logger.info("Data is read from the CSV file");
		FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("input.csv"));
		LineMapper<Student> lm=linemapper();
        reader.setLineMapper(lm);
        return reader;
	}
	@Bean
	public LineMapper<Student> linemapper(){
		DefaultLineMapper<Student> linemap=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames(new String[] {"id","stu_name","subject","marks"});
		linemap.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<Student>  beanwrap=new BeanWrapperFieldSetMapper<>();
		beanwrap.setTargetType(Student.class);
		linemap.setFieldSetMapper(beanwrap);
		return linemap;
		
	}

}
