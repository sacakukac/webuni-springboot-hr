package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import hu.webuni.hr.saca.model.Employee;

public abstract class AbstractEmployeeService implements EmployeeService{

	private Map<Long, Employee> employees = new HashMap<>();

	{
		employees.put(Long.valueOf(1L), new Employee(Long.valueOf(1L), "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50)));
		employees.put(Long.valueOf(2L), new Employee(Long.valueOf(2L), "jozsi", "littleboss", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(30)));
	}
	
	public List<Employee> findAll() {
		return new ArrayList<Employee>(employees.values());
	}
	
	public Employee findById(long id) {
		return employees.get(id);
	} 
	
	public Employee save(Employee company) {
		employees.put(company.getId(), company);
		return company;
	}
	
	public void delete(long id) {
		employees.remove(id);
	}

	public Employee update(Employee company) {
		if (employees.containsKey(company.getId())) {
			employees.put(company.getId(), company);			
			return company;
		} else {
			throw new NoSuchElementException();
		}
	}
		
	
	
}
