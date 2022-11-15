package hu.webuni.hr.saca.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.model.Employee;

@Service
public class SalaryService {

	@Autowired
	private EmployeeService employeeService;
	
	public int getEmployeeSalary(Employee employee) {
		return employee.getSalary() + (int)( employee.getSalary() * employeeService.getPayRaisePercent(employee) / 100.0 ); 
	}
}
