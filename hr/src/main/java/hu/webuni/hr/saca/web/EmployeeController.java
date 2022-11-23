package hu.webuni.hr.saca.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import hu.webuni.hr.saca.Service.AbstractEmployeeService;
import hu.webuni.hr.saca.dto.EmployeeDto;
import hu.webuni.hr.saca.mapper.EmployeeMapper;
import hu.webuni.hr.saca.model.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	private AbstractEmployeeService employeeService;
	
//	private Map<Long, EmployeeDto> employees = new HashMap<>();
//
//	{
//		employees.put(1L, new EmployeeDto(1L, "Bela", "boss", 100, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(50)));
//		employees.put(2L, new EmployeeDto(2L, "jozsi", "littleboss", 50, LocalDateTime.of(2022,11,11,12,00,00).minusMonths(30)));
//	}

	//1. megoldás param nélküli hívás esetén
//	@GetMapping
//	public List<EmployeeDto> getAll() {
//		return new ArrayList<>(employees.values());
//	}

	//1. megoldás szuresre param-mal történő hívás esetén
//	@GetMapping(params = "salarylimit")
//	public ResponseEntity<List<EmployeeDto>> getEmployeeBySalary(@RequestParam Integer salarylimit ) {
//		List<EmployeeDto> overLimitEmployees = new ArrayList<>();		
//		for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
//			if( entry.getValue().getSalary() > salarylimit)	{
//				overLimitEmployees.add(entry.getValue());
//			}
//		}
//		if (overLimitEmployees.size() > 0 ) {
//			return ResponseEntity.ok(overLimitEmployees);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}	

	//2. megoldás egy rutinban lekezelve
	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getAll(@RequestParam(required = false) Integer salarylimit) {
		List<EmployeeDto> employeesDto = employeeMapper.employeesToDtos(( employeeService).findAll());  
		if (salarylimit == null) {
			return ResponseEntity.ok(new ArrayList<>(employeesDto));
		} else {
			List<EmployeeDto> overLimitEmployees = employeesDto
			.stream()
			.filter(emp -> emp.getSalary() > salarylimit)
			.collect(Collectors.toList());
//			List<EmployeeDto> overLimitEmployees = new ArrayList<>();		
//			for (Map.Entry<Long, EmployeeDto> entry : employees.entrySet()) {
//				if( entry.getValue().getSalary() > salarylimit)	{
//					overLimitEmployees.add(entry.getValue());
//				}
//			}
			if (overLimitEmployees != null ) {
				return ResponseEntity.ok(overLimitEmployees);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
		EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.findById(id)); 
		if (employeeDto != null) {
			return ResponseEntity.ok(employeeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return employeeMapper.employeeToDto(employee);
	}
	
	@PutMapping("/{id}")  //modify
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto){
		Employee employee = employeeMapper.dtoToEmployee(employeeDto);
		employee.setId(id);
		try {
			EmployeeDto updatedEmployeeDto = employeeMapper.employeeToDto(employeeService.update(employee));
			return ResponseEntity.ok(updatedEmployeeDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.delete(id);
	}
	
	//sajat megoldas
	@GetMapping("/salarylimit")
	public ResponseEntity<List<EmployeeDto>> getEmployeeBySalary2(@RequestParam("salarylimit") Integer salaryLimit ) {
		List<EmployeeDto> overLimitEmployees = new ArrayList<>();
		List<EmployeeDto> employeesDto = employeeMapper.employeesToDtos(employeeService.findAll());
		for (EmployeeDto employeeDto : employeesDto) {
			if( employeeDto.getSalary() > salaryLimit)	{
				overLimitEmployees.add(employeeDto);
			}
		}
		if (overLimitEmployees.size() > 0 ) {
			return ResponseEntity.ok(overLimitEmployees);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
