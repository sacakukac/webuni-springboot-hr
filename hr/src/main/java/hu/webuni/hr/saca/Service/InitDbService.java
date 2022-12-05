package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.saca.model.AvgOfSalary;
import hu.webuni.hr.saca.model.Company;
import hu.webuni.hr.saca.model.CountOfCompany;
import hu.webuni.hr.saca.model.Employee;
import hu.webuni.hr.saca.model.EmployeeUser;

@Service
public class InitDbService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	CompanyService companyService;
	
	EmployeeUserRepository employeeUserRepository;
	PasswordEncoder passwordEncoder;
	
	public InitDbService(EmployeeUserRepository employeeUserRepository, PasswordEncoder passwordEncoder) {
		super();
		this.employeeUserRepository = employeeUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void createUsersIfNeeded() {
		if(!employeeUserRepository.existsById("admin")) {
			employeeUserRepository.save(new EmployeeUser("admin", passwordEncoder.encode("pass"), Set.of("admin","user")));
		}
		if(!employeeUserRepository.existsById("user")) {
			employeeUserRepository.save(new EmployeeUser("user", passwordEncoder.encode("pass"), Set.of("user")));
		}
	}
	
	
	@Transactional
	public void clearDb() {
		em.createNamedQuery("Employee.deleteAll").executeUpdate();
		em.createNamedQuery("Company.deleteAll").executeUpdate();
	}

	@Transactional
	public void insertTestData() {
		                   
		Company company1 = new Company(null, "Kerékgyártó kft.", "Budapest, 1044. Átlós utca 23", "12-34-567890", Company.CompanyType.Kft, null);
		Company company2 = new Company(null, "Aszfaltozó kft.", "Budapest, 1033. Vízimalom utca 42", "98-76-5432100", Company.CompanyType.ZRt, null); 
		
		Employee emp1 = new Employee(null, "Bela", "boss", 1000, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50), company1);
		Employee emp2 = new Employee(null, "Jozsi", "boss", 2000, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(20), company1);
		Employee emp3 = new Employee(null, "Péter", "manager", 300, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(10), company1);
		Employee emp4 = new Employee(null, "Gyula", "tester", 200, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(15), company1);
		Employee emp5 = new Employee(null, "Joe", "manager", 200, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(25), company2);
		Employee emp6 = new Employee(null, "Tarja", "tester", 250, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(35), company2);
		List<Employee> empList1 = new ArrayList<Employee>(Arrays.asList(emp1, emp2, emp3, emp4)); 
		List<Employee> empList2 = new ArrayList<Employee>(Arrays.asList( emp5, emp6));
		company1.setEmployees(empList1);
		company2.setEmployees(empList2);

		employeeRepository.save(emp1);
		employeeRepository.save(emp2);
		employeeRepository.save(emp3);
		employeeRepository.save(emp4);
		employeeRepository.save(emp5);
		employeeRepository.save(emp6);
		
		companyRepository.save(company1);
		companyRepository.save(company2);

		List<Company> company = companyRepository.getCompaniesBySalaryLimit(200);
		for (Company company3 : company) {
			System.out.println("Company over salary limit: " + company3.getName());
		}
		
		List<CountOfCompany> NoEmpl = companyRepository.getCompaniesByNumberOfEmployee(3L);
		for (CountOfCompany countOfCompany : NoEmpl) {
			System.out.println("Company over number of employees limit: " + countOfCompany.getName() + " " + countOfCompany.getCnt());
		}
		
		List<AvgOfSalary> avgOfSalaries = companyRepository.getAvgOfSalaryByJob(320L);  // <= ez mindig változik a tesztadat újragenerálás miatt
		for (AvgOfSalary avgOfSalary : avgOfSalaries) {
			System.out.println(" Avg job salaries: " + avgOfSalary.getJobname() + " " + avgOfSalary.getAvg());
		}
		

		
	}
	
}
