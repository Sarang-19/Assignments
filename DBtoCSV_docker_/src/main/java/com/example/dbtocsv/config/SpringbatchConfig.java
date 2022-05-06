package com.example.dbtocsv.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import com.example.dbtocsv.Processsor.EmployeeProcessor;
import com.example.dbtocsv.Reader.EmployeeMapper;
import com.example.dbtocsv.model.Employee;

@Configuration
@EnableBatchProcessing
public class SpringbatchConfig {
	@Autowired
	private JobBuilderFactory jobbuilder;
	@Autowired
	private StepBuilderFactory stepbuilder;
	@Autowired
	private DataSource datasource;
	@Autowired
	private EmployeeMapper empMapper;
	@Bean
	public JdbcCursorItemReader<Employee> read(){
		 JdbcCursorItemReader<Employee> jdbcCursorItemReader = new JdbcCursorItemReader<>();
	        jdbcCursorItemReader.setDataSource(datasource);
	        jdbcCursorItemReader.setSql("SELECT * FROM employee");
	        jdbcCursorItemReader.setRowMapper(empMapper);
	        return jdbcCursorItemReader;
	}
	@Bean
	public FlatFileItemWriter<Employee> write(){
		FlatFileItemWriter<Employee> writer=new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("out1.csv"));
		DelimitedLineAggregator<Employee> aggregator=new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Employee> extractor=new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] {"id","name","dept","salary"});
		aggregator.setFieldExtractor(extractor);		
		writer.setLineAggregator(aggregator);
		return writer;
	}
	
	
	@Bean
	public Job job(EmployeeProcessor processor) {
		return jobbuilder.get("db-csv").incrementer(new RunIdIncrementer()).flow(step(processor)).end().build();
	}
    @Bean
	public Step step(EmployeeProcessor processor) {
		
		return stepbuilder.get("db-csvstep").<Employee,Employee> chunk(1).reader(read()).processor(processor).writer(write()).build();
	}
  
  
}
