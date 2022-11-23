package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.saca.model.Company;
import hu.webuni.hr.saca.model.Employee;

@Service
public class CompanyService {
	
	@Autowired
	private EmployeeService employeeService;

	private Map<Long, Company> companies = new HashMap<>();

	{
		Employee emp1 = new Employee(1L, "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50));
		Employee emp2 = new Employee(2L, "Jozsi", "bigboss", 200, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(20));
		Employee emp3 = new Employee(3L, "Péter", "worker", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(10));
		Employee emp4 = new Employee(4L, "Gyula", "ceo", 300, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(35));
		List<Employee> empList1 = new ArrayList<Employee>(Arrays.asList(emp1, emp2)); 
		List<Employee> empList2 = new ArrayList<Employee>(Arrays.asList(emp3, emp4)); 
		companies.put(1L, new Company(1L, "Kerékgyártó kft.", "Budapest, 1044. Átlós utca 23", "12-34-567890", empList1));
		companies.put(2L, new Company(2L, "Aszfaltozó kft.", "Budapest, 1033. Vízimalom utca 42", "98-76-5432100", empList2));
	}

	
	public List<Company> findAll() {
		return new ArrayList<Company>(companies.values());
	}
	
	public Company findById(long id) {
		return companies.get(id);
	} 
	
	public Company save(Company company) {
		companies.put(company.getId(), company);
		return company;
	}
	
	public void delete(long id) {
		companies.remove(id);
	}

	public Company update(Company company) {
		if (companies.containsKey(company.getId())) {
			companies.put(company.getId(), company);			
			return company;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Employee saveEmployee(Long id, Employee employee) {
		if (companies.containsKey(id)) {
			companies.get(id).getEmployees().add(employee);
			return employee;
		} else {
			throw new NoSuchElementException();
		}		
	}
	
	public void deleteEmployee(Long compId, Long empId) {
		List<Employee> empList = companies.get(compId).getEmployees();
		Optional<Employee> empl = empList.stream()
						.filter(emp -> emp.getId() == empId)
						.findFirst();
		if (empl.isPresent()) {
			empList.remove(empl.get());	
		}
	}
	
	public List<Employee> changeEmployee(Long compId, List<Employee> listEmployees) {
		companies.get(compId).setEmployees(listEmployees);
		return listEmployees;
	}

}
