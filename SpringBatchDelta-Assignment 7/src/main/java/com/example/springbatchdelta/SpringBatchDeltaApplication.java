package com.example.springbatchdelta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class SpringBatchDeltaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDeltaApplication.class, args);
	}

}
