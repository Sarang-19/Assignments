package com.example.dbtoXml.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.dbtoXml.Processor.BookProcessor;
import com.example.dbtoXml.model.Book;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	private DataSource datasource;
	@Autowired
	private StepBuilderFactory stepbuilder;
	@Autowired
	private JobBuilderFactory jobbuilder;
	@Autowired
	private BookProcessor bookprocessor;
	
  @Bean
  public JdbcCursorItemReader<Book> read(){
	  JdbcCursorItemReader<Book> reader=new JdbcCursorItemReader<>();
	  reader.setDataSource(datasource);
	  reader.setSql("select * from books");
	  reader.setRowMapper(new RowMapper<Book>() {

		@Override
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book=new Book();
			book.setBook_id(rs.getInt("book_id"));
			book.setBook_name(rs.getString("book_name"));
			book.setBook_author(rs.getString("book_author"));
			
			return book;
		}
		  
	  });
	  return reader;
  }
  
  @Bean
  public StaxEventItemWriter<Book> write(){
	  StaxEventItemWriter<Book> writer =new StaxEventItemWriter<>();
	  writer.setResource(new ClassPathResource("Books.xml"));
	  XStreamMarshaller marshaller=new XStreamMarshaller();
	  Map<String,String> aliases=new HashMap<>();
	  aliases.put("Book", "com.example.dbtoXml.model.Book");
	  marshaller.setAliases(aliases);
	  writer.setOverwriteOutput(true);
	  writer.setRootTagName("Books");
	    
	  writer.setMarshaller(marshaller);
	  return writer;
  }
  @Bean
  public Step step() {
	  return stepbuilder.get("dbtoXml").<Book,Book>chunk(3).reader(read()).processor(bookprocessor).writer(write()).build();
  }
  @Bean
  public Job job() {
	  return jobbuilder.get("dbtoXmljob").incrementer(new RunIdIncrementer()).start(step()).build();
  }
}
