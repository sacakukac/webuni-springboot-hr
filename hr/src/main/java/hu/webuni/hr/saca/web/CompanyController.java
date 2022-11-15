package hu.webuni.hr.saca.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.saca.Service.EmployeeService;
import hu.webuni.hr.saca.dto.CompanyDto;
import hu.webuni.hr.saca.dto.EmployeeDto;
import hu.webuni.hr.saca.model.Employee;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	@Autowired
	private EmployeeService employeeService;

	private Map<Long, CompanyDto> companies = new HashMap<>();

	{
		EmployeeDto emp1 = new EmployeeDto(1L, "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50));
		EmployeeDto emp2 = new EmployeeDto(2L, "Jozsi", "bigboss", 200, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(20));
		EmployeeDto emp3 = new EmployeeDto(3L, "Péter", "worker", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(10));
		EmployeeDto emp4 = new EmployeeDto(4L, "Gyula", "ceo", 300, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(35));
		List<EmployeeDto> empList1 = new ArrayList<EmployeeDto>(Arrays.asList(emp1, emp2)); 
		List<EmployeeDto> empList2 = new ArrayList<EmployeeDto>(Arrays.asList(emp3, emp4)); 
		companies.put(1L, new CompanyDto(1L, "Kerékgyártó kft.", "Budapest, 1044. Átlós utca 23", "12-34-567890", empList1));
		companies.put(2L, new CompanyDto(2L, "Aszfaltozó kft.", "Budapest, 1033. Vízimalom utca 42", "98-76-5432100", empList2));
	}

	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) String full) {
		List<CompanyDto> compDto;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			compDto = new ArrayList<>();
			for (Map.Entry<Long, CompanyDto> entry : companies.entrySet()) {
//				Long key = entry.getKey();
				CompanyDto comp = entry.getValue();
				compDto.add(  new CompanyDto(comp.getId(), comp.getName(), comp.getAddress(), comp.getRegNumber(), null)  );
			}
		} else {
			compDto = new ArrayList<>(companies.values());  // employee-k is, összes elem
		}			
		return compDto;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getById(@PathVariable long id, @RequestParam(required = false) String full) {
		CompanyDto compDto;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			compDto = new CompanyDto(companies.get(id).getId(), companies.get(id).getName(), companies.get(id).getAddress(), companies.get(id).getRegNumber(), null); 
		} else {
			compDto = companies.get(id); 
		}
		if (compDto != null) {
			return ResponseEntity.ok(compDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
		companies.put(companyDto.getId(), companyDto);
		return companyDto;
	}
	
	@PutMapping("/{id}")  //modify
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto){
		if (!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		} else {
			companyDto.setId(id);
			companies.put(id, companyDto);
			return ResponseEntity.ok(companyDto);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companies.remove(id);
	}

	@PostMapping("/{id}/addEmployee")
	public ResponseEntity<EmployeeDto>  createEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		CompanyDto companyDto = companies.get(id);
		if (companyDto != null) {
			companyDto.getEmployees().add(employeeDto);
			return ResponseEntity.ok(employeeDto);
		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@DeleteMapping("/{compid}/deleteEmployee/{empid}")
	public void deleteEmployee (@PathVariable Long compid, @PathVariable Long empid ) {
		EmployeeDto empl = companies.get(compid).getEmployees().stream().filter(emp -> emp.getId() == empid).findFirst().get();
		companies.get(compid).getEmployees().remove(empl);
	}

	@PutMapping("/{compid}/changeEmployee")
	public List<EmployeeDto> changeEmployees(@PathVariable Long compid, @RequestBody List<EmployeeDto>  listEmployees) {
		companies.get(compid).setEmployees(listEmployees);
		return listEmployees;
	} 
	
	@PostMapping("/getpercent")
	public int getPayRaisePercent(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
}
