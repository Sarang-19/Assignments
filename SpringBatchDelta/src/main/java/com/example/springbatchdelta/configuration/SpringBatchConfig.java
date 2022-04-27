package com.example.springbatchdelta.configuration;

import java.io.IOException;
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
    	writer.setResource(new ClassPathResource("success.csv"));
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
    	writer.setResource(new ClassPathResource("reject.csv"));
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
     public FlatFileItemWriter<Student> writer_delta(){
    	 FlatFileItemWriter<Student> writer=new FlatFileItemWriter<>();
    	writer.setResource(new ClassPathResource("delta.csv"));
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
    	 reader.setResource(new ClassPathResource("previous.csv"));
    	 DefaultLineMapper<Student> mapper=new DefaultLineMapper<>();
    	 DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	 tokenizer.setDelimiter(",");
    	 tokenizer.setNames(Student.getFields());
    	 mapper.setLineTokenizer(tokenizer);
    	 BeanWrapperFieldSetMapper<Student> fieldmapper=new BeanWrapperFieldSetMapper<>();
    	 fieldmapper.setTargetType(Student.class);
    	 mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	return reader;	
    	 
     }
     @Bean
     public FlatFileItemReader<Student> reader_current(){
    	 FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
    	 reader.setResource(new ClassPathResource("current.csv"));
    	 DefaultLineMapper<Student> mapper=new DefaultLineMapper<>();
    	 DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();
    	 tokenizer.setDelimiter(",");
    	 tokenizer.setNames(Student.getFields());
    	 mapper.setLineTokenizer(tokenizer);
    	 BeanWrapperFieldSetMapper<Student> fieldmapper=new BeanWrapperFieldSetMapper<>();
    	 fieldmapper.setTargetType(Student.class);
    	 mapper.setFieldSetMapper(fieldmapper);
    	reader.setLineMapper(mapper);
    	return reader;	
    	 
     }
     
     @Bean
     public FlatFileItemReader<Student> reader_delta(){
    	 FlatFileItemReader<Student> reader=new FlatFileItemReader<>();
    	 reader.setResource(new ClassPathResource("delta.csv"));
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
    	return reader;	
    	 
     }
    
    
     @Bean
     public Step step3() {
    	 return stepbuilder.get("WriterClassifierstep").<Student,Student>chunk(10).reader(reader_delta()).writer(classifier()).stream(writer_success()).stream(writer_reject()).build();
     }
     @Bean
     public Step step2() {
    	 return stepbuilder.get("read_current").<Student,Student>chunk(10).reader(reader_current()).processor(new UniqueStudentProcessor()).writer(writer_delta()).build();
     }
     @Bean
     public Step step1() {
    	 return stepbuilder.get("read_previous").<Student,Student>chunk(10).reader(reader_previous()).writer(new Previous_Writer()).build();
     }
     @Bean
     public Job job() {
    	 return jobbuilder.get("deltajob").incrementer(new RunIdIncrementer()).flow(step1()).next(step2()).next(step3()).end().build();
     }
}
