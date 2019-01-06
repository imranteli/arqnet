package com.web.mvc.service;

import java.util.List;

import com.web.mvc.model.Employee;



public interface EmployeeService {
	
	Employee findById(long id);
	
	Employee findByName(String name);
	
	void saveEmployee(Employee user);
	
	void updateEmployee(Employee user);
	
	void deleteEmployeeById(long id);

	List<Employee> findAllEmployees(); 
	
	void deleteAllEmployees();
	
	public boolean isEmployeeExist(Employee user);
	
}
