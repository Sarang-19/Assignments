package com.example.springbatchdelta.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.example.springbatchdelta.model.Student;
import com.example.springbatchdelta.processor.UniqueStudentProcessor;
import com.example.springbatchdelta.writer.Previous_Writer;
import com.example.springbatchdelta.writer.StudentClassifer;



@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
     @Autowired
     private JobBuilderFactory jobbuilder;
     @Autowired
     private StepBuilderFactory stepbuilder;
    
      @Bean
      public ClassifierCompositeItemWriter<Student> classifier(){
    	  ClassifierCompositeItemWriter<Student> writer=new ClassifierCompositeItemWriter<>();
    	  writer.setClassifier(new StudentClassifer(writer_success(),writer_reject()));
    	  return writer;
      }
    
     @Bean
     public FlatFileItemWriter<Student> writer_success(){
    	 FlatFileItemWriter<Student> writer=new FlatFileItemWriter<>();
    	 
    	writer.setResource(new FileSystemResource("success.csv"));
    	DelimitedLineAggregator<Student> aggregator=new DelimitedLineAggregator<>();
    	aggregator.setDelimiter(",");
    	BeanWrapperFieldExtractor<Student> extractor=new BeanWrapperFieldExtractor<>();
    	extractor.setNames(Student.getFields());
		aggregator.setFieldExtractor(extractor);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("student_id,student_name,subject,marks");
				
			}
			
		});
		writer.setLineAggregator(aggregator);
		
		
		return writer;
     } 
     @Bean
     public FlatFileItemWriter<Student> writer_reject(){
    	 FlatFileItemWriter<Student> writer=new FlatFileItemWriter<>();
    	writer.setResource(new FileSystemResource("reject.csv"));
    	DelimitedLineAggregator<Student> aggregator=new DelimitedLineAggregator<>();
    	aggregator.setDelimiter(",");
    
    	BeanWrapperFieldExtractor<Student> extractor=new BeanWrapperFieldExtractor<>();
    	extractor.setNames(Student.getFields());
		aggregator.setFieldExtractor(extractor);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("student_id,student_name,subject,marks");
				
			}
			
		});
		writer.setLineAggregator(aggregator);
		
		return writer;
     } 
     
     @Bean
     public FlatFileItemWriter<Student> writer_delta() throws IOException{
    	 FlatFileItemWriter<Student> writer=new FlatFileItemWriter<>();
    	 //File file = ResourceUtils.getFile("classpath:delta.csv");
    	 
    	
    	//ClassPathResource resource=new ClassPathResource("delta.csv");
    	
 	//InputStream inputStream=resource.getInputStream();
 //	System.out.println(inputStream.toString());
    	 //writer.setResource(inputStream);
    	
    	writer.setResource(new FileSystemResource("delta.csv"));
    	DelimitedLineAggregator<Student> aggregator=new DelimitedLineAggregator<>();
    	aggregator.setDelimiter(",");
    	BeanWrapperFieldExtractor<Student> extractor=new BeanWrapperFieldExtractor<>();
    	extractor.setNames(Student.getFields());
		aggregator.setFieldExtractor(extractor);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("student_id,student_name,subject,marks");
				
			}
			
		});
		writer.setLineAggregator(aggregator);
		return writer;
     } 
     
     @Bean
     public FlatFileItemReader<Student> reader_previous(){
    	 FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
    	 reader.setResource(new FileSystemResource("previous.csv"));
    	 DefaultLineMapper<Student> mapper=new DefaultLineMapper<>();
    	 DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	 tokenizer.setDelimiter(",");
    	 tokenizer.setNames(Student.getFields());
    	 mapper.setLineTokenizer(tokenizer);
    	 BeanWrapperFieldSetMapper<Student> fieldmapper=new BeanWrapperFieldSetMapper<>();
    	 fieldmapper.setTargetType(Student.class);
    	 mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	reader.setStrict(false);
    	return reader;	
    	 
     }
     @Bean
     public FlatFileItemReader<Student> reader_current(){
    	 FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
    	 reader.setResource(new FileSystemResource("current.csv"));
    	 DefaultLineMapper<Student> mapper=new DefaultLineMapper<>();
    	 DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	 tokenizer.setDelimiter(",");
    	 tokenizer.setNames(Student.getFields());
    	 mapper.setLineTokenizer(tokenizer);
    	 BeanWrapperFieldSetMapper<Student> fieldmapper=new BeanWrapperFieldSetMapper<>();
    	 fieldmapper.setTargetType(Student.class);
    	 mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	reader.setStrict(false);
    	return reader;	
    	 
     }
     
     @Bean
     public FlatFileItemReader<Student> reader_delta(){
    	 FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
    	 reader.setResource(new FileSystemResource("delta.csv"));
    	 DefaultLineMapper<Student> mapper=new DefaultLineMapper<>();
    	 DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	 tokenizer.setDelimiter(",");
    	 tokenizer.setNames(Student.getFields());
    	 mapper.setLineTokenizer(tokenizer);
    	 BeanWrapperFieldSetMapper<Student> fieldmapper=new BeanWrapperFieldSetMapper<>();
    	 fieldmapper.setTargetType(Student.class);
    	 mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	reader.setLinesToSkip(1);
    	reader.setStrict(false);
    	return reader;	
    	 
     }
    
    
     @Bean
     public Step step3() {
    	 return stepbuilder.get("WriterClassifierstep").<Student,Student>chunk(10).reader(reader_delta()).writer(classifier()).stream(writer_success()).stream(writer_reject()).build();
     }
     @Bean
     public Step step2() throws IOException {
    	 return stepbuilder.get("read_current").<Student,Student>chunk(10).reader(reader_current()).processor(new UniqueStudentProcessor()).writer(writer_delta()).build();
     }
     @Bean
     public Step step1() {
    	 return stepbuilder.get("read_previous").<Student,Student>chunk(10).reader(reader_previous()).writer(new Previous_Writer()).build();
     }
     @Bean
     public Job job() throws IOException {
    	 return jobbuilder.get("deltajob").incrementer(new RunIdIncrementer()).flow(step1()).next(step2()).next(step3()).end().build();
     }
}
