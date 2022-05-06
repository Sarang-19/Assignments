package com.example.dbtocsv.Reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.dbtocsv.model.Employee;
@Component
public class EmployeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee e=new Employee();
		e.setId(rs.getInt("id"));
		e.setName(rs.getString("name"));
		e.setDept(rs.getInt("dept"));
		e.setSalary(rs.getInt("salary"));
		return e;
	}

}
