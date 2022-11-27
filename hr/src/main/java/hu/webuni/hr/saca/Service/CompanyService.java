package hu.webuni.hr.saca.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.saca.model.Company;
import hu.webuni.hr.saca.model.Employee;

@Service
public class CompanyService {
	
//	@Autowired
//	private EmployeeService employeeService;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
//	private Map<Long, Company> companies = new HashMap<>();
//
//	{
//		Employee emp1 = new Employee(1L, "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50));
//		Employee emp2 = new Employee(2L, "Jozsi", "bigboss", 200, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(20));
//		Employee emp3 = new Employee(3L, "Péter", "worker", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(10));
//		Employee emp4 = new Employee(4L, "Gyula", "ceo", 300, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(35));
//		List<Employee> empList1 = new ArrayList<Employee>(Arrays.asList(emp1, emp2)); 
//		List<Employee> empList2 = new ArrayList<Employee>(Arrays.asList(emp3, emp4)); 
//		companies.put(1L, new Company(1L, "Kerékgyártó kft.", "Budapest, 1044. Átlós utca 23", "12-34-567890", empList1));
//		companies.put(2L, new Company(2L, "Aszfaltozó kft.", "Budapest, 1033. Vízimalom utca 42", "98-76-5432100", empList2));
//	}

	
	public List<Company> findAll() {
//		return new ArrayList<Company>(companies.values());
		return new ArrayList<Company>(companyRepository.findAll());
		
	}
	
	public Company findById(long id) {
//		return companies.get(id);
		return companyRepository.findById(id).get();	
	} 
	
	@Transactional
	public Company save(Company company) {
//		companies.put(company.getId(), company);
		companyRepository.save(company);
		for (Employee employee : company.getEmployees()) {
			employee.setCompany(company);
			employeeRepository.save(employee);
		} 
		return company;
	}
	
	@Transactional
	public void delete(long id) {
//		companies.remove(id);
		for (Employee employee : companyRepository.findById(id).get().getEmployees()) {
			employeeRepository.delete(employee);
		} 
		companyRepository.deleteById(id);
	}

	@Transactional
	public Company update(Company company) {
//		if (companies.containsKey(company.getId())) {
//			companies.put(company.getId(), company);			
//			return company;
//		} else {
//			throw new NoSuchElementException();
//		}
		if (companyRepository.findById(company.getId()).isPresent()) {
			companyRepository.save(company);
			for (Employee employee : company.getEmployees()) {
				employee.setCompany(company);
				employeeRepository.save(employee);
			} 			
			return company;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Transactional
	public Employee saveEmployee(Long companyId, Employee employee) {
		if (companyRepository.findById(companyId).isPresent()) {
			companyRepository.findById(companyId).get().getEmployees().add(employee);
			employee.setCompany(companyRepository.findById(companyId).get());
			employeeRepository.save(employee);
			return employee;
		} else {
			throw new NoSuchElementException();
		}		
	}
	
	@Transactional
	public void deleteEmployee(Long compId, Long empId) {
		List<Employee> empList = companyRepository.findById(compId).get().getEmployees();
		Optional<Employee> empl = empList.stream()
						.filter(emp -> emp.getId() == empId)
						.findFirst();
		if (empl.isPresent()) {
			empList.remove(empl.get());	
			employeeRepository.deleteById(empId);
		}
	}
	
	@Transactional
	public List<Employee> changeEmployee(Long compId, List<Employee> listEmployees) {
		for (Employee employee : companyRepository.findById(compId).get().getEmployees()) {
			employeeRepository.delete(employee);
		}
		companyRepository.findById(compId).get().setEmployees(listEmployees);
		for (Employee employee : listEmployees) {
			employee.setCompany(companyRepository.findById(compId).get());
			employeeRepository.save(employee);
		}
		return listEmployees;
	}

}
