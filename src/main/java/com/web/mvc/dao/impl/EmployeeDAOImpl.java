package com.web.mvc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.web.mvc.dao.EmployeeDAO;
import com.web.mvc.model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	@Qualifier("GET_ALL_EMPLOYEE_SQL")
	private String GET_ALL_EMPLOYEE;
	
	
	@Autowired
	@Qualifier("INSERT_EMPLOYEE_SQL")
	private String INSERT_EMPLOYEE;
	
	@Autowired
	@Qualifier("UPDATE_EMPLOYEE_SQL")
	private String UPDATE_EMPLOYEE;
	
	@Autowired
	@Qualifier("DELETE_EMPLOYEE_SQL")
	private String DELETE_EMPLOYEE;
	
	
	@Autowired
	@Qualifier("DELETE_ALL_EMPLOYEE_SQL")
	private String DELETE_ALL_EMPLOYEE;
	
	@Autowired
	@Qualifier("IS_EMPLOYEE_EXIST_SQL")
	private String IS_EMPLOYEE_EXIST;
	
	@Autowired
	@Qualifier("GET_EMPLOYEE_BY_ID_SQL")
	private String GET_EMPLOYEE_BY_ID;
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	@Override
	public List<Employee> findAllEmployees() {
		try {
			List<Employee> users = jdbcTemplate.query(GET_ALL_EMPLOYEE, new ResultSetExtractor<List<Employee>>() {
				@Override
				public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Employee> tmpUsers = new ArrayList<>();
					while(rs.next()) {
						Employee user = new Employee();
						System.out.println("name...."+rs.getString("emp_name"));
						user.setId(rs.getLong("emp_no"));
						user.setUsername(rs.getString("emp_name"));
						user.setAddress(rs.getString("emp_address"));
						user.setEmail(rs.getString("email"));
						tmpUsers.add(user);
						
					}
					return tmpUsers;
				}
				
			});
			System.out.println("users...."+users.size());
		return users;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void saveEmployee(Employee user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empname", user.getUsername());
		params.addValue("empaddress", user.getAddress());
		params.addValue("email", user.getEmail());
		
		namedParameterJdbcTemplate.update(INSERT_EMPLOYEE, params);
		
		
	}


	@Override
	public Employee findById(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empno", id);
		
		List<Employee> users = namedParameterJdbcTemplate.query(GET_EMPLOYEE_BY_ID, params, new ResultSetExtractor<List<Employee>>() {
			@Override
			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Employee> tmpUsers = new ArrayList<>();
				while(rs.next()) {
					Employee user = new Employee();
					System.out.println("name...."+rs.getString("emp_name"));
					user.setId(rs.getLong("emp_no"));
					user.setUsername(rs.getString("emp_name"));
					user.setAddress(rs.getString("emp_address"));
					user.setEmail(rs.getString("email"));
					tmpUsers.add(user);
					
				}
				return tmpUsers;
			}
			
		});
		
		if(!users.isEmpty()) {
			return users.get(0);
		}
		
		return null;
	}


	@Override
	public Employee findByName(String name) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empname", name);
		
		List<Employee> users = namedParameterJdbcTemplate.query(IS_EMPLOYEE_EXIST, params, new ResultSetExtractor<List<Employee>>() {
			@Override
			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Employee> tmpUsers = new ArrayList<>();
				while(rs.next()) {
					Employee user = new Employee();
					System.out.println("name...."+rs.getString("emp_name"));
					user.setId(rs.getLong("emp_no"));
					user.setUsername(rs.getString("emp_name"));
					user.setAddress(rs.getString("emp_address"));
					user.setEmail(rs.getString("email"));
					tmpUsers.add(user);
					
				}
				return tmpUsers;
			}
			
		});
		
		if(!users.isEmpty()) {
			return users.get(0);
		}
		
		return null;
	}


	@Override
	public void updateEmployee(Employee user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empname", user.getUsername());
		params.addValue("empaddress", user.getAddress());
		params.addValue("email", user.getEmail());
		params.addValue("empno", user.getId());
		
		namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE, params);
		
	}


	@Override
	public void deleteEmployeeById(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empno", id);
		
		namedParameterJdbcTemplate.update(DELETE_EMPLOYEE, params);
		
	}


	@Override
	public void deleteAllEmployees() {
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		namedParameterJdbcTemplate.update(DELETE_ALL_EMPLOYEE, params);
		
	}


	@Override
	public boolean isEmployeeExist(Employee user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empname", user.getUsername());
		
		List<Employee> users = namedParameterJdbcTemplate.query(IS_EMPLOYEE_EXIST, params, new ResultSetExtractor<List<Employee>>() {
			@Override
			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Employee> tmpUsers = new ArrayList<>();
				while(rs.next()) {
					Employee user = new Employee();
					System.out.println("name...."+rs.getString("emp_name"));
					user.setId(rs.getLong("emp_no"));
					user.setUsername(rs.getString("emp_name"));
					user.setAddress(rs.getString("emp_address"));
					user.setEmail(rs.getString("email"));
					tmpUsers.add(user);
					
				}
				return tmpUsers;
			}
			
		});
		
		if(!users.isEmpty()) {
			return true;
		}
		
		return false;
	}
	

}
