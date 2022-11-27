package hu.webuni.hr.saca.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.saca.Service.CompanyService;
import hu.webuni.hr.saca.Service.EmployeeService;
import hu.webuni.hr.saca.dto.CompanyDto;
import hu.webuni.hr.saca.dto.EmployeeDto;
import hu.webuni.hr.saca.mapper.CompanyMapper;
import hu.webuni.hr.saca.mapper.EmployeeMapper;
import hu.webuni.hr.saca.model.Company;
import hu.webuni.hr.saca.model.Employee;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	CompanyMapper companyMapper;

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) String full) {
		List<CompanyDto> compDto;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			compDto = new ArrayList<>();
			for (CompanyDto companyDto : companyMapper.companiesToDtos((companyService.findAll()))) {
				compDto.add(  new CompanyDto(companyDto.getId(), companyDto.getName(), companyDto.getAddress(), companyDto.getRegNumber(), null)  );
			}	 
		} else {
			compDto = companyMapper.companiesToDtos(companyService.findAll());  // employee-k is, összes elem
		}			
		return compDto;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getById(@PathVariable long id, @RequestParam(required = false) String full) {
		CompanyDto compDto;
		if ((full == null) || (full.equalsIgnoreCase("false"))) {
			CompanyDto foundCompanyDto = companyMapper.companyToDto(companyService.findById(id));
			compDto = new CompanyDto(foundCompanyDto.getId(), foundCompanyDto.getName(), foundCompanyDto.getAddress(), foundCompanyDto.getRegNumber(), null); 
		} else {
			compDto = companyMapper.companyToDto(companyService.findById(id)); 
		}
		if (compDto != null) {
			return ResponseEntity.ok(compDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.save(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company);
	}
	
	@PutMapping("/{id}")  //modify
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto){
		Company company = companyMapper.dtoToCompany(companyDto);
		company.setId(id);
		try {
			CompanyDto savedCompanyDto = companyMapper.companyToDto((companyService.update(company))); 
			return ResponseEntity.ok(savedCompanyDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyService.delete(id);
	}

	@PostMapping("/{companyId}/addEmployee")
	public ResponseEntity<EmployeeDto>  createEmployee(@PathVariable Long companyId, @RequestBody EmployeeDto employeeDto) {
		Company company = companyService.findById(companyId);
		if (company != null) {
			Employee employee = companyService.saveEmployee(companyId, employeeMapper.dtoToEmployee(employeeDto));
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{compid}/deleteEmployee/{empid}")
	public void deleteEmployee (@PathVariable Long compid, @PathVariable Long empid ) {
		companyService.deleteEmployee(compid, empid);
	}

	@PutMapping("/{compid}/changeEmployee")
	public List<EmployeeDto> changeEmployees(@PathVariable Long compid, @RequestBody List<EmployeeDto>  listEmployeesDto) {
		List<Employee> listEmployees = employeeMapper.employeesDtosToEmpl(listEmployeesDto);
		List<Employee> changedEmployee = companyService.changeEmployee(compid, listEmployees);
		return employeeMapper.employeesToDtos(changedEmployee);
	} 
	
	@PostMapping("/getpercent")
	public int getPayRaisePercent(@RequestBody EmployeeDto employeeDto) {
		Employee employee = employeeMapper.dtoToEmployee(employeeDto);
		return employeeService.getPayRaisePercent(employee);
	}
}
