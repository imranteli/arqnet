package com.web.mvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.mvc.dao.EmployeeDAO;
import com.web.mvc.model.Employee;

@Service("userService")
public class EmployeeServiceImpl implements EmployeeService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private EmployeeDAO employeeDao;
	
	private static List<Employee> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
		//return users;
	}
	
	public Employee findById(long id) {
		/*for(Employee user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;*/
		return employeeDao.findById(id);
	}
	
	public Employee findByName(String name) {
		/*for(Employee user : users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;*/
		return employeeDao.findByName(name);
	}
	
	public void saveEmployee(Employee user) {
		employeeDao.saveEmployee(user);
		//user.setId(counter.incrementAndGet());
		//users.add(user);
	}

	public void updateEmployee(Employee user) {
		//int index = users.indexOf(user);
		//users.set(index, user);
		employeeDao.updateEmployee(user);
	}

	public void deleteEmployeeById(long id) {
		
		/*for (Iterator<Employee> iterator = users.iterator(); iterator.hasNext(); ) {
		    Employee user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}*/
		employeeDao.deleteEmployeeById(id);
		
	}

	public boolean isEmployeeExist(Employee user) {
		//return findByName(user.getUsername())!=null;
		return employeeDao.isEmployeeExist(user);
	}
	
	public void deleteAllEmployees(){
		//users.clear();
		employeeDao.deleteAllEmployees();
	}

	private static List<Employee> populateDummyUsers(){
		List<Employee> users = new ArrayList<Employee>();
		users.add(new Employee(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
		users.add(new Employee(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
		users.add(new Employee(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
		return users;
	}

}
