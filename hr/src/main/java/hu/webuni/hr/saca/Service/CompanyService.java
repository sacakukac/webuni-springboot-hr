package hu.webuni.hr.saca.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping
	public List<Company> getAll(@RequestParam(required = false) String full) {
		List<Company> comp;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			comp = new ArrayList<>();
			for (Map.Entry<Long, Company> entry : companies.entrySet()) {
//				Long key = entry.getKey();
				Company company = entry.getValue();
				comp.add(  new Company(company.getId(), company.getName(), company.getAddress(), company.getRegNumber(), null)  );
			}
		} else {
			comp = new ArrayList<>(companies.values());  // employee-k is, összes elem
		}			
		return comp;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Company> getById(@PathVariable long id, @RequestParam(required = false) String full) {
		Company comp;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			comp = new Company(companies.get(id).getId(), companies.get(id).getName(), companies.get(id).getAddress(), companies.get(id).getRegNumber(), null); 
		} else {
			comp = companies.get(id); 
		}
		if (comp != null) {
			return ResponseEntity.ok(comp);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public Company createCompany(@RequestBody Company company) {
		companies.put(company.getId(), company);
		return company;
	}
	
	@PutMapping("/{id}")  //modify
	public ResponseEntity<Company> modifyCompany(@PathVariable long id, @RequestBody Company company){
		if (!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		} else {
			company.setId(id);
			companies.put(id, company);
			return ResponseEntity.ok(company);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companies.remove(id);
	}

	@PostMapping("/{id}/addEmployee")
	public ResponseEntity<Employee>  createEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		Company company = companies.get(id);
		if (company != null) {
			company.getEmployees().add(employee);
			return ResponseEntity.ok(employee);
		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@DeleteMapping("/{compid}/deleteEmployee/{empid}")
	public void deleteEmployee (@PathVariable Long compid, @PathVariable Long empid ) {
		Employee empl = companies.get(compid).getEmployees().stream().filter(emp -> emp.getId() == empid).findFirst().get();
		companies.get(compid).getEmployees().remove(empl);
	}

	@PutMapping("/{compid}/changeEmployee")
	public List<Employee> changeEmployees(@PathVariable Long compid, @RequestBody List<Employee>  listEmployees) {
		companies.get(compid).setEmployees(listEmployees);
		return listEmployees;
	} 
	
	@PostMapping("/getpercent")
	public int getPayRaisePercent(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
}
