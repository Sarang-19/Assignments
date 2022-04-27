package com.example.dbtocsv.Processsor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.dbtocsv.model.Employee;
@Component
public class EmployeeProcessor implements ItemProcessor<Employee,Employee> {

	@Override
	public Employee process(Employee item) throws Exception {
		if(item.getSalary()<20000) {
			return null;
		}
		return item;
	}

}
