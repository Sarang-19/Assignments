package com.example.xmltodb.config;

import java.sql.PreparedStatement;
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
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.xmltodb.model.User;
import com.thoughtworks.xstream.XStream;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	private DataSource datasource;
    @Bean
    public StaxEventItemReader<User> read(){
    	StaxEventItemReader<User> reader=new StaxEventItemReader<>();
    	reader.setResource(new ClassPathResource("users.xml"));
    	reader.setFragmentRootElementName("User");
    	Jaxb2Marshaller marshaller=new Jaxb2Marshaller();
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("User","com.example.xmltodb.model.User");
    	marshaller.setClassesToBeBound(com.example.xmltodb.model.User.class);
    	reader.setUnmarshaller(marshaller);
    	return reader;
    }
    @Bean
    public JdbcBatchItemWriter<User> write(){
    	JdbcBatchItemWriter<User> writer=new JdbcBatchItemWriter<>();
    	writer.setDataSource(datasource);
    	writer.setSql("insert into userrecord(user_id,name,age) values(?,?,?)");
    	writer.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<User>() {

			@Override
			public void setValues(User item, PreparedStatement ps) throws SQLException {
				ps.setInt(1, item.getUser_id());
				ps.setString(2,item.getName());
				ps.setInt(3, item.getAge());
				
			}

			
    		
    	});
    	return writer;
    	
    }
    @Bean
    public Job job(JobBuilderFactory jobbuilder,StepBuilderFactory stepbuilder) {
    	Step step=stepbuilder.get("xmltodbstep").<User,User> chunk(1).reader(read()).writer(write()).build();
    	return jobbuilder.get("xmltodbjob").incrementer(new RunIdIncrementer()).start(step).build();
    }
}
